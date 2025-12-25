package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.LocalidadeControllerDocument;
import com.corridasonline.api.dto.localidade.CidadeResponse;
import com.corridasonline.api.dto.localidade.EstadoResponse;
import com.corridasonline.api.service.LocalidadeService;
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
public class LocalidadeController implements LocalidadeControllerDocument {

    private final LocalidadeService localidadeService;

    @Override
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoResponse>> listarEstados() {
        return ResponseEntity.ok(localidadeService.listarEstados());
    }

    @Override
    @GetMapping("/estados/{uf}/cidades")
    public ResponseEntity<List<CidadeResponse>> listarCidades(@PathVariable String uf) {
        return ResponseEntity.ok(localidadeService.listarCidadesPorEstado(uf));
    }

}
