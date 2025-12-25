package com.corridasonline.api.service;

import com.corridasonline.api.dto.evento.TrajetoPontoRequest;
import com.corridasonline.api.dto.evento.TrajetoPontoResponse;
import com.corridasonline.api.entity.Evento;
import com.corridasonline.api.entity.EventoTrajeto;
import com.corridasonline.api.entity.Organizador;
import com.corridasonline.api.exception.AccessDeniedException;
import com.corridasonline.api.exception.ResourceNotFoundException;
import com.corridasonline.api.repository.EventoRepository;
import com.corridasonline.api.repository.EventoTrajetoRepository;
import com.corridasonline.api.repository.OrganizadorRepository;
import com.corridasonline.api.security.UsuarioLogado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoTrajetoService {

    private final EventoTrajetoRepository trajetoRepository;
    private final EventoRepository eventoRepository;
    private final OrganizadorRepository organizadorRepository;
    private final UsuarioLogado usuarioLogado;

    public List<TrajetoPontoResponse> listarPontos(Long eventoId) {
        return trajetoRepository.findByEventoIdOrderByOrdemAsc(eventoId)
                .stream()
                .map(TrajetoPontoResponse::fromEntity)
                .toList();
    }

    public List<TrajetoPontoResponse> listarPontosPublico(Long eventoId) {
        eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", eventoId));
        return trajetoRepository.findByEventoIdOrderByOrdemAsc(eventoId)
                .stream()
                .map(TrajetoPontoResponse::fromEntity)
                .toList();
    }

    @Transactional
    public List<TrajetoPontoResponse> salvarTrajeto(Long eventoId, List<TrajetoPontoRequest> pontos) {
        Evento evento = getEventoDoOrganizador(eventoId);

        // Remover pontos existentes
        trajetoRepository.deleteByEventoId(eventoId);

        // Salvar novos pontos
        for (TrajetoPontoRequest ponto : pontos) {
            EventoTrajeto trajeto = new EventoTrajeto();
            trajeto.setEvento(evento);
            trajeto.setOrdem(ponto.ordem());
            trajeto.setLatitude(ponto.latitude());
            trajeto.setLongitude(ponto.longitude());
            trajeto.setDescricao(ponto.descricao());
            trajeto.setTipoPonto(ponto.tipoPonto());
            trajetoRepository.save(trajeto);
        }

        return listarPontos(eventoId);
    }

    @Transactional
    public void limparTrajeto(Long eventoId) {
        getEventoDoOrganizador(eventoId);
        trajetoRepository.deleteByEventoId(eventoId);
    }

    public Double calcularDistanciaTotal(Long eventoId) {
        List<EventoTrajeto> pontos = trajetoRepository.findByEventoIdOrderByOrdemAsc(eventoId);

        if (pontos.size() < 2) {
            return 0.0;
        }

        double distanciaTotal = 0.0;
        for (int i = 0; i < pontos.size() - 1; i++) {
            EventoTrajeto p1 = pontos.get(i);
            EventoTrajeto p2 = pontos.get(i + 1);
            distanciaTotal += calcularDistanciaHaversine(
                    p1.getLatitude(), p1.getLongitude(),
                    p2.getLatitude(), p2.getLongitude()
            );
        }

        return Math.round(distanciaTotal * 100.0) / 100.0; // Arredondar para 2 casas
    }

    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raio da Terra em km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private Organizador getOrganizadorLogado() {
        Long usuarioId = usuarioLogado.getId();
        return organizadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new AccessDeniedException("Usuario nao e um organizador"));
    }

    private Evento getEventoDoOrganizador(Long eventoId) {
        Organizador organizador = getOrganizadorLogado();
        return eventoRepository.findByIdAndOrganizadorId(eventoId, organizador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento", eventoId));
    }

}
