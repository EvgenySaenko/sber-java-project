package com.evgenys.online.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private String createdDateTime;
    private String updatedDateTime;

}
