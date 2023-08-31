package com.digitalers.spring.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.digitalers.spring.boot.dto.ProductoRequestDTO;
import com.digitalers.spring.boot.dto.ProductoResponseDTO;
import com.digitalers.spring.boot.entidades.service.ProductoService;
import com.digitalers.spring.boot.utils.FileUploadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/productos")
public class ProductoController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private ProductoService productoService;

  @Autowired
  public ProductoController(ProductoService productoService) {
    this.productoService = productoService;
  }

  @GetMapping
  public ResponseEntity<?> obtenerTodos() {
    Map<String, Object> respuesta = new HashMap<>();
    try {
      log.info("Buscando todos los productos...");
      List<ProductoResponseDTO> elementos = this.productoService.listarTodos();
      log.info("Productos obtenidos: " + elementos.size());
      respuesta.put("elementos", elementos);
      respuesta.put("mensaje", "Los productos han sido encontrados correctamente");
      respuesta.put("status", HttpStatus.OK.value());
      return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    } catch (Exception e) {
      respuesta.put("mensaje", "Ha ocurrido un error interno en la aplicación: " + e.getMessage());
      respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
      return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> obtener(@PathVariable Long id) {
    Map<String, Object> respuesta = new HashMap<>();
    try {
      ProductoResponseDTO elemento = this.productoService.obtener(id);
      respuesta.put("elemento", elemento);
      respuesta.put("mensaje", "El producto con ID " + id + "ha sido encontrado correctamente");
      respuesta.put("status", HttpStatus.OK.value());
      return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    } catch (NoSuchElementException e) {
      respuesta.put("mensaje", "Error al buscar. Producto con ID " + id + " no encontrado.");
      respuesta.put("status", HttpStatus.NOT_FOUND.value());
      return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      respuesta.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
      respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
      return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Crea un producto")
  @PostMapping
  public ResponseEntity<?> crear(
      @Parameter(description = "Cuerpo del producto") @Valid @ModelAttribute ProductoRequestDTO dto,
      BindingResult result, @RequestParam(value = "file", required = false) MultipartFile archivo) {
    Map<String, Object> response = new HashMap<>();

    if (result.hasErrors()) {
      List<String> errorList =
          result.getFieldErrors().stream().map(fieldError -> "Campo " + fieldError.getField()
              + "inválido: " + fieldError.getDefaultMessage()).collect(Collectors.toList());
      response.put("errors", errorList);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    try {
      if (archivo != null && !archivo.isEmpty()) {
        dto.setNombreImagen(archivo.getOriginalFilename());
      }
      ProductoResponseDTO elementoGuardado = this.productoService.insertar(dto);
      FileUploadUtil.guardarArchivo(archivo);
      response.put("mensaje", "El producto ha sido guardado correctamente.");
      response.put("elemento", elementoGuardado);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      response.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> borrar(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();
    try {
      this.productoService.borrar(id);
      response.put("mensaje", "El producto ha sido eliminado correctamente.");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    } catch (DataAccessException e) {
      response.put("mensaje", "Error al eliminar. Producto con ID " + id + " no encontrado.");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      response.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
