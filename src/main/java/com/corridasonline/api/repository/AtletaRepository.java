package com.corridasonline.api.repository;

import com.corridasonline.api.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    Optional<Atleta> findByUsuarioId(Long usuarioId);

    boolean existsByCpf(String cpf);

}
