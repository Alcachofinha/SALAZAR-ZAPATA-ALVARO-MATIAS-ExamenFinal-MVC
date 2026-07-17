package org.unisiga.exception;

public class PrerrequisitoNoCumplidoException
        extends ReglaNegocioException {

    private static final long serialVersionUID = 1L;

    public PrerrequisitoNoCumplidoException(
            String mensaje
    ) {
        super(mensaje);
    }
}