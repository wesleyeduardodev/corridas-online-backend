package com.corridasonline.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscricoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"atleta_id", "evento_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atleta_id", nullable = false)
    private Atleta atleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "tamanho_camiseta", length = 5)
    private String tamanhoCamiseta;

    @Column(name = "contato_emergencia")
    private String contatoEmergencia;

    @Column(name = "telefone_emergencia", length = 20)
    private String telefoneEmergencia;

    @Column(name = "numero_peito")
    private Integer numeroPeito;

    @Column(name = "valor_pago", precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInscricao status = StatusInscricao.PENDENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_anterior")
    private StatusInscricao statusAnterior;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

}
