package com.corridasonline.api.security;

import com.corridasonline.api.entity.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioLogado {

    public Usuario get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Usuario) {
            return (Usuario) auth.getPrincipal();
        }
        return null;
    }

    public Long getId() {
        Usuario usuario = get();
        return usuario != null ? usuario.getId() : null;
    }

    public boolean isOrganizador() {
        Usuario usuario = get();
        return usuario != null && "ORGANIZADOR".equals(usuario.getRole().name());
    }

    public boolean isAtleta() {
        Usuario usuario = get();
        return usuario != null && "ATLETA".equals(usuario.getRole().name());
    }

    public boolean isAdmin() {
        Usuario usuario = get();
        return usuario != null && "ADMIN".equals(usuario.getRole().name());
    }

}
