package com.corridasonline.api.dto.inscricao;

import com.corridasonline.api.entity.Inscricao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record InscricaoResponse(
        Long id,
        Long eventoId,
        String eventoNome,
        LocalDate eventoData,
        String eventoLocal,
        String eventoCidade,
        String eventoEstado,
        Long categoriaId,
        String categoriaNome,
        BigDecimal categoriaDistanciaKm,
        BigDecimal valor,
        String tamanhoCamiseta,
        String contatoEmergencia,
        String telefoneEmergencia,
        Integer numeroInscricao,
        BigDecimal valorPago,
        String status,
        LocalDateTime dataPagamento,
        LocalDateTime dataInscricao
) {

    public static InscricaoResponse fromEntity(Inscricao inscricao) {
        return new InscricaoResponse(
                inscricao.getId(),
                inscricao.getEvento().getId(),
                inscricao.getEvento().getNome(),
                inscricao.getEvento().getData(),
                inscricao.getEvento().getLocal(),
                inscricao.getEvento().getCidade(),
                inscricao.getEvento().getEstado(),
                inscricao.getCategoria().getId(),
                inscricao.getCategoria().getNome(),
                inscricao.getCategoria().getDistanciaKm(),
                inscricao.getCategoria().getValor(),
                inscricao.getTamanhoCamiseta(),
                inscricao.getContatoEmergencia(),
                inscricao.getTelefoneEmergencia(),
                inscricao.getNumeroPeito(),
                inscricao.getValorPago(),
                inscricao.getStatus().name(),
                inscricao.getDataPagamento(),
                inscricao.getCreatedAt()
        );
    }

}
