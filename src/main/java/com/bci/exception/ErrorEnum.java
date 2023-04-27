package com.bci.exception;

public enum ErrorEnum {
    USERALREADYEXIST("El usuario ya existe"),
    USERNOTFOUND("Usuario no encontrado"),
    INVALID_REQUEST("Request invalido");

    private String mensaje;

    ErrorEnum(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
