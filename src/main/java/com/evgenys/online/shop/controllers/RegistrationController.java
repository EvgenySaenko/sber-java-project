package com.evgenys.online.shop.controllers;


import com.evgenys.online.shop.dto.UserDtoRegistration;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveNewUser(@RequestBody UserDtoRegistration userDtoReg){//user with such a password and username does not exist
        if (userService.findByUsernameAndPhoneAndEmail(userDtoReg).isPresent()){
            throw new ResourceNotFoundException("User with this login: " + userDtoReg.getUsername() + "," +
                                                               "phone: " + userDtoReg.getPhone() + "," +
                                                          "and e-mail: " + userDtoReg.getEmail() + " already exists");
        }
        return userService.saveOrUpdate(userDtoReg);
    }
}
