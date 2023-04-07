package com.evgenys.online.shop.configs;

import com.evgenys.online.shop.utils.token.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;


/***
 * Модуль безопасности при каждом запросе должен проверять есть ли токен в хэдере запроса
 * И если он есть, и актуальный - складывать в security context ( он тредлокал - для каждого запроса свой)
 * Добавляем этот класс в цепочку стандартных секьюрити фильтров
 * **/
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    //private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override//метод обрабатывает пролетающий сквозь него запрос
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");//проверяем есть ли хэдер "Authorization"
        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {//если "Authorization" существует и начинается с "Bearer "
            jwt = authHeader.substring(7);//отпиливаем "Bearer "
            try {
                //достаем имя пользователя из токена(в этот момент библиотека jjwt проверит (корректность токена -время жизни итд)
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("The token is expired");
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{\"message\": \"JWT is expired\"");
//                return;
            }
        }
        //если юзер существует  и контекст пустой(в Ресте должен быть пустой) => секьюрити контекст это тредлокал он для каждого потока(запроса) свой
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /***
             * Пользователя не спешим закидывать в ручную в security Context
             * В этом варианте мы каждый раз как запрос будет приходить будем дергать базу
             * **/
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);//достаем из базы пользователя
//            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
              //формируем токен
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(token);//и потом только кладем его в security Context
//            }
            /***
             * Этот вариант мы доверяем узеру который с токеном, так как он его получил значит прошел проверку до этого
             * Минус если изменим роли то они не изменятся пока время жизни токена не закончится и не сгенерится новый
             * **/
            //кладем в токен юзернайм и роли - пароль затираем
            //То есть обычно спринг делает это сам автоматом, заворачивает в объект UsernamePasswordAuthenticationToken
            //и если совпадают пара логин пароль то кладет в секюрити контекст
            //А тут делаем это мы в ручную достав все данные из токена
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null,
                     jwtTokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);//положили в ручную в секьюрити контекст
        }
//        else {
//            //без этого 403 Forbidden,поэтому прерываем работу цепочки фильтров кидаем 401	Unauthorized
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}
