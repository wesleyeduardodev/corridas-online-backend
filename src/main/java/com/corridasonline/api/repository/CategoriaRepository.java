package com.corridasonline.api.repository;

import com.corridasonline.api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByEventoIdOrderByDistanciaKmAsc(Long eventoId);

}
