package com.corridasonline.api.controller.document;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Health", description = "Endpoints de verificação de saúde da API")
public interface HealthControllerDocument {

    @Operation(
            summary = "Verificar saúde da API",
            description = "Retorna o status atual da API e timestamp"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API funcionando corretamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HealthResponse.class)
                    )
            )
    })
    ResponseEntity<Map<String, Object>> health();

    @Schema(description = "Resposta do health check")
    record HealthResponse(
            @Schema(description = "Status da API", example = "UP")
            String status,

            @Schema(description = "Data e hora da verificação", example = "2024-12-24T10:00:00")
            String timestamp,

            @Schema(description = "Nome do serviço", example = "corridas-api")
            String service
    ) {}

}
