package com.digitalers.spring.boot.entidades.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.digitalers.spring.boot.dto.CarritoRequestDTO;
import com.digitalers.spring.boot.dto.CarritoResponseDTO;
import com.digitalers.spring.boot.entidades.Carrito;
import com.digitalers.spring.boot.entidades.service.CarritoService;

// @CrossOrigin(origins = {"http://127.0.0.1:5501", "http://localhost:5501"})
@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            log.info("Buscando todos los carritos...");
            List<CarritoResponseDTO> elementos = this.carritoService.listarTodos();
            log.info("Carritos obtenidos: " + elementos.size());
            respuesta.put("elementos", elementos);
            respuesta.put("mensaje", "Los carritos han sido encontrados correctamente");
            respuesta.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            respuesta.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
            respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            CarritoResponseDTO elemento = this.carritoService.obtener(id);
            return new ResponseEntity<CarritoResponseDTO>(elemento, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            response.put("mensaje", "Error al buscar. Carrito con ID " + id + " no encontrado.");
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CarritoRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Carrito elementoGuardado = this.carritoService.insertar(dto);
            // Por si quisiera enviar un mensaje de éxito.
            response.put("mensaje", "El carrito ha sido guardado correctamente.");
            response.put("elemento", elementoGuardado);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
