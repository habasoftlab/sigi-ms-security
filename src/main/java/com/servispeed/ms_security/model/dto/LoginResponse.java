package com.servispeed.ms_security.model.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class LoginResponse {
    private Integer idUsuario;
    private String token;
    private String rol;
    private List<String> permisos;
}