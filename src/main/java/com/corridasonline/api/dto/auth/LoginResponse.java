package com.corridasonline.api.dto.auth;

public record LoginResponse(
        String token,
        String email,
        String role,
        String nome
) {}
