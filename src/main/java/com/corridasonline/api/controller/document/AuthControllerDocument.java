package com.corridasonline.api.controller.document;

import com.corridasonline.api.dto.auth.LoginRequest;
import com.corridasonline.api.dto.auth.LoginResponse;
import com.corridasonline.api.dto.auth.RegistroAtletaRequest;
import com.corridasonline.api.dto.auth.RegistroOrganizadorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Endpoints de login e registro")
public interface AuthControllerDocument {

    @Operation(summary = "Login", description = "Autentica um usuário e retorna o token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
    })
    ResponseEntity<LoginResponse> login(LoginRequest request);

    @Operation(summary = "Registrar Organizador", description = "Cria uma nova conta de organizador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organizador criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email/cpf já cadastrado")
    })
    ResponseEntity<LoginResponse> registrarOrganizador(RegistroOrganizadorRequest request);

    @Operation(summary = "Registrar Atleta", description = "Cria uma nova conta de atleta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atleta criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email/cpf já cadastrado")
    })
    ResponseEntity<LoginResponse> registrarAtleta(RegistroAtletaRequest request);

}
