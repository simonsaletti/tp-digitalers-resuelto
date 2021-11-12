package com.digitalers.spring.boot.entidades.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digitalers.spring.boot.dto.CarritoRequestDTO;
import com.digitalers.spring.boot.dto.CarritoResponseDTO;
import com.digitalers.spring.boot.entidades.Carrito;
import com.digitalers.spring.boot.entidades.Usuario;
import com.digitalers.spring.boot.entidades.ItemCarrito;
import com.digitalers.spring.boot.entidades.repository.CarritoRepository;
import com.digitalers.spring.boot.entidades.repository.ProductoRepository;
import com.digitalers.spring.boot.entidades.service.CarritoService;
import com.digitalers.spring.boot.entidades.service.ClienteService;
import com.digitalers.spring.boot.entidades.service.ProductoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    private CarritoRepository dao;

    private ProductoService productoService;

    private ClienteService clienteService;

    private ProductoRepository productoDAO;

    @Autowired
    public CarritoServiceImpl(CarritoRepository dao, ProductoService productoService, ClienteService clienteService, ProductoRepository productoDAO) {
        this.dao = dao;
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.productoDAO = productoDAO;
    }

    @Override
    public List<CarritoResponseDTO> listarTodos() {
        List<Carrito> registros = this.dao.findAll();
        return new ArrayList<CarritoResponseDTO>();
    }

    @Override
    @Transactional(readOnly = true)
    public CarritoResponseDTO obtener(Long id) {
        Carrito carrito = this.dao.findById(id).orElseThrow();
        CarritoResponseDTO response = new CarritoResponseDTO();
        return response;
    }

    @Override
    @Transactional
    public Carrito insertar(CarritoRequestDTO dto) {

        List<ItemCarrito> itemsCarrito = dto.getItems().stream()
                .map(itemCarritoDTO -> new ItemCarrito(itemCarritoDTO.getCantidad(), this.productoDAO.findById(itemCarritoDTO.getIdProducto()).orElse(null)))
                .collect(Collectors.toList());

        Usuario cliente = this.clienteService.obtener(dto.getIdCliente());

        Carrito entidad = new Carrito(dto.getDescripcion(), dto.getObservacion(), cliente, itemsCarrito);
        return this.dao.save(entidad);
    }

}
