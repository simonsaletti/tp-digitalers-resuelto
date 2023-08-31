package com.digitalers.spring.boot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.digitalers.spring.boot.entidades.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Aquellos productos que tienen stock.
    public List<Producto> findByStockGreaterThan(Long stock);

    // Debe llamarse usando porcentajes.
    public List<Producto> findByDescripcionLike(String descParcial);

    // Traer todos los productos activos.
    public List<Producto> findAllByActive(boolean active);

    // Traer todos los productos activos y que tienen stock.
    public List<Producto> findAllByActiveAndStockGreaterThan(boolean active, Long stock);

    @Modifying
    @Query("UPDATE Producto prod SET prod.stock = prod.stock - :cantidad WHERE prod.id = :id")
    public void disminuirStock(@Param("id") Long idProducto, @Param("cantidad") Long cantidadComprada);

}
