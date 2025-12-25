package com.corridasonline.api.dto.evento;

import jakarta.validation.constraints.NotNull;

public record TrajetoPontoRequest(
        @NotNull(message = "Ordem e obrigatoria")
        Integer ordem,

        @NotNull(message = "Latitude e obrigatoria")
        Double latitude,

        @NotNull(message = "Longitude e obrigatoria")
        Double longitude,

        String descricao,

        String tipoPonto // PARTIDA, INTERMEDIARIO, CHEGADA
) {}
