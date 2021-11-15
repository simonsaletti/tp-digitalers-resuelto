package com.digitalers.spring.boot.entidades.service;

import com.digitalers.spring.boot.dto.ProductoRequestDTO;
import com.digitalers.spring.boot.dto.ProductoResponseDTO;

public interface ProductoService extends CrudService<ProductoRequestDTO, ProductoResponseDTO> {

    public void disminuirStock(Long idProducto, Long cantidadComprada);
}
