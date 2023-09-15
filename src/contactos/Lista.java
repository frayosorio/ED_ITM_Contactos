package contactos;

import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

        //agregar el evento de modificacion de datos
        dtm.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                //obtener el modelo de datos de la tabla editada
                DefaultTableModel dtm = (DefaultTableModel) e.getSource();
                //obtener la posicion de la fila editada
                int posicion = e.getFirstRow();
                //obetner los valores de la fila editada
                String nombre = (String) dtm.getValueAt(posicion, 0);
                String telefono = (String) dtm.getValueAt(posicion, 1);
                String celular = (String) dtm.getValueAt(posicion, 2);
                String direccion = (String) dtm.getValueAt(posicion, 3);
                String correo = (String) dtm.getValueAt(posicion, 4);

                actualizar(posicion, nombre, telefono, celular, direccion, correo);
            }
        });

        tbl.setModel(dtm);

    }

    public Nodo obtenerNodo(int posicion) {
        int p = 0;
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            if (p == posicion) {
                return apuntador;
            }
            apuntador = apuntador.siguiente;
            p++;
        }
        return null;
    }

    public void actualizar(int posicion,
            String nombre,
            String telefono,
            String celular,
            String direccion,
            String correo) {
        Nodo n = obtenerNodo(posicion);
        if (n != null) {
            n.actualizar(nombre, telefono, celular, direccion, correo);
        }
    }

    public boolean guardar(String nombreArchivo) {
        int totalLineas = obtenerLongitud();
        if (totalLineas > 0) {
            String[] lineas = new String[totalLineas];
            Nodo apuntador = cabeza;
            int i = 0;
            while (apuntador != null) {
                lineas[i] = (apuntador.nombre.isEmpty() ? " " : apuntador.nombre) + "\t"
                        + (apuntador.telefono.isEmpty() ? " " : apuntador.telefono) + "\t"
                        + (apuntador.celular.isEmpty() ? " " : apuntador.celular) + "\t"
                        + (apuntador.direccion.isEmpty() ? " " : apuntador.direccion) + "\t"
                        + (apuntador.correo.isEmpty() ? " " : apuntador.correo);
                apuntador = apuntador.siguiente;
                i++;
            }
            return Archivo.guardarArchivo(nombreArchivo, lineas);
        }
        return false;
    }

    public void eliminar(Nodo n) {
        if (n != null) {
            if (n == cabeza) {
                cabeza = cabeza.siguiente;
            } else {
                Nodo apuntador = cabeza;
                while (apuntador.siguiente != n) {
                    apuntador = apuntador.siguiente;
                }
                apuntador.siguiente = n.siguiente;
            }
        }
    }

    public void intercambiar(Nodo n1, Nodo n2, Nodo a1, Nodo a2) {
        if (cabeza != null && n1 != n2 && n1 != null && n2 != null) {
            if (a1 != null) {
                a1.siguiente = n2;
            } else {
                cabeza = n2;
            }
            Nodo t = n2.siguiente;
            if (n1 != a2) {
                n2.siguiente = n1.siguiente;
                a2.siguiente = n1;
            } else {
                n2.siguiente = n1;
            }
            n1.siguiente = t;
        }
    }

    public void ordenarBurbuja() {
        Nodo ni = cabeza;
        Nodo ai = null;
        while (ni.siguiente != null) {
            Nodo nj = ni.siguiente;
            Nodo aj = ni;
            while (nj != null) {
                if (ni.nombre.compareTo(nj.nombre) > 0) {
                    intercambiar(ni, nj, ai, aj);
                    Nodo t = ni;
                    ni = nj;
                    nj = t;
                }
                aj = nj;
                nj = nj.siguiente;
            }
            ai = ni;
            ni = ni.siguiente;
        }
    }

}
