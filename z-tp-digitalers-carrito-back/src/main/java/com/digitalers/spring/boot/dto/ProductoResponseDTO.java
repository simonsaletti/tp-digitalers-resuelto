package com.digitalers.spring.boot.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoResponseDTO implements Serializable {

    private static final long serialVersionUID = -3862902094819847521L;

    private Long id;
    private String descripcion;
    private BigDecimal precio;
    private Long stock;
    private LocalDate fechaCreacion;
    private String nombreImagen;

}
