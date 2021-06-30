package com.evgenys.online.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRegistration {

    private String firstName;
    private String lastName;

    private String username;
    private String password;

    private String email;
    private String phone;
}
