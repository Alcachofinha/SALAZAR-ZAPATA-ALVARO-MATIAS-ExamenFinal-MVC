package org.unisiga.exception;

public class InscripcionDuplicadaException
        extends ReglaNegocioException {

    private static final long serialVersionUID = 1L;

    public InscripcionDuplicadaException(String mensaje) {
        super(mensaje);
    }
}