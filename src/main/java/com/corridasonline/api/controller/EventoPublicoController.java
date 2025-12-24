package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.EventoPublicoControllerDocument;
import com.corridasonline.api.dto.categoria.CategoriaResponse;
import com.corridasonline.api.dto.evento.EventoResponse;
import com.corridasonline.api.entity.Evento;
import com.corridasonline.api.exception.ResourceNotFoundException;
import com.corridasonline.api.repository.EventoRepository;
import com.corridasonline.api.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/eventos")
@RequiredArgsConstructor
public class EventoPublicoController implements EventoPublicoControllerDocument {

    private final EventoRepository eventoRepository;
    private final CategoriaService categoriaService;

    @Override
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listarEventosAbertos() {
        List<EventoResponse> eventos = eventoRepository.findByInscricoesAbertasTrueOrderByDataAsc()
                .stream()
                .map(EventoResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarEvento(@PathVariable Long id) {
        Evento evento = eventoRepository.findById(id)
                .filter(Evento::getInscricoesAbertas)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
        return ResponseEntity.ok(EventoResponse.fromEntity(evento));
    }

    @GetMapping("/{id}/categorias")
    public ResponseEntity<List<CategoriaResponse>> listarCategorias(@PathVariable Long id) {
        eventoRepository.findById(id)
                .filter(Evento::getInscricoesAbertas)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
        return ResponseEntity.ok(categoriaService.listarCategoriasPublicas(id));
    }

}
