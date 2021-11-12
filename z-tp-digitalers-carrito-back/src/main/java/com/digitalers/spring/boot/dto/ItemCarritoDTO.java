package com.digitalers.spring.boot.dto;

import java.io.Serializable;

public class ItemCarritoDTO implements Serializable {

    private static final long serialVersionUID = 5547156376304088628L;

    private Long idProducto;
    private Integer cantidad;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
