package com.corridasonline.api.controller.document;

import com.corridasonline.api.dto.localidade.CidadeResponse;
import com.corridasonline.api.dto.localidade.EstadoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Localidades", description = "Endpoints públicos para estados e cidades (IBGE)")
public interface LocalidadeControllerDocument {

    @Operation(summary = "Listar estados", description = "Retorna todos os estados do Brasil ordenados por nome")
    @ApiResponse(responseCode = "200", description = "Lista de estados retornada")
    ResponseEntity<List<EstadoResponse>> listarEstados();

    @Operation(summary = "Listar cidades por estado", description = "Retorna todas as cidades de um estado ordenadas por nome")
    @ApiResponse(responseCode = "200", description = "Lista de cidades retornada")
    ResponseEntity<List<CidadeResponse>> listarCidades(String uf);

}
