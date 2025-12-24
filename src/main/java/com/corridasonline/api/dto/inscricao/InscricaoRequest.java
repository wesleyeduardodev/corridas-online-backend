package com.corridasonline.api.dto.inscricao;

import jakarta.validation.constraints.NotNull;

public record InscricaoRequest(
        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId,

        String tamanhoCamiseta,

        String contatoEmergencia,

        String telefoneEmergencia
) {}
