package org.unisiga.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.unisiga.exception.PersistenciaException;

public class ArchivoSistema {

    public void guardar(
            String ruta,
            DatosSistema datos
    ) {
        validarRuta(ruta);

        if (datos == null) {
            throw new IllegalArgumentException(
                    "Los datos no pueden ser nulos."
            );
        }

        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null
                && !carpeta.exists()
                && !carpeta.mkdirs()) {

            throw new PersistenciaException(
                    "No se pudo crear la carpeta de datos."
            );
        }

        try (
                ObjectOutputStream salida =
                        new ObjectOutputStream(
                                new FileOutputStream(archivo)
                        )
        ) {
            salida.writeObject(datos);

        } catch (IOException ex) {
            throw new PersistenciaException(
                    "No se pudieron guardar los datos.",
                    ex
            );
        }
    }

    public DatosSistema cargar(String ruta) {
        validarRuta(ruta);

        File archivo = new File(ruta);

        if (!archivo.exists()) {
            throw new PersistenciaException(
                    "El archivo de datos no existe."
            );
        }

        try (
                ObjectInputStream entrada =
                        new ObjectInputStream(
                                new FileInputStream(archivo)
                        )
        ) {
            Object objeto = entrada.readObject();

            if (!(objeto instanceof DatosSistema)) {
                throw new PersistenciaException(
                        "El archivo contiene datos inválidos."
                );
            }

            return (DatosSistema) objeto;

        } catch (IOException
                | ClassNotFoundException ex) {

            throw new PersistenciaException(
                    "No se pudieron cargar los datos.",
                    ex
            );
        }
    }

    public boolean existe(String ruta) {
        validarRuta(ruta);

        return new File(ruta).exists();
    }

    private void validarRuta(String ruta) {
        if (ruta == null
                || ruta.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "La ruta no puede estar vacía."
            );
        }
    }
}