package com.evgenys.online.shop.utils.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 * Утилитный класс создающий токен, с методами работы с токеном
 * **/

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;//ключ для генерации токенов

    /***
     * Генерит новый токен такого вида:
     * token header{type:jwt, algoritm: HS256 or RSA}
     *       payload{claims} - описание сущности пользователя
     *       signature - подпись токена
     * HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
     * **/
    public String generateToken(UserDetails userDetails) {//формируем токен для определенного пользователя(по его данным)
        Map<String, Object> claims = new HashMap<>();//payload (полезные данные по пользователе которые передадим клиенту в пейлоаде)
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);//кладем роли по ключу (чтобы фронт как то ориентировался на эти роли)

        Date issuedDate = new Date();//дата создания
        Date expiredDate = new Date(issuedDate.getTime() + 60 * 60 * 1000);//дата истечения (истекает через час)

        return Jwts.builder()//собираем токен
                .setClaims(claims)//полезные данные
                .setSubject(userDetails.getUsername())//имя пользователя
                .setIssuedAt(issuedDate)//задаем даты создания и дата конечная
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)//делаем подпись, с указанием алгоритма хэширования и нашего секрет ключа
                .compact();
    }

    /***
     * Достает полезные данные из токена -  все Claims
     * **/
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)//идет проверка подписи что никто не подменил и что время не истекло
                .parseClaimsJws(token)//парсится достается payload
                .getBody();//получаем claims
    }

    /***
     * Позволяет достать конкретный Claims пользователя (имя пользователя или список ролей)
     * **/
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /***
     * Правило как вытаскивать username
     * **/
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /***
     * Правило как вытаскивать список ролей
     * **/
    public List<String> getRoles(String token){
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles",List.class));
    }

//
//    private boolean isTokenExpired(String token) {
//        Date date = getExpirationDateFromToken(token);
//        return date != null && date.before(new Date());
//    }
}
