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
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Buscando usuario ---> " + username + ".");
		Usuario usuario = this.usuarioRepository.findByUsername(username);
		if(usuario == null) {
			final String msjError = "El usuario " + username + " no existe en el sistema.";
			logger.error(msjError);
			throw new UsernameNotFoundException(msjError);
		}
		logger.info("Usuario encontrado correctamente.");
		List<GrantedAuthority> roles = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				// Usamos peek para loggear.
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		User user = new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, roles); 
		
		return user;
	}

}
