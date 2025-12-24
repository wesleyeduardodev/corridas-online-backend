package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.CategoriaControllerDocument;
import com.corridasonline.api.dto.categoria.CategoriaRequest;
import com.corridasonline.api.dto.categoria.CategoriaResponse;
import com.corridasonline.api.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos/{eventoId}/categorias")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZADOR')")
public class CategoriaController implements CategoriaControllerDocument {

    private final CategoriaService categoriaService;

    @Override
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar(@PathVariable Long eventoId) {
        return ResponseEntity.ok(categoriaService.listarPorEvento(eventoId));
    }

    @Override
    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long eventoId, @PathVariable Long categoriaId) {
        return ResponseEntity.ok(categoriaService.buscarPorId(eventoId, categoriaId));
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@PathVariable Long eventoId, @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criar(eventoId, request));
    }

    @Override
    @PutMapping("/{categoriaId}")
    public ResponseEntity<CategoriaResponse> atualizar(@PathVariable Long eventoId, @PathVariable Long categoriaId, @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.atualizar(eventoId, categoriaId, request));
    }

    @Override
    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> excluir(@PathVariable Long eventoId, @PathVariable Long categoriaId) {
        categoriaService.excluir(eventoId, categoriaId);
        return ResponseEntity.noContent().build();
    }

}
