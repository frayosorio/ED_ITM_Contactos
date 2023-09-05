package contactos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Archivo {

    // Método estático para abrir archivos planos
    public static BufferedReader abrirArchivo(String nombreArchivo) {
        File f = new File(nombreArchivo);
        // Existe el archivo?
        if (f.exists()) {
            /*
             * captura de error estructurada. Intenta realizar la instrucción de
             * apertura del archivo. Es susceptible de no poder realizarse
             */
            try {
                /*
                 * Apertura del archivo plano La clase BufferedReader permite
                 * manipular secuencia de caracteres. La clase File ofrece
                 * funcionalidad para operar con archivos
                 */
                FileReader fr = new FileReader(f);
                return new BufferedReader(fr);
            } catch (IOException e) {
                /*
                 * Sucedió un error que se captura mediante la clase IOException
                 * encargada de manipular errores de entrada y salida
                 */
                return null;
            }
        } else {
            return null;
        }
    }//abrirArchivo

}
