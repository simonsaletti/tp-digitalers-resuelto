package com.digitalers.spring.boot.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesResponseDTO implements Serializable {

    private static final long serialVersionUID = 9007083866621638106L;

    String nombre;
    String descripcion;
}
