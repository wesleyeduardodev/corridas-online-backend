package com.corridasonline.api.controller.document;

import com.corridasonline.api.dto.inscricao.InscricaoDetalheResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Inscrições - Organizador", description = "Gerenciamento de inscrições pelo organizador")
public interface InscricaoOrganizadorControllerDocument {

    @Operation(summary = "Listar inscrições", description = "Lista todas as inscrições de um evento")
    @ApiResponse(responseCode = "200", description = "Lista de inscrições")
    ResponseEntity<List<InscricaoDetalheResponse>> listar(Long eventoId);

    @Operation(summary = "Listar por status", description = "Lista inscrições filtradas por status")
    @ApiResponse(responseCode = "200", description = "Lista de inscrições")
    ResponseEntity<List<InscricaoDetalheResponse>> listarPorStatus(Long eventoId, String status);

    @Operation(summary = "Confirmar pagamento", description = "Confirma o pagamento de uma inscrição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento confirmado"),
            @ApiResponse(responseCode = "400", description = "Pagamento já confirmado")
    })
    ResponseEntity<InscricaoDetalheResponse> confirmarPagamento(Long eventoId, Long inscricaoId);

    @Operation(summary = "Atribuir número de peito", description = "Atribui número de peito a uma inscrição")
    @ApiResponse(responseCode = "200", description = "Número atribuído")
    ResponseEntity<InscricaoDetalheResponse> atribuirNumeroPeito(Long eventoId, Long inscricaoId, Integer numeroPeito);

}
