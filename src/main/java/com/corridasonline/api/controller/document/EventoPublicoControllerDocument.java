package com.corridasonline.api.controller.document;

import com.corridasonline.api.dto.categoria.CategoriaResponse;
import com.corridasonline.api.dto.evento.EventoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Eventos Públicos", description = "Eventos disponíveis para inscrição")
public interface EventoPublicoControllerDocument {

    @Operation(summary = "Listar eventos abertos", description = "Lista todos os eventos com inscrições abertas")
    @ApiResponse(responseCode = "200", description = "Lista de eventos retornada")
    ResponseEntity<List<EventoResponse>> listarEventosAbertos();

    @Operation(summary = "Buscar evento", description = "Busca detalhes de um evento aberto")
    @ApiResponse(responseCode = "200", description = "Evento encontrado")
    @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    ResponseEntity<EventoResponse> buscarEvento(Long id);

    @Operation(summary = "Listar categorias do evento", description = "Lista as categorias de um evento com inscrições abertas")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada")
    @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    ResponseEntity<List<CategoriaResponse>> listarCategorias(Long id);

}
