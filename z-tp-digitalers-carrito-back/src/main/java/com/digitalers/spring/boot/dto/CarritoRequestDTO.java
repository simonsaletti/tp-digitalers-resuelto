package com.digitalers.spring.boot.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CarritoRequestDTO implements Serializable {

    private static final long serialVersionUID = 3852522385847471438L;

    private Long idCliente;
    private List<ItemCarritoDTO> items;

}
