package com.digitalers.spring.boot.entidades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.digitalers.spring.boot.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Forma 1 para encontrar un usuario, según su username.
    public Usuario findByUsername(String username);

    // Forma 2 para encontrar un usuario, según su username.
    // Si tuviésemos otro parámetro, tendríamos que usar ?2 y así sucesivamente.
    @Query("FROM Usuario us WHERE us.username = ?1")
    public Usuario findByUsernameAlternativaDos(String username);

    // Forma 3 para encontrar un usuario, según su username.
    @Query("FROM Usuario us WHERE us.username = :usernameParam")
    public Usuario findByUsernameAlternativaTres(@Param("usernameParam") String username);

}
