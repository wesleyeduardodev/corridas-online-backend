package com.corridasonline.api.repository;

import com.corridasonline.api.entity.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {

    Optional<Organizador> findByUsuarioId(Long usuarioId);

    boolean existsByCpfCnpj(String cpfCnpj);

}
