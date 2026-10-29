package com.servispeed.ms_security.service;

import com.servispeed.ms_security.model.entity.Rol;
import com.servispeed.ms_security.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet; // Importación añadida

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", "Rz/0bjufMPmSHYIxfNABdTgVvLyIcgIvkhH8Y3L37yM=");
    }

    @Test
    void generateToken_ReturnsValidToken() {
        Rol rol = new Rol();
        rol.setDescripcion("ADMIN");
        rol.setPermisos(new HashSet<>());

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(11);
        usuario.setEmail("test@correo.com");
        usuario.setRol(rol);

        String token = jwtService.generateToken(usuario);

        assertNotNull(token);
    }
}