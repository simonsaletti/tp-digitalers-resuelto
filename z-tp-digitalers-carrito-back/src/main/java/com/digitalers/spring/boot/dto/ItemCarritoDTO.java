package com.digitalers.spring.boot.dto;

import java.io.Serializable;

public class ItemCarritoDTO implements Serializable {

    private static final long serialVersionUID = 5547156376304088628L;

    private Long idProducto;
    private Long cantidad;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

}
