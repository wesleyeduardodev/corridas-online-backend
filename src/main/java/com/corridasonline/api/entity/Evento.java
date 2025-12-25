package com.corridasonline.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Organizador organizador;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(name = "cidade_ibge")
    private Integer cidadeIbge;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(name = "estado_ibge")
    private Integer estadoIbge;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "regulamento_url")
    private String regulamentoUrl;

    @Column(name = "inscricoes_abertas", nullable = false)
    private Boolean inscricoesAbertas = false;

    @Column(name = "limite_inscricoes")
    private Integer limiteInscricoes;

    @Column(name = "trajeto_url")
    private String trajetoUrl;

    @Column(name = "resultados_publicados", nullable = false)
    private Boolean resultadosPublicados = false;

}
