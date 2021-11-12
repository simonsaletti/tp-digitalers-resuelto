package com.digitalers.spring.boot.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    private static final long serialVersionUID = -3862902094819847521L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "stock")
    private Long stock;

    private String URLImagen;

    private Boolean active;

    @PrePersist
    public void preGuardar() {
        this.fechaCreacion = LocalDate.now();
    }

    public Producto(String descripcion, BigDecimal precio, Long stock, String uRLImagen) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        URLImagen = uRLImagen;
        this.active = true;
    }

}
