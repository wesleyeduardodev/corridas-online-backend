package com.corridasonline.api.controller.document;

import com.corridasonline.api.dto.categoria.CategoriaRequest;
import com.corridasonline.api.dto.categoria.CategoriaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Categorias", description = "Gerenciamento de categorias dos eventos")
public interface CategoriaControllerDocument {

    @Operation(summary = "Listar categorias", description = "Lista todas as categorias de um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<List<CategoriaResponse>> listar(Long eventoId);

    @Operation(summary = "Buscar categoria", description = "Busca uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<CategoriaResponse> buscarPorId(Long eventoId, Long categoriaId);

    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria para o evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<CategoriaResponse> criar(Long eventoId, CategoriaRequest request);

    @Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<CategoriaResponse> atualizar(Long eventoId, Long categoriaId, CategoriaRequest request);

    @Operation(summary = "Excluir categoria", description = "Exclui uma categoria do evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluída"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<Void> excluir(Long eventoId, Long categoriaId);

}
