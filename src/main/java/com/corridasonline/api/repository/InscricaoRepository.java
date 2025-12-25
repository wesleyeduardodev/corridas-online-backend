package com.corridasonline.api.repository;

import com.corridasonline.api.entity.Inscricao;
import com.corridasonline.api.entity.StatusInscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByAtletaIdOrderByCreatedAtDesc(Long atletaId);

    List<Inscricao> findByEventoIdOrderByCreatedAtDesc(Long eventoId);

    List<Inscricao> findByEventoIdAndStatus(Long eventoId, StatusInscricao status);

    Optional<Inscricao> findByAtletaIdAndEventoId(Long atletaId, Long eventoId);

    boolean existsByAtletaIdAndEventoId(Long atletaId, Long eventoId);

    long countByEventoIdAndStatus(Long eventoId, StatusInscricao status);

    List<Inscricao> findByEventoIdAndStatusIn(Long eventoId, List<StatusInscricao> statuses);

    boolean existsByEventoId(Long eventoId);

}
