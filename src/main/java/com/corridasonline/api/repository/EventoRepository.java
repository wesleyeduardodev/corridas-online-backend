package com.corridasonline.api.repository;

import com.corridasonline.api.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByOrganizadorIdOrderByDataDesc(Long organizadorId);

    Optional<Evento> findByIdAndOrganizadorId(Long id, Long organizadorId);

    List<Evento> findByInscricoesAbertasTrueOrderByDataAsc();

}
