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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digitalers.spring.boot.entidades.Usuario;
import com.digitalers.spring.boot.entidades.repository.UsuarioRepository;
import com.digitalers.spring.boot.entidades.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Implementamos el método de la interfaz UserDetailsService, provista por Spring Security.
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

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
        User user = new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtener(String username) {
        Usuario usuario = this.usuarioRepository.findByUsername(username);
        return usuario;
    }

}
