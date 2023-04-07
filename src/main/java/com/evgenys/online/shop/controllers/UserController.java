package com.evgenys.online.shop.controllers;


import com.evgenys.online.shop.dto.UserDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/api/v1/profile")
    public UserDto getDataUser(Principal principal){
        return userService.findUserDataDto(principal.getName()).orElseThrow(()-> new ResourceNotFoundException("User with username: " + principal.getName() + " does not exist"));
    }
}













