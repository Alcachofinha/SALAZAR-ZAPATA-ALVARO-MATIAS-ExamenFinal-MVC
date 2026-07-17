package org.unisiga.exception;

public class CupoCompletoException
        extends ReglaNegocioException {

    private static final long serialVersionUID = 1L;

    public CupoCompletoException(String mensaje) {
        super(mensaje);
    }
}