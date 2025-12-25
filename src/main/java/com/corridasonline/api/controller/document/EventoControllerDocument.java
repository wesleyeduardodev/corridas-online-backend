package com.corridasonline.api.controller.document;

import com.corridasonline.api.dto.evento.EventoRequest;
import com.corridasonline.api.dto.evento.EventoResponse;
import com.corridasonline.api.dto.evento.TrajetoPontoRequest;
import com.corridasonline.api.dto.evento.TrajetoPontoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Eventos", description = "Gerenciamento de eventos do organizador")
public interface EventoControllerDocument {

    @Operation(summary = "Listar meus eventos", description = "Lista todos os eventos do organizador logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos retornada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<List<EventoResponse>> listar();

    @Operation(summary = "Buscar evento por ID", description = "Busca um evento específico do organizador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<EventoResponse> buscarPorId(Long id);

    @Operation(summary = "Criar evento", description = "Cria um novo evento para o organizador logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<EventoResponse> criar(EventoRequest request);

    @Operation(summary = "Atualizar evento", description = "Atualiza um evento existente do organizador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<EventoResponse> atualizar(Long id, EventoRequest request);

    @Operation(summary = "Excluir evento", description = "Exclui um evento do organizador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento excluído"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<Void> excluir(Long id);

    @Operation(summary = "Abrir inscrições", description = "Abre as inscrições do evento para atletas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrições abertas"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<EventoResponse> abrirInscricoes(Long id);

    @Operation(summary = "Fechar inscrições", description = "Fecha as inscrições do evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrições fechadas"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<EventoResponse> fecharInscricoes(Long id);

    @Operation(summary = "Listar trajeto", description = "Lista os pontos do trajeto do evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trajeto retornado"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<List<TrajetoPontoResponse>> listarTrajeto(Long id);

    @Operation(summary = "Salvar trajeto", description = "Salva os pontos do trajeto do evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trajeto salvo"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<List<TrajetoPontoResponse>> salvarTrajeto(Long id, List<TrajetoPontoRequest> pontos);

    @Operation(summary = "Limpar trajeto", description = "Remove todos os pontos do trajeto do evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trajeto removido"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<Void> limparTrajeto(Long id);

    @Operation(summary = "Calcular distância", description = "Calcula a distância total do trajeto em km")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distância calculada"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    ResponseEntity<Map<String, Double>> calcularDistancia(Long id);

}
