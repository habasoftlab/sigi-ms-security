package com.servispeed.ms_security.security.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException() {
        super("Credenciales inválidas");
    }
}