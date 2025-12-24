package com.corridasonline.api.service;

import com.corridasonline.api.dto.auth.LoginRequest;
import com.corridasonline.api.dto.auth.LoginResponse;
import com.corridasonline.api.dto.auth.RegistroAtletaRequest;
import com.corridasonline.api.dto.auth.RegistroOrganizadorRequest;
import com.corridasonline.api.entity.Atleta;
import com.corridasonline.api.entity.Organizador;
import com.corridasonline.api.entity.Role;
import com.corridasonline.api.entity.Usuario;
import com.corridasonline.api.exception.BusinessException;
import com.corridasonline.api.repository.AtletaRepository;
import com.corridasonline.api.repository.OrganizadorRepository;
import com.corridasonline.api.repository.UsuarioRepository;
import com.corridasonline.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final OrganizadorRepository organizadorRepository;
    private final AtletaRepository atletaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new BusinessException("Email ou senha inválidos");
        }

        if (!usuario.getAtivo()) {
            throw new BusinessException("Usuário inativo");
        }

        String token = jwtService.gerarToken(usuario);
        String nome = buscarNome(usuario);

        return new LoginResponse(token, usuario.getEmail(), usuario.getRole().name(), nome);
    }

    @Transactional
    public LoginResponse registrarOrganizador(RegistroOrganizadorRequest request) {
        validarEmailDisponivel(request.email());
        validarCpfCnpjDisponivel(request.cpfCnpj());

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setRole(Role.ORGANIZADOR);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);

        Organizador organizador = new Organizador();
        organizador.setUsuario(usuario);
        organizador.setNome(request.nome());
        organizador.setCpfCnpj(request.cpfCnpj());
        organizador.setTelefone(request.telefone());
        organizadorRepository.save(organizador);

        String token = jwtService.gerarToken(usuario);
        return new LoginResponse(token, usuario.getEmail(), usuario.getRole().name(), organizador.getNome());
    }

    @Transactional
    public LoginResponse registrarAtleta(RegistroAtletaRequest request) {
        validarEmailDisponivel(request.email());
        validarCpfDisponivel(request.cpf());

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setRole(Role.ATLETA);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);

        Atleta atleta = new Atleta();
        atleta.setUsuario(usuario);
        atleta.setNome(request.nome());
        atleta.setCpf(request.cpf());
        atleta.setTelefone(request.telefone());
        atleta.setDataNascimento(request.dataNascimento());
        atleta.setSexo(request.sexo());
        atleta.setCidade(request.cidade());
        atleta.setCidadeIbge(request.cidadeIbge());
        atleta.setEstado(request.estado() != null ? request.estado().toUpperCase() : null);
        atleta.setEstadoIbge(request.estadoIbge());
        atletaRepository.save(atleta);

        String token = jwtService.gerarToken(usuario);
        return new LoginResponse(token, usuario.getEmail(), usuario.getRole().name(), atleta.getNome());
    }

    private String buscarNome(Usuario usuario) {
        return switch (usuario.getRole()) {
            case ORGANIZADOR -> organizadorRepository.findByUsuarioId(usuario.getId())
                    .map(Organizador::getNome)
                    .orElse("");
            case ATLETA -> atletaRepository.findByUsuarioId(usuario.getId())
                    .map(Atleta::getNome)
                    .orElse("");
            case ADMIN -> "Administrador";
        };
    }

    private void validarEmailDisponivel(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new BusinessException("Email já cadastrado");
        }
    }

    private void validarCpfCnpjDisponivel(String cpfCnpj) {
        if (organizadorRepository.existsByCpfCnpj(cpfCnpj)) {
            throw new BusinessException("CPF/CNPJ já cadastrado");
        }
    }

    private void validarCpfDisponivel(String cpf) {
        if (atletaRepository.existsByCpf(cpf)) {
            throw new BusinessException("CPF já cadastrado");
        }
    }

}
