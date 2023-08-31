package com.digitalers.spring.boot.entidades.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digitalers.spring.boot.dto.ProductoRequestDTO;
import com.digitalers.spring.boot.dto.ProductoResponseDTO;
import com.digitalers.spring.boot.entidades.Producto;
import com.digitalers.spring.boot.entidades.service.ProductoService;
import com.digitalers.spring.boot.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    private ProductoRepository dao;

    @Autowired
    public ProductoServiceImpl(ProductoRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<ProductoResponseDTO> listarTodos() {
        List<Producto> productos = this.dao.findAllByActiveAndStockGreaterThan(true, 0L);
        // List<Producto> productos = this.dao.findByDescripcionLike("%Apple%");

        List<ProductoResponseDTO> productosDTO = new ArrayList<>();

        for (Producto producto : productos) {
            String URLImagen = producto.getURLImagen() != null ? producto.getURLImagen() : "no-image.png";
            ProductoResponseDTO productoDTO = new ProductoResponseDTO(producto.getId(), producto.getDescripcion(), producto.getPrecio(), producto.getStock(),
                    producto.getFechaCreacion(), URLImagen);
            productosDTO.add(productoDTO);
        }
        return productosDTO;
    }

    @Override
    public ProductoResponseDTO obtener(Long id) {
        Producto producto = this.dao.findById(id).orElseThrow();
        ProductoResponseDTO productoDTO = new ProductoResponseDTO(producto.getId(), producto.getDescripcion(), producto.getPrecio(), producto.getStock(),
                producto.getFechaCreacion(), producto.getURLImagen());
        return productoDTO;
    }

    @Override
    public ProductoResponseDTO insertar(ProductoRequestDTO reqDTO) {
        Producto producto;
        if (reqDTO.getId() != null) {
            producto = this.dao.findById(reqDTO.getId()).orElseThrow();
            producto.setDescripcion(reqDTO.getDescripcion());
            producto.setPrecio(reqDTO.getPrecio());
            producto.setStock(reqDTO.getStock());
            producto.setURLImagen(reqDTO.getNombreImagen());
        } else {
            producto = new Producto(reqDTO.getDescripcion(), reqDTO.getPrecio(), reqDTO.getStock(), reqDTO.getNombreImagen());

        }

        this.dao.save(producto);

        return new ProductoResponseDTO(producto.getId(), producto.getDescripcion(), producto.getPrecio(), producto.getStock(), producto.getFechaCreacion(),
                producto.getURLImagen());
    }

    @Override
    public void borrar(Long id) {
        this.dao.deleteById(id);
    }

    @Override
    @Transactional
    public void disminuirStock(Long idProducto, Long cantidadComprada) {
        this.dao.disminuirStock(idProducto, cantidadComprada);

    }

}
