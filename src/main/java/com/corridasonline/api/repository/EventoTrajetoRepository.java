package com.corridasonline.api.repository;

import com.corridasonline.api.entity.EventoTrajeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoTrajetoRepository extends JpaRepository<EventoTrajeto, Long> {

    List<EventoTrajeto> findByEventoIdOrderByOrdemAsc(Long eventoId);

    void deleteByEventoId(Long eventoId);

}
