package com.digitalers.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.digitalers.spring.boot.entidades.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
