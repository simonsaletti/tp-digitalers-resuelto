
package com.digitalers.spring.boot.entidades.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.digitalers.spring.boot.dto.UsuarioResponseDTO;

// Debemos implementar la interfaz UserDetailsService, provista por Spring Security.
public interface UsuarioService extends UserDetailsService {

    public UsuarioResponseDTO obtener(Long id);

    public List<UsuarioResponseDTO> listarTodos();

}
