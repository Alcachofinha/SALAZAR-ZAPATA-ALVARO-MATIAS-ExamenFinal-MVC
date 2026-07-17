package org.unisiga.exception;

public class ReglaNegocioException
        extends IllegalStateException {

    private static final long serialVersionUID = 1L;

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}