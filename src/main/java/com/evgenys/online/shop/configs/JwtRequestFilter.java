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

@Component
@RequiredArgsConstructor
@Slf4j
//добавляем в цепочку стандартных секьюрити фильтров наш фильтр
//модуль безопасности проверяет есть ли токен в хэдере запроса
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
        //если юзер существует  и контекст пустой => секьюрити контекст это тредлокал он для каждого потока(запроса) свой
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(token);
//            }
            //кладем в токен юзернайм и роли - пароль затираем
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null,
                     jwtTokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);//положили в секьюрити контекст
        }
//        else {
//            //без этого 403 Forbidden,поэтому прерываем работу цепочки фильтров кидаем 401	Unauthorized
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}
