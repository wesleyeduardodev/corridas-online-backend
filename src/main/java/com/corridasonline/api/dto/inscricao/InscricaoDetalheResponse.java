package com.corridasonline.api.dto.inscricao;

import com.corridasonline.api.entity.Inscricao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InscricaoDetalheResponse(
        Long id,
        String atletaNome,
        String atletaEmail,
        String atletaCpf,
        String categoriaNome,
        BigDecimal categoriaDistanciaKm,
        String tamanhoCamiseta,
        String contatoEmergencia,
        String telefoneEmergencia,
        Integer numeroPeito,
        BigDecimal valorPago,
        String status,
        LocalDateTime dataPagamento,
        LocalDateTime dataInscricao
) {

    public static InscricaoDetalheResponse fromEntity(Inscricao inscricao) {
        return new InscricaoDetalheResponse(
                inscricao.getId(),
                inscricao.getAtleta().getNome(),
                inscricao.getAtleta().getUsuario().getEmail(),
                inscricao.getAtleta().getCpf(),
                inscricao.getCategoria().getNome(),
                inscricao.getCategoria().getDistanciaKm(),
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
