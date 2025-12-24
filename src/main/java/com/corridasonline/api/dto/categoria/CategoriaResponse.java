package com.corridasonline.api.dto.categoria;

import com.corridasonline.api.entity.Categoria;

import java.math.BigDecimal;

public record CategoriaResponse(
        Long id,
        Long eventoId,
        String nome,
        BigDecimal distanciaKm,
        BigDecimal valor,
        Integer limiteVagas,
        Integer vagasDisponiveis
) {

    public static CategoriaResponse fromEntity(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getEvento().getId(),
                categoria.getNome(),
                categoria.getDistanciaKm(),
                categoria.getValor(),
                categoria.getLimiteVagas(),
                categoria.getVagasDisponiveis()
        );
    }

}
