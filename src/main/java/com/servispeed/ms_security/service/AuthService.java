package com.servispeed.ms_security.service;

import com.servispeed.ms_security.model.entity.Permiso;
import com.servispeed.ms_security.model.entity.Usuario;
import com.servispeed.ms_security.model.dto.LoginRequest;
import com.servispeed.ms_security.model.dto.LoginResponse;
import com.servispeed.ms_security.repository.UsuarioRepository;
import com.servispeed.ms_security.security.exception.CredencialesInvalidasException;
import com.servispeed.ms_security.security.exception.UsuarioNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(UsuarioNoEncontradoException::new);

        if (!passwordEncoder.matches(request.getPassword(), usuario.getContrasena())) {
            throw new CredencialesInvalidasException();
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
