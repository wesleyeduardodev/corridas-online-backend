package com.corridasonline.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegistroAtletaRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "CPF é obrigatório")
        String cpf,

        String telefone,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @NotBlank(message = "Sexo é obrigatório")
        @Size(max = 1, message = "Sexo deve ser M ou F")
        String sexo
) {}
