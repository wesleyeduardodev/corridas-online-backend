package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.AuthControllerDocument;
import com.corridasonline.api.dto.auth.LoginRequest;
import com.corridasonline.api.dto.auth.LoginResponse;
import com.corridasonline.api.dto.auth.RegistroAtletaRequest;
import com.corridasonline.api.dto.auth.RegistroOrganizadorRequest;
import com.corridasonline.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocument {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Override
    @PostMapping("/registro/organizador")
    public ResponseEntity<LoginResponse> registrarOrganizador(@Valid @RequestBody RegistroOrganizadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrarOrganizador(request));
    }

    @Override
    @PostMapping("/registro/atleta")
    public ResponseEntity<LoginResponse> registrarAtleta(@Valid @RequestBody RegistroAtletaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrarAtleta(request));
    }

}
