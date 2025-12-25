package com.corridasonline.api.service;

import com.corridasonline.api.dto.inscricao.InscricaoDetalheResponse;
import com.corridasonline.api.dto.inscricao.InscricaoRequest;
import com.corridasonline.api.dto.inscricao.InscricaoResponse;
import com.corridasonline.api.entity.Atleta;
import com.corridasonline.api.entity.Categoria;
import com.corridasonline.api.entity.Evento;
import com.corridasonline.api.entity.Inscricao;
import com.corridasonline.api.entity.Organizador;
import com.corridasonline.api.entity.StatusInscricao;
import com.corridasonline.api.exception.AccessDeniedException;
import com.corridasonline.api.exception.BusinessException;
import com.corridasonline.api.exception.ResourceNotFoundException;
import com.corridasonline.api.repository.AtletaRepository;
import com.corridasonline.api.repository.CategoriaRepository;
import com.corridasonline.api.repository.EventoRepository;
import com.corridasonline.api.repository.InscricaoRepository;
import com.corridasonline.api.repository.OrganizadorRepository;
import com.corridasonline.api.security.UsuarioLogado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository;
    private final AtletaRepository atletaRepository;
    private final OrganizadorRepository organizadorRepository;
    private final UsuarioLogado usuarioLogado;

    @Transactional
    public InscricaoResponse inscrever(Long eventoId, InscricaoRequest request) {
        Atleta atleta = getAtletaLogado();
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", eventoId));

        validarInscricao(atleta, evento, request.categoriaId());

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", request.categoriaId()));

        Inscricao inscricao = new Inscricao();
        inscricao.setAtleta(atleta);
        inscricao.setEvento(evento);
        inscricao.setCategoria(categoria);
        inscricao.setTamanhoCamiseta(request.tamanhoCamiseta());
        inscricao.setContatoEmergencia(request.contatoEmergencia());
        inscricao.setTelefoneEmergencia(request.telefoneEmergencia());
        inscricao.setValorPago(categoria.getValor());
        inscricao.setStatus(StatusInscricao.PENDENTE);

        decrementarVagas(categoria);
        inscricaoRepository.save(inscricao);

        return InscricaoResponse.fromEntity(inscricao);
    }

    public List<InscricaoResponse> minhasInscricoes() {
        Atleta atleta = getAtletaLogado();
        return inscricaoRepository.findByAtletaIdOrderByCreatedAtDesc(atleta.getId())
                .stream()
                .map(InscricaoResponse::fromEntity)
                .toList();
    }

    public InscricaoResponse buscarMinhaInscricao(Long inscricaoId) {
        Atleta atleta = getAtletaLogado();
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .filter(i -> i.getAtleta().getId().equals(atleta.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição", inscricaoId));
        return InscricaoResponse.fromEntity(inscricao);
    }

    @Transactional
    public void cancelarInscricao(Long inscricaoId) {
        Atleta atleta = getAtletaLogado();
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .filter(i -> i.getAtleta().getId().equals(atleta.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição", inscricaoId));

        if (inscricao.getStatus() == StatusInscricao.CANCELADO) {
            throw new BusinessException("Inscrição já está cancelada");
        }

        inscricao.setStatus(StatusInscricao.CANCELADO);
        incrementarVagas(inscricao.getCategoria());
        inscricaoRepository.save(inscricao);
    }

    public List<InscricaoDetalheResponse> listarInscricoesDoEvento(Long eventoId) {
        validarEventoDoOrganizador(eventoId);
        return inscricaoRepository.findByEventoIdOrderByCreatedAtDesc(eventoId)
                .stream()
                .map(InscricaoDetalheResponse::fromEntity)
                .toList();
    }

    public List<InscricaoDetalheResponse> listarInscricoesPorStatus(Long eventoId, String status) {
        validarEventoDoOrganizador(eventoId);
        StatusInscricao statusEnum = parseStatus(status);
        return inscricaoRepository.findByEventoIdAndStatus(eventoId, statusEnum)
                .stream()
                .map(InscricaoDetalheResponse::fromEntity)
                .toList();
    }

    private StatusInscricao parseStatus(String status) {
        try {
            return StatusInscricao.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Status invalido: " + status);
        }
    }

    @Transactional
    public InscricaoDetalheResponse confirmarPagamento(Long eventoId, Long inscricaoId) {
        validarEventoDoOrganizador(eventoId);
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .filter(i -> i.getEvento().getId().equals(eventoId))
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição", inscricaoId));

        if (inscricao.getStatus() == StatusInscricao.PAGO) {
            throw new BusinessException("Pagamento já confirmado");
        }

        inscricao.setStatus(StatusInscricao.PAGO);
        inscricao.setDataPagamento(LocalDateTime.now());
        inscricaoRepository.save(inscricao);

        return InscricaoDetalheResponse.fromEntity(inscricao);
    }

    @Transactional
    public InscricaoDetalheResponse atribuirNumeroPeito(Long eventoId, Long inscricaoId, Integer numeroPeito) {
        validarEventoDoOrganizador(eventoId);
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .filter(i -> i.getEvento().getId().equals(eventoId))
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição", inscricaoId));

        inscricao.setNumeroPeito(numeroPeito);
        inscricaoRepository.save(inscricao);

        return InscricaoDetalheResponse.fromEntity(inscricao);
    }

    private void validarInscricao(Atleta atleta, Evento evento, Long categoriaId) {
        if (!evento.getInscricoesAbertas()) {
            throw new BusinessException("Inscrições fechadas para este evento");
        }

        if (inscricaoRepository.existsByAtletaIdAndEventoId(atleta.getId(), evento.getId())) {
            throw new BusinessException("Você já está inscrito neste evento");
        }

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", categoriaId));

        if (!categoria.getEvento().getId().equals(evento.getId())) {
            throw new BusinessException("Categoria não pertence a este evento");
        }

        if (categoria.getLimiteVagas() != null && categoria.getVagasDisponiveis() <= 0) {
            throw new BusinessException("Não há vagas disponíveis nesta categoria");
        }
    }

    private void decrementarVagas(Categoria categoria) {
        if (categoria.getVagasDisponiveis() != null) {
            categoria.setVagasDisponiveis(categoria.getVagasDisponiveis() - 1);
            categoriaRepository.save(categoria);
        }
    }

    private void incrementarVagas(Categoria categoria) {
        if (categoria.getVagasDisponiveis() != null && categoria.getLimiteVagas() != null) {
            categoria.setVagasDisponiveis(categoria.getVagasDisponiveis() + 1);
            categoriaRepository.save(categoria);
        }
    }

    private Atleta getAtletaLogado() {
        Long usuarioId = usuarioLogado.getId();
        return atletaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new AccessDeniedException("Usuário não é um atleta"));
    }

    private void validarEventoDoOrganizador(Long eventoId) {
        Organizador organizador = organizadorRepository.findByUsuarioId(usuarioLogado.getId())
                .orElseThrow(() -> new AccessDeniedException("Usuário não é um organizador"));
        eventoRepository.findByIdAndOrganizadorId(eventoId, organizador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento", eventoId));
    }

}
