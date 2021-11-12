package com.digitalers.spring.boot.entidades.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.digitalers.spring.boot.entidades.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Aquellos productos que tienen stock.
    List<Producto> findByStockGreaterThan(Long stock);

    // Debe llamarse usando porcentajes.
    List<Producto> findByDescripcionLike(String descParcial);

    // Traer todos los productos activos.
    List<Producto> findAllByActive(boolean active);

}
