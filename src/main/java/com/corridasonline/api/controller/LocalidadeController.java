package com.corridasonline.api.controller;

import com.corridasonline.api.dto.localidade.CidadeResponse;
import com.corridasonline.api.dto.localidade.EstadoResponse;
import com.corridasonline.api.service.LocalidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/localidades")
@RequiredArgsConstructor
@Tag(name = "Localidades", description = "Endpoints públicos para estados e cidades (IBGE)")
public class LocalidadeController {

    private final LocalidadeService localidadeService;

    @GetMapping("/estados")
    @Operation(summary = "Listar estados", description = "Retorna todos os estados do Brasil ordenados por nome")
    public ResponseEntity<List<EstadoResponse>> listarEstados() {
        return ResponseEntity.ok(localidadeService.listarEstados());
    }

    @GetMapping("/estados/{uf}/cidades")
    @Operation(summary = "Listar cidades por estado", description = "Retorna todas as cidades de um estado ordenadas por nome")
    public ResponseEntity<List<CidadeResponse>> listarCidades(@PathVariable String uf) {
        return ResponseEntity.ok(localidadeService.listarCidadesPorEstado(uf));
    }
}
