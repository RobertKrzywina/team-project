package com.letswork.api.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserDto {

    private String email;
    private String password;
    private String confirmedPassword;
}
