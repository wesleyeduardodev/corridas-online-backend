package com.corridasonline.api.service;

import com.corridasonline.api.dto.categoria.CategoriaRequest;
import com.corridasonline.api.dto.categoria.CategoriaResponse;
import com.corridasonline.api.entity.Categoria;
import com.corridasonline.api.entity.Evento;
import com.corridasonline.api.entity.Organizador;
import com.corridasonline.api.exception.AccessDeniedException;
import com.corridasonline.api.exception.ResourceNotFoundException;
import com.corridasonline.api.repository.CategoriaRepository;
import com.corridasonline.api.repository.EventoRepository;
import com.corridasonline.api.repository.OrganizadorRepository;
import com.corridasonline.api.security.UsuarioLogado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final EventoRepository eventoRepository;
    private final OrganizadorRepository organizadorRepository;
    private final UsuarioLogado usuarioLogado;

    public List<CategoriaResponse> listarPorEvento(Long eventoId) {
        validarEventoDoOrganizador(eventoId);
        return categoriaRepository.findByEventoIdOrderByDistanciaKmAsc(eventoId)
                .stream()
                .map(CategoriaResponse::fromEntity)
                .toList();
    }

    public CategoriaResponse buscarPorId(Long eventoId, Long categoriaId) {
        Categoria categoria = getCategoriaDoEvento(eventoId, categoriaId);
        return CategoriaResponse.fromEntity(categoria);
    }

    @Transactional
    public CategoriaResponse criar(Long eventoId, CategoriaRequest request) {
        Evento evento = validarEventoDoOrganizador(eventoId);

        Categoria categoria = new Categoria();
        categoria.setEvento(evento);
        preencherDados(categoria, request);

        categoriaRepository.save(categoria);
        return CategoriaResponse.fromEntity(categoria);
    }

    @Transactional
    public CategoriaResponse atualizar(Long eventoId, Long categoriaId, CategoriaRequest request) {
        Categoria categoria = getCategoriaDoEvento(eventoId, categoriaId);
        preencherDados(categoria, request);
        categoriaRepository.save(categoria);
        return CategoriaResponse.fromEntity(categoria);
    }

    @Transactional
    public void excluir(Long eventoId, Long categoriaId) {
        Categoria categoria = getCategoriaDoEvento(eventoId, categoriaId);
        categoriaRepository.delete(categoria);
    }

    public List<CategoriaResponse> listarCategoriasPublicas(Long eventoId) {
        return categoriaRepository.findByEventoIdOrderByDistanciaKmAsc(eventoId)
                .stream()
                .map(CategoriaResponse::fromEntity)
                .toList();
    }

    private void preencherDados(Categoria categoria, CategoriaRequest request) {
        categoria.setNome(request.nome());
        categoria.setDistanciaKm(request.distanciaKm());
        categoria.setValor(request.valor());
        categoria.setLimiteVagas(request.limiteVagas());
        categoria.setVagasDisponiveis(request.limiteVagas());
    }

    private Evento validarEventoDoOrganizador(Long eventoId) {
        Organizador organizador = getOrganizadorLogado();
        return eventoRepository.findByIdAndOrganizadorId(eventoId, organizador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento", eventoId));
    }

    private Categoria getCategoriaDoEvento(Long eventoId, Long categoriaId) {
        validarEventoDoOrganizador(eventoId);
        return categoriaRepository.findById(categoriaId)
                .filter(c -> c.getEvento().getId().equals(eventoId))
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", categoriaId));
    }

    private Organizador getOrganizadorLogado() {
        Long usuarioId = usuarioLogado.getId();
        return organizadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new AccessDeniedException("Usuário não é um organizador"));
    }

}
