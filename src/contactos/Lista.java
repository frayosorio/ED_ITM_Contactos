package contactos;

import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Lista {

    private Nodo cabeza;

    public Lista() {
        cabeza = null;
    }

    //Agrega un nodo al final de la lista
    public void agregar(Nodo n) {
        if (cabeza == null) {
            cabeza = n;
        } else {
            Nodo apuntador = cabeza;
            while (apuntador.siguiente != null) {

                apuntador = apuntador.siguiente;
            }
            apuntador.siguiente = n;
        }
        n.siguiente = null;
    }

    //Llena la lista desde un archivo plano
    public void desdeArchivo(String nombreArchivo) {
        BufferedReader br = Archivo.abrirArchivo(nombreArchivo);

        cabeza = null;
        try {
            String linea = br.readLine();
            while (linea != null) {
                String[] datos = linea.split("\t");
                if (datos.length >= 5) {
                    Nodo n = new Nodo(datos[0],
                            datos[1],
                            datos[2],
                            datos[3],
                            datos[4]);
                    agregar(n);
                }

                linea = br.readLine();
            }

        } catch (IOException ex) {

        }

    }

    //Devuelve el numero de nodos en la lista
    public int obtenerLongitud() {
        int totalNodos = 0;
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            totalNodos++;
            apuntador = apuntador.siguiente;
        }
        return totalNodos;
    }

    //Muestra la lista en una tabla
    public void mostrar(JTable tbl) {
        String[] encabezados = new String[]{"Nombre", "Telefono", "Celular", "Direccion", "Correo"};
        int filas = obtenerLongitud();
        String[][] datos = new String[filas][encabezados.length];
        Nodo apuntador = cabeza;
        int fila = 0;
        while (apuntador != null) {
            datos[fila][0] = apuntador.nombre;
            datos[fila][1] = apuntador.telefono;
            datos[fila][2] = apuntador.celular;
            datos[fila][3] = apuntador.direccion;
            datos[fila][4] = apuntador.correo;
            fila++;
            apuntador = apuntador.siguiente;
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);

    }

}
