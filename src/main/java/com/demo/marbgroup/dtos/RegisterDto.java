package com.demo.marbgroup.dtos;

import lombok.Data;

@Data
public class RegisterDto {

    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
}
