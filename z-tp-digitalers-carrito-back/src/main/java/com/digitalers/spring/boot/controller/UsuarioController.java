package com.digitalers.spring.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.digitalers.spring.boot.dto.UsuarioResponseDTO;
import com.digitalers.spring.boot.entidades.service.UsuarioService;
import com.digitalers.spring.boot.utils.Mensajes;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            log.info("Buscando todos los usuarios...");
            List<UsuarioResponseDTO> elementos = this.usuarioService.listarTodos();
            log.info("Usuarios obtenidos: " + elementos.size());
            respuesta.put("elementos", elementos);
            respuesta.put("mensaje", "Los usuarios han sido encontrados correctamente");
            respuesta.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            log.error(Mensajes.ERROR_INTERNO.getMensajeError() + ": " + e.getMessage());
            respuesta.put("mensaje", Mensajes.ERROR_INTERNO.getMensajeError());
            respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            log.info("Buscando el usuario con id " + id + "...");
            UsuarioResponseDTO elemento = this.usuarioService.obtener(id);
            log.info("Usuario obtenido: " + elemento.toString());
            respuesta.put("elemento", elemento);
            respuesta.put("mensaje", "El usuario ha sido encontrado correctamente");
            respuesta.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.error("Usuario con ID " + id + " no encontrado" + ": " + e.getMessage() + ".");
            respuesta.put("mensaje", "Usuario con ID " + id + " no encontrado.");
            respuesta.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(Mensajes.ERROR_INTERNO.getMensajeError() + ": " + e.getMessage() + ".");
            respuesta.put("mensaje", Mensajes.ERROR_INTERNO.getMensajeError());
            respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
