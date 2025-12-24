package com.corridasonline.api.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CategoriaRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Distância é obrigatória")
        @Positive(message = "Distância deve ser positiva")
        BigDecimal distanciaKm,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        BigDecimal valor,

        Integer limiteVagas
) {}
