package com.digitalers.spring.boot.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoRequestDTO implements Serializable {

    private static final long serialVersionUID = -3862902094819847521L;

    private Long id;
    // Validaciones (instalar la dependencia en el POM)
    @NotEmpty
    @Size(min = 3, max = 100)
    private String descripcion;
    @Min(value = 0)
    private BigDecimal precio;
    private String fechaCreacion;
    @Min(value = 0)
    private Long stock;
    private String nombreImagen;
    // private MultipartFile file;

}
