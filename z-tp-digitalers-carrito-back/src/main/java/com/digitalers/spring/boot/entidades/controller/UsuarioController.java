package com.digitalers.spring.boot.entidades.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    // private final Logger log = LoggerFactory.getLogger(this.getClass());
    //
    // private UsuarioService usuarioService;
    //
    // @Autowired
    // public UsuarioController(UsuarioService usuarioService) {
    // this.usuarioService = usuarioService;
    // }

    // @GetMapping
    // public ResponseEntity<?> obtenerTodos() {
    // Map<String, Object> respuesta = new HashMap<>();
    // try {
    // log.info("Buscando todos los clientes...");
    // List<Usuario> elementos = this.usuarioService.listarTodos();
    // log.info("Clientes obtenidos: " + elementos.size());
    // respuesta.put("elementos", elementos);
    // respuesta.put("mensaje", "Los clientes han sido encontrados correctamente");
    // respuesta.put("status", HttpStatus.OK.value());
    // return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    // } catch (Exception e) {
    // respuesta.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
    // respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    // return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
    //
    // @GetMapping("/{id}")
    // public ResponseEntity<?> obtener(@PathVariable Long id) {
    // Map<String, String> response = new HashMap<>();
    // try {
    // Usuario cliente = this.clienteService.obtener(id);
    // return new ResponseEntity<Usuario>(cliente, HttpStatus.OK);
    // } catch (NoSuchElementException e) {
    // response.put("mensaje", "Error al buscar. Cliente con ID " + id + " no encontrado.");
    // return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
    // } catch (Exception e) {
    // response.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
    // return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

}
