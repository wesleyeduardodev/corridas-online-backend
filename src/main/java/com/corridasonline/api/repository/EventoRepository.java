package com.corridasonline.api.repository;

import com.corridasonline.api.entity.Evento;
import com.corridasonline.api.entity.StatusEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByOrganizadorIdOrderByDataDesc(Long organizadorId);

    List<Evento> findByOrganizadorIdAndStatusOrderByDataDesc(Long organizadorId, StatusEvento status);

    Optional<Evento> findByIdAndOrganizadorId(Long id, Long organizadorId);

    List<Evento> findByInscricoesAbertasTrueOrderByDataAsc();

    List<Evento> findByInscricoesAbertasTrueAndStatusOrderByDataAsc(StatusEvento status);

}
