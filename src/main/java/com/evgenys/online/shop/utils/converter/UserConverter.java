package com.evgenys.online.shop.utils.converter;


import com.evgenys.online.shop.dto.UserDto;
import com.evgenys.online.shop.dto.UserDtoRegistration;
import com.evgenys.online.shop.persistence.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor
public class UserConverter {

    private DateTimeFormatter formatter;

    @PostConstruct
    public void init(){
        this.formatter = DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy");

    }

    public UserDto convertToUserDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdDateTime(formatter.format(user.getCreatedAt()))
                .updatedDateTime(formatter.format(user.getUpdatedAt()))
        .build();
    }

    public User convertToUser(UserDtoRegistration userDtoRegistration){
        return User.builder()
                .username(userDtoRegistration.getUsername())
                .password(userDtoRegistration.getPassword())
                .firstName(userDtoRegistration.getFirstName())
                .lastName(userDtoRegistration.getLastName() == null ? "": userDtoRegistration.getLastName())
                .email(userDtoRegistration.getEmail())
                .phone(userDtoRegistration.getPhone())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
        .build();
    }
}
