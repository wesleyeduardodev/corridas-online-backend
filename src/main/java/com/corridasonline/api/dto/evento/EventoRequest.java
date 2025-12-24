package com.corridasonline.api.dto.evento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        String descricao,

        @NotNull(message = "Data é obrigatória")
        LocalDate data,

        @NotNull(message = "Horário é obrigatório")
        LocalTime horario,

        @NotBlank(message = "Local é obrigatório")
        String local,

        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotNull(message = "Código IBGE da cidade é obrigatório")
        Integer cidadeIbge,

        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
        String estado,

        @NotNull(message = "Código IBGE do estado é obrigatório")
        Integer estadoIbge,

        String bannerUrl,

        String regulamentoUrl,

        Boolean inscricoesAbertas
) {}
