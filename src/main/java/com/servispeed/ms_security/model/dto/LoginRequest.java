package com.servispeed.ms_security.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
