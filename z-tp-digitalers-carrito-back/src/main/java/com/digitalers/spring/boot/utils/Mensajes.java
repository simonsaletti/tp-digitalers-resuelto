package com.digitalers.spring.boot.utils;

public enum Mensajes {

    ERROR_INTERNO("Ha ocurrido un error interno en la aplicación");

    private String mensajeError;

    private Mensajes(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getMensajeError() {
        return this.mensajeError;
    }

}
