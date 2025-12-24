package com.corridasonline.api.service;

import com.corridasonline.api.client.IbgeClient;
import com.corridasonline.api.dto.localidade.CidadeResponse;
import com.corridasonline.api.dto.localidade.EstadoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalidadeService {

    private final IbgeClient ibgeClient;

    @Cacheable("estados")
    public List<EstadoResponse> listarEstados() {
        return ibgeClient.buscarEstados();
    }

    @Cacheable(value = "cidades", key = "#uf.toUpperCase()")
    public List<CidadeResponse> listarCidadesPorEstado(String uf) {
        return ibgeClient.buscarCidadesPorEstado(uf);
    }

    public EstadoResponse buscarEstadoPorId(Integer estadoIbge) {
        return listarEstados().stream()
                .filter(e -> e.id().equals(estadoIbge))
                .findFirst()
                .orElse(null);
    }

    public EstadoResponse buscarEstadoPorSigla(String sigla) {
        return listarEstados().stream()
                .filter(e -> e.sigla().equalsIgnoreCase(sigla))
                .findFirst()
                .orElse(null);
    }

    public CidadeResponse buscarCidadePorId(String uf, Integer cidadeIbge) {
        return listarCidadesPorEstado(uf).stream()
                .filter(c -> c.id().equals(cidadeIbge))
                .findFirst()
                .orElse(null);
    }
}
