package com.corridasonline.api.client;

import com.corridasonline.api.dto.localidade.CidadeResponse;
import com.corridasonline.api.dto.localidade.EstadoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class IbgeClient {

    private static final String IBGE_BASE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades";

    private final RestClient restClient;

    public IbgeClient() {
        this.restClient = RestClient.builder()
                .baseUrl(IBGE_BASE_URL)
                .build();
    }

    public List<EstadoResponse> buscarEstados() {
        return restClient.get()
                .uri("/estados?orderBy=nome")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<CidadeResponse> buscarCidadesPorEstado(String uf) {
        return restClient.get()
                .uri("/estados/{uf}/municipios?orderBy=nome", uf.toUpperCase())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
