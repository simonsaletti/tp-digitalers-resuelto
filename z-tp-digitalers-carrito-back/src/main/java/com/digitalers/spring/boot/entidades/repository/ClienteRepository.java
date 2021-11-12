package com.digitalers.spring.boot.entidades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.digitalers.spring.boot.entidades.Usuario;

@Repository
public interface ClienteRepository extends JpaRepository<Usuario, Long> {

}
