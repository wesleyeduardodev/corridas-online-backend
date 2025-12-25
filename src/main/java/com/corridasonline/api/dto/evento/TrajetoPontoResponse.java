package com.corridasonline.api.dto.evento;

import com.corridasonline.api.entity.EventoTrajeto;

public record TrajetoPontoResponse(
        Long id,
        Integer ordem,
        Double latitude,
        Double longitude,
        String descricao,
        String tipoPonto
) {

    public static TrajetoPontoResponse fromEntity(EventoTrajeto trajeto) {
        return new TrajetoPontoResponse(
                trajeto.getId(),
                trajeto.getOrdem(),
                trajeto.getLatitude(),
                trajeto.getLongitude(),
                trajeto.getDescricao(),
                trajeto.getTipoPonto()
        );
    }

}
