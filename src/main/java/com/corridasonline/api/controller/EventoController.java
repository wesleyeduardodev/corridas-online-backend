package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.EventoControllerDocument;
import com.corridasonline.api.dto.evento.EventoRequest;
import com.corridasonline.api.dto.evento.EventoResponse;
import com.corridasonline.api.service.EventoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZADOR')")
public class EventoController implements EventoControllerDocument {

    private final EventoService eventoService;

    @Override
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listar(
            @RequestParam(defaultValue = "false") boolean includeCancelados) {
        return ResponseEntity.ok(eventoService.listarMeusEventos(includeCancelados));
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
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EventoResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.cancelar(id));
    }

    @Override
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<EventoResponse> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.reativar(id));
    }

}
