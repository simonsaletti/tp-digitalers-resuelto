package com.digitalers.spring.boot.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO implements Serializable {

    private static final long serialVersionUID = -942348092141822285L;

    private String username;
    private String nombre;
    private String apellido;
    private String email;
    private List<RolesResponseDTO> roles;

}
