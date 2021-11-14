package com.digitalers.spring.boot.entidades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalers.spring.boot.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	@Query("from Usuario us where us.username = ?1")
	public Usuario findByUsernameAlternativaDos(String username);

}
