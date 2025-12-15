package com.servispeed.ms_security.service;

import com.servispeed.ms_security.model.entity.Permiso;
import com.servispeed.ms_security.model.entity.Usuario;
import com.servispeed.ms_security.model.dto.LoginRequest;
import com.servispeed.ms_security.model.dto.LoginResponse;
import com.servispeed.ms_security.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import com.servispeed.ms_security.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getContrasena())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generateToken(usuario);

        List<String> permisos = usuario.getRol()
                .getPermisos()
                .stream()
                .map(Permiso::getNombreClave)
                .toList();

        return LoginResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .token(token)
                .rol(usuario.getRol().getDescripcion())
                .permisos(permisos)
                .build();
    }
}
