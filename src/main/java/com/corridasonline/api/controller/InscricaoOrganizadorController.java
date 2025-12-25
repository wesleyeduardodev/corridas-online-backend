package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.InscricaoOrganizadorControllerDocument;
import com.corridasonline.api.dto.inscricao.InscricaoDetalheResponse;
import com.corridasonline.api.service.InscricaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos/{eventoId}/inscricoes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZADOR')")
public class InscricaoOrganizadorController implements InscricaoOrganizadorControllerDocument {

    private final InscricaoService inscricaoService;

    @Override
    @GetMapping
    public ResponseEntity<List<InscricaoDetalheResponse>> listar(@PathVariable Long eventoId) {
        return ResponseEntity.ok(inscricaoService.listarInscricoesDoEvento(eventoId));
    }

    @Override
    @GetMapping(params = "status")
    public ResponseEntity<List<InscricaoDetalheResponse>> listarPorStatus(
            @PathVariable Long eventoId,
            @RequestParam String status) {
        return ResponseEntity.ok(inscricaoService.listarInscricoesPorStatus(eventoId, status));
    }

    @Override
    @PatchMapping("/{inscricaoId}/confirmar-pagamento")
    public ResponseEntity<InscricaoDetalheResponse> confirmarPagamento(
            @PathVariable Long eventoId,
            @PathVariable Long inscricaoId) {
        return ResponseEntity.ok(inscricaoService.confirmarPagamento(eventoId, inscricaoId));
    }

    @Override
    @PatchMapping("/{inscricaoId}/numero-peito")
    public ResponseEntity<InscricaoDetalheResponse> atribuirNumeroPeito(
            @PathVariable Long eventoId,
            @PathVariable Long inscricaoId,
            @RequestParam Integer numeroPeito) {
        return ResponseEntity.ok(inscricaoService.atribuirNumeroPeito(eventoId, inscricaoId, numeroPeito));
    }

}
