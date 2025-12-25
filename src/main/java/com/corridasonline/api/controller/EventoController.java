package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.EventoControllerDocument;
import com.corridasonline.api.dto.evento.EventoRequest;
import com.corridasonline.api.dto.evento.EventoResponse;
import com.corridasonline.api.dto.evento.TrajetoPontoRequest;
import com.corridasonline.api.dto.evento.TrajetoPontoResponse;
import com.corridasonline.api.service.EventoService;
import com.corridasonline.api.service.EventoTrajetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZADOR')")
public class EventoController implements EventoControllerDocument {

    private final EventoService eventoService;
    private final EventoTrajetoService trajetoService;

    @Override
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listar() {
        return ResponseEntity.ok(eventoService.listarMeusEventos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<EventoResponse> criar(@Valid @RequestBody EventoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.criar(request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody EventoRequest request) {
        return ResponseEntity.ok(eventoService.atualizar(id, request));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        eventoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/abrir-inscricoes")
    public ResponseEntity<EventoResponse> abrirInscricoes(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.abrirInscricoes(id));
    }

    @Override
    @PatchMapping("/{id}/fechar-inscricoes")
    public ResponseEntity<EventoResponse> fecharInscricoes(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.fecharInscricoes(id));
    }

    @Override
    @GetMapping("/{id}/trajeto")
    public ResponseEntity<List<TrajetoPontoResponse>> listarTrajeto(@PathVariable Long id) {
        return ResponseEntity.ok(trajetoService.listarPontos(id));
    }

    @Override
    @PostMapping("/{id}/trajeto")
    public ResponseEntity<List<TrajetoPontoResponse>> salvarTrajeto(
            @PathVariable Long id,
            @Valid @RequestBody List<TrajetoPontoRequest> pontos) {
        return ResponseEntity.ok(trajetoService.salvarTrajeto(id, pontos));
    }

    @Override
    @DeleteMapping("/{id}/trajeto")
    public ResponseEntity<Void> limparTrajeto(@PathVariable Long id) {
        trajetoService.limparTrajeto(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}/trajeto/distancia")
    public ResponseEntity<Map<String, Double>> calcularDistancia(@PathVariable Long id) {
        Double distancia = trajetoService.calcularDistanciaTotal(id);
        return ResponseEntity.ok(Map.of("distanciaKm", distancia));
    }

}
