package com.corridasonline.api.dto.evento;

import com.corridasonline.api.entity.Evento;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResponse(
        Long id,
        String nome,
        String descricao,
        LocalDate data,
        LocalTime horario,
        String local,
        String cidade,
        Integer cidadeIbge,
        String estado,
        Integer estadoIbge,
        String bannerUrl,
        String regulamentoUrl,
        Boolean inscricoesAbertas,
        Boolean resultadosPublicados,
        String organizadorNome
) {

    public static EventoResponse fromEntity(Evento evento) {
        return new EventoResponse(
                evento.getId(),
                evento.getNome(),
                evento.getDescricao(),
                evento.getData(),
                evento.getHorario(),
                evento.getLocal(),
                evento.getCidade(),
                evento.getCidadeIbge(),
                evento.getEstado(),
                evento.getEstadoIbge(),
                evento.getBannerUrl(),
                evento.getRegulamentoUrl(),
                evento.getInscricoesAbertas(),
                evento.getResultadosPublicados(),
                evento.getOrganizador().getNome()
        );
    }

}
