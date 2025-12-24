package com.corridasonline.api.controller.document;

import com.corridasonline.api.dto.inscricao.InscricaoRequest;
import com.corridasonline.api.dto.inscricao.InscricaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Inscrições - Atleta", description = "Gerenciamento de inscrições do atleta")
public interface InscricaoAtletaControllerDocument {

    @Operation(summary = "Realizar inscrição", description = "Inscreve o atleta em um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscrição realizada"),
            @ApiResponse(responseCode = "400", description = "Inscrições fechadas ou atleta já inscrito")
    })
    ResponseEntity<InscricaoResponse> inscrever(Long eventoId, InscricaoRequest request);

    @Operation(summary = "Minhas inscrições", description = "Lista todas as inscrições do atleta")
    @ApiResponse(responseCode = "200", description = "Lista de inscrições")
    ResponseEntity<List<InscricaoResponse>> minhasInscricoes();

    @Operation(summary = "Buscar inscrição", description = "Busca detalhes de uma inscrição")
    @ApiResponse(responseCode = "200", description = "Inscrição encontrada")
    ResponseEntity<InscricaoResponse> buscarInscricao(Long inscricaoId);

    @Operation(summary = "Cancelar inscrição", description = "Cancela uma inscrição do atleta")
    @ApiResponse(responseCode = "204", description = "Inscrição cancelada")
    ResponseEntity<Void> cancelar(Long inscricaoId);

}
