package com.servispeed.ms_security.service;

import com.servispeed.ms_security.model.entity.Permiso;
import com.servispeed.ms_security.model.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String SECRET;

    public String generateToken(Usuario usuario) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getIdUsuario());
        claims.put("rol", usuario.getRol().getDescripcion());

        List<String> permisos = usuario.getRol()
                .getPermisos()
                .stream()
                .map(Permiso::getNombreClave)
                .toList();

        claims.put("permisos", permisos);

        return Jwts.builder()
                .claims(claims)
                .subject(usuario.getEmail())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
}