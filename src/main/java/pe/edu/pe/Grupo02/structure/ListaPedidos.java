package pe.edu.pe.Grupo02.structure;

import pe.edu.pe.Grupo02.model.Pedido;

public class ListaPedidos implements OperacionesLista {

    private NodoPedido cabeza;
    private int tamaño;

    @Override
    public void insertar(Pedido pedido) {
        NodoPedido nuevo = new NodoPedido();
        nuevo.dato = pedido;

        if (cabeza == null) {
            cabeza = nuevo;
        } else {

            NodoPedido aux = cabeza;

            // recorrer hasta el final
            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }

            aux.siguiente = nuevo;
            nuevo.anterior = aux;
        }

        tamaño++;

    }

    @Override
     public Pedido buscar(int id) {
        NodoPedido aux = cabeza;

        while (aux != null) {

            if (aux.dato.getId() == id) {
                return aux.dato;
            }

            aux = aux.siguiente;
        }

        return null;

    }

    @Override
    public void eliminar(int id) {
        NodoPedido aux = cabeza;

        while (aux != null) {

            if (aux.dato.getId() == id) {

                // si es cabeza
                if (aux == cabeza) {
                    cabeza = cabeza.siguiente;

                    if (cabeza != null) {
                        cabeza.anterior = null;
                    }

                } else {

                    aux.anterior.siguiente = aux.siguiente;

                    if (aux.siguiente != null) {
                        aux.siguiente.anterior = aux.anterior;
                    }
                }

                tamaño--;
                return;
            }

            aux = aux.siguiente;
        }
    }

}
