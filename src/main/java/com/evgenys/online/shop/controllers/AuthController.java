package com.evgenys.online.shop.controllers;


import com.evgenys.online.shop.dto.JwtRequest;
import com.evgenys.online.shop.dto.JwtResponse;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.exceptions.ShopError;
import com.evgenys.online.shop.persistence.entities.Role;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.services.CartService;
import com.evgenys.online.shop.services.UserService;
import com.evgenys.online.shop.utils.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {//эта часть для получения токена
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    private final CartService cartService;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception {
        try {//получаем запрос в виде JSON(@RequestBody) authRequest и пробуем пройти аутентификацию AuthenticationProvider проверяет есть ли такой
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new ShopError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);//генерим токен


       cartService.getCartForUser(authRequest.getUsername(), authRequest.getCartId());


        return ResponseEntity.ok(new JwtResponse(token, userExistsRoleAdmin(authRequest.getUsername())));//в ручную выдаем токен не кладя в securityContext
    }

    public boolean userExistsRoleAdmin(String username) {
        User user = userService.findByUsername(username).orElseThrow(()->
                new ResourceNotFoundException("User with username: " + username + " does not exist"));
        for(Role r : (List<Role>) user.getRoles()){
            if (r.getName().equals("ROLE_ADMIN")) return true;
        }
        return false;
    }


}
