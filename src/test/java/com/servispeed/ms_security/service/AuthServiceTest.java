package com.servispeed.ms_security.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.servispeed.ms_security.model.dto.LoginRequest;
import com.servispeed.ms_security.model.entity.Rol;
import com.servispeed.ms_security.model.entity.Usuario;
import com.servispeed.ms_security.repository.UsuarioRepository;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success_ReturnsToken() {
        Rol rol = new Rol();
        rol.setDescripcion("ADMIN");
        rol.setPermisos(new java.util.HashSet<>());

        Usuario mockUser = new Usuario();
        mockUser.setEmail("admin@correo.com");
        mockUser.setContrasena("hash123");
        mockUser.setRol(rol); 

        when(usuarioRepository.findByEmail("admin@correo.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("12345", "hash123")).thenReturn(true);
        when(jwtService.generateToken(mockUser)).thenReturn("token.falso.aqui");

        LoginRequest request = new LoginRequest();
        request.setEmail("admin@correo.com");
        request.setPassword("12345"); 

        var response = authService.login(request);

        assertNotNull(response);
    }

    @Test
    void login_Fails_WhenUserNotFound() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        
        LoginRequest request = new LoginRequest();
        request.setEmail("fantasma@correo.com");
        request.setPassword("12345");

        // Validar que lance excepción en JUnit 5
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void login_Fails_WhenPasswordIncorrect() {
        Usuario mockUser = new Usuario();
        mockUser.setContrasena("hash123");
        when(usuarioRepository.findByEmail("admin@correo.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("malaClave", "hash123")).thenReturn(false);
        
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@correo.com");
        request.setPassword("malaClave");

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}