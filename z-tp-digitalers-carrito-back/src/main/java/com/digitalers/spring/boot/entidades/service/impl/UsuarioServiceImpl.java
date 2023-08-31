package com.digitalers.spring.boot.entidades.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digitalers.spring.boot.dto.UsuarioResponseDTO;
import com.digitalers.spring.boot.entidades.Usuario;
import com.digitalers.spring.boot.entidades.service.UsuarioService;
import com.digitalers.spring.boot.mappers.UsuarioMapper;
import com.digitalers.spring.boot.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    // Implementamos el método de la interfaz UserDetailsService, provista por Spring Security.
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	
    	logger.info("Buscando usuario ---> '" + username + "'.");

        // 1) Buscamos el usuario en nuestro sistema.
        Usuario usuario = this.usuarioRepository.findByUsername(username);

        // 2) Si el usuario no existe, entonces lanzamos la excepción que trae el método que sobrescribimos.
        if (usuario == null) {
            final String msjError = "El usuario '" + username + "' no existe en el sistema.";
            logger.error(msjError);
            throw new UsernameNotFoundException(msjError);
        }

        logger.info("Usuario '" + username + "' encontrado correctamente.");

        // 3) El constructor del objeto que vamos a devolver, nos pedirá los roles que tiene el usuario.
        // Necesitamos hacer una conversión de objetos de tipo Role a tipo GrantedAuthority.
        List<GrantedAuthority> authorities = usuario.getRoles().stream().map(role -> {
            logger.info("Role: " + role.getNombre());
            return new SimpleGrantedAuthority(role.getNombre());
        }).collect(Collectors.toList());

        // 4) Este método devuelve una intefaz, pero nosotros vamos devolver un objeto de tipo User, que es
        // una implementación concreta.

        String passEncriptada = encoder.encode(usuario.getPassword());
        User user = new User(usuario.getUsername(), passEncriptada, usuario.getEnabled(), true, true, true, authorities);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtener(Long id) {
        Usuario usuario = this.usuarioRepository.findById(id).orElseThrow();
        UsuarioResponseDTO usuarioResponseDTO = this.usuarioMapper.fromUsuarioToUsuarioResponseDTO(usuario);
        return usuarioResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = this.usuarioRepository.findAll();
        List<UsuarioResponseDTO> usuarioResponseDTOs = this.usuarioMapper.fromUsuariosToUsuarioResponseDTOs(usuarios);
        return usuarioResponseDTOs;
    }

}
