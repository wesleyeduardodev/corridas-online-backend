package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.EventoPublicoControllerDocument;
import com.corridasonline.api.dto.categoria.CategoriaResponse;
import com.corridasonline.api.dto.evento.EventoResponse;
import com.corridasonline.api.dto.evento.TrajetoPontoResponse;
import com.corridasonline.api.service.CategoriaService;
import com.corridasonline.api.service.EventoService;
import com.corridasonline.api.service.EventoTrajetoService;
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

    private final EventoService eventoService;
    private final EventoTrajetoService trajetoService;
    private final CategoriaService categoriaService;

    @Override
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listarEventosAbertos() {
        return ResponseEntity.ok(eventoService.listarEventosPublicos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarEvento(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarEventoPublico(id));
    }

    @Override
    @GetMapping("/{id}/categorias")
    public ResponseEntity<List<CategoriaResponse>> listarCategorias(@PathVariable Long id) {
        eventoService.buscarEventoPublico(id);
        return ResponseEntity.ok(categoriaService.listarCategoriasPublicas(id));
    }

    @Override
    @GetMapping("/{id}/trajeto")
    public ResponseEntity<List<TrajetoPontoResponse>> listarTrajeto(@PathVariable Long id) {
        return ResponseEntity.ok(trajetoService.listarPontosPublico(id));
    }

}
