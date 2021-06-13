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

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    /***
     * token header{type:jwt, algoritm: HS256 or RSA}
     *       payload{claims} - описание сущности пользователя
     *       signature - подпись токена
     * HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
     * **/
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();//payload
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);//кладем роли по ключу

        Date issuedDate = new Date();//дата создания
        Date expiredDate = new Date(issuedDate.getTime() + 60 * 60 * 1000);//истекает

        return Jwts.builder()//собираем токен
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)//подпись
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)//идет проверка подписи что никто не подминил и что время не истекло
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public List<String> getRoles(String token){
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles",List.class));
    }

//
//    private boolean isTokenExpired(String token) {
//        Date date = getExpirationDateFromToken(token);
//        return date != null && date.before(new Date());
//    }
}
