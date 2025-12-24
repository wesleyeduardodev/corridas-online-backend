package com.corridasonline.api.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException() {
        super("Você não tem permissão para acessar este recurso");
    }

}
