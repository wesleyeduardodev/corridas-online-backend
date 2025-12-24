package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.InscricaoAtletaControllerDocument;
import com.corridasonline.api.dto.inscricao.InscricaoRequest;
import com.corridasonline.api.dto.inscricao.InscricaoResponse;
import com.corridasonline.api.service.InscricaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/atleta")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ATLETA')")
public class InscricaoAtletaController implements InscricaoAtletaControllerDocument {

    private final InscricaoService inscricaoService;

    @Override
    @PostMapping("/eventos/{eventoId}/inscricoes")
    public ResponseEntity<InscricaoResponse> inscrever(@PathVariable Long eventoId, @Valid @RequestBody InscricaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoService.inscrever(eventoId, request));
    }

    @Override
    @GetMapping("/inscricoes")
    public ResponseEntity<List<InscricaoResponse>> minhasInscricoes() {
        return ResponseEntity.ok(inscricaoService.minhasInscricoes());
    }

    @Override
    @GetMapping("/inscricoes/{inscricaoId}")
    public ResponseEntity<InscricaoResponse> buscarInscricao(@PathVariable Long inscricaoId) {
        return ResponseEntity.ok(inscricaoService.buscarMinhaInscricao(inscricaoId));
    }

    @Override
    @DeleteMapping("/inscricoes/{inscricaoId}")
    public ResponseEntity<Void> cancelar(@PathVariable Long inscricaoId) {
        inscricaoService.cancelarInscricao(inscricaoId);
        return ResponseEntity.noContent().build();
    }

}
