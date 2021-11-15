package com.digitalers.spring.boot.entidades.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.digitalers.spring.boot.entidades.Usuario;

/*
 * Debemos implementar la interfaz UserDetailsService, provista por Spring Security.
 */
public interface UsuarioService extends UserDetailsService {

    public Usuario obtener(String username);

}
