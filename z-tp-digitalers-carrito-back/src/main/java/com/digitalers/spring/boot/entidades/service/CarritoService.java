package com.digitalers.spring.boot.entidades.service;

import java.util.List;
import com.digitalers.spring.boot.dto.CarritoRequestDTO;
import com.digitalers.spring.boot.dto.CarritoResponseDTO;
import com.digitalers.spring.boot.entidades.Carrito;

public interface CarritoService {

    List<CarritoResponseDTO> listarTodos();

    public CarritoResponseDTO obtener(Long id);

    public Carrito insertar(CarritoRequestDTO dto);

}
