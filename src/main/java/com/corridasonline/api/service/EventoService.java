package com.corridasonline.api.service;

import com.corridasonline.api.dto.evento.EventoRequest;
import com.corridasonline.api.dto.evento.EventoResponse;
import com.corridasonline.api.entity.Evento;
import com.corridasonline.api.entity.Organizador;
import com.corridasonline.api.exception.AccessDeniedException;
import com.corridasonline.api.exception.ResourceNotFoundException;
import com.corridasonline.api.repository.EventoRepository;
import com.corridasonline.api.repository.OrganizadorRepository;
import com.corridasonline.api.security.UsuarioLogado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final OrganizadorRepository organizadorRepository;
    private final UsuarioLogado usuarioLogado;

    public List<EventoResponse> listarMeusEventos() {
        Organizador organizador = getOrganizadorLogado();
        return eventoRepository.findByOrganizadorIdOrderByDataDesc(organizador.getId())
                .stream()
                .map(EventoResponse::fromEntity)
                .toList();
    }

    public EventoResponse buscarPorId(Long id) {
        Evento evento = getEventoDoOrganizador(id);
        return EventoResponse.fromEntity(evento);
    }

    @Transactional
    public EventoResponse criar(EventoRequest request) {
        Organizador organizador = getOrganizadorLogado();

        Evento evento = new Evento();
        evento.setOrganizador(organizador);
        preencherDados(evento, request);
        evento.setInscricoesAbertas(request.inscricoesAbertas() != null ? request.inscricoesAbertas() : false);
        evento.setResultadosPublicados(false);

        eventoRepository.save(evento);
        return EventoResponse.fromEntity(evento);
    }

    @Transactional
    public EventoResponse atualizar(Long id, EventoRequest request) {
        Evento evento = getEventoDoOrganizador(id);
        preencherDados(evento, request);
        eventoRepository.save(evento);
        return EventoResponse.fromEntity(evento);
    }

    @Transactional
    public void excluir(Long id) {
        Evento evento = getEventoDoOrganizador(id);
        eventoRepository.delete(evento);
    }

    @Transactional
    public EventoResponse abrirInscricoes(Long id) {
        Evento evento = getEventoDoOrganizador(id);
        evento.setInscricoesAbertas(true);
        eventoRepository.save(evento);
        return EventoResponse.fromEntity(evento);
    }

    @Transactional
    public EventoResponse fecharInscricoes(Long id) {
        Evento evento = getEventoDoOrganizador(id);
        evento.setInscricoesAbertas(false);
        eventoRepository.save(evento);
        return EventoResponse.fromEntity(evento);
    }

    public List<EventoResponse> listarEventosPublicos() {
        return eventoRepository.findByInscricoesAbertasTrueOrderByDataAsc()
                .stream()
                .map(EventoResponse::fromEntity)
                .toList();
    }

    public EventoResponse buscarEventoPublico(Long id) {
        Evento evento = eventoRepository.findById(id)
                .filter(Evento::getInscricoesAbertas)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
        return EventoResponse.fromEntity(evento);
    }

    private void preencherDados(Evento evento, EventoRequest request) {
        evento.setNome(request.nome());
        evento.setDescricao(request.descricao());
        evento.setData(request.data());
        evento.setHorario(request.horario());
        evento.setLocal(request.local());
        evento.setCidade(request.cidade());
        evento.setCidadeIbge(request.cidadeIbge());
        evento.setEstado(request.estado().toUpperCase());
        evento.setEstadoIbge(request.estadoIbge());
        evento.setBannerUrl(request.bannerUrl());
        evento.setRegulamentoUrl(request.regulamentoUrl());
        evento.setLimiteInscricoes(request.limiteInscricoes());
        evento.setTrajetoUrl(request.trajetoUrl());
        if (request.inscricoesAbertas() != null) {
            evento.setInscricoesAbertas(request.inscricoesAbertas());
        }
    }

    private Organizador getOrganizadorLogado() {
        Long usuarioId = usuarioLogado.getId();
        return organizadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new AccessDeniedException("Usuário não é um organizador"));
    }

    private Evento getEventoDoOrganizador(Long eventoId) {
        Organizador organizador = getOrganizadorLogado();
        return eventoRepository.findByIdAndOrganizadorId(eventoId, organizador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento", eventoId));
    }

}
