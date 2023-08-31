package com.digitalers.spring.boot.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.digitalers.spring.boot.dto.TokenRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/token")
public class TokenController {

  @PostMapping
  public ResponseEntity<?> obtener(@RequestBody TokenRequestDTO tokenRequestDTO)
      throws JsonMappingException, JsonProcessingException {
    Map<String, Object> respuesta = new HashMap<>();
    try {
      if (tokenRequestDTO.usernameIsBlank()) {
        respuesta.put("mensaje", "Debe introducir un usuario válido.");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
      }

      if (tokenRequestDTO.usernameLengthIsWrong()) {
        respuesta.put("mensaje", "La cantidad de caracteres no está permitida para el username.");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
      }

      if (tokenRequestDTO.passwordIsBlank()) {
        respuesta.put("mensaje", "Debe introducir una contraseña válida.");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
      }

      if (tokenRequestDTO.passwordLengthIsWrong()) {
        respuesta.put("mensaje", "La cantidad de caracteres no está permitida para el password.");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
      }

      RestTemplate restTemplate = new RestTemplate();
      String credentials = "carrito:12345";
      String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      headers.add("Authorization", "Basic " + encodedCredentials);

      MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
      map.add("username", tokenRequestDTO.getUsername());
      map.add("password", tokenRequestDTO.getPassword());
      map.add("grant_type", "password");

      HttpEntity<MultiValueMap<String, String>> request =
          new HttpEntity<MultiValueMap<String, String>>(map, headers);

      String access_token_url = "http://localhost:8080/oauth/token";

      ResponseEntity<Map> response =
          restTemplate.postForEntity(access_token_url, request, Map.class);

      respuesta.put("access_token", response.getBody().get("access_token"));

      return new ResponseEntity<Map<String, Object>>(respuesta, response.getStatusCode());

      // respuesta.put("mensaje", "El producto con ID " + id + "ha sido encontrado correctamente");
      // respuesta.put("status", HttpStatus.OK.value());
      // return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    } catch (NoSuchElementException e) {
      // respuesta.put("mensaje", "Error al buscar. Producto con ID " + id + " no encontrado.");
      // respuesta.put("status", HttpStatus.NOT_FOUND.value());
      // return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
    } catch (HttpClientErrorException e) {
      ObjectMapper mapper = new ObjectMapper();
      String errorMsj = e.getMessage().substring(e.getMessage().indexOf("\"")).replace("\"{", "{")
          .replace("}\"", "}");

      @SuppressWarnings("unchecked")
      Map<String, String> map = mapper.readValue(errorMsj, Map.class);

      switch (map.get("error_description")) {
        case "User is disabled":
          respuesta.put("mensaje", "El usuario no está habilitado.");
          break;

        case "Bad credentials":
          respuesta.put("mensaje", "Las credenciales insertadas son incorrectas.");
          break;

        default:
          respuesta.put("mensaje", "No seeee.");
          break;
      }
      // respuesta.put("mensaje", );
      return new ResponseEntity<Map<String, Object>>(respuesta, e.getStatusCode());
    } catch (Exception e) {
      respuesta.put("aaaa", e);
      return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
      // respuesta.put("mensaje", "Ha ocurrido un error interno en la aplicación.");
      // respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
      // return new ResponseEntity<Map<String, Object>>(respuesta,
      // HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
  }

}
