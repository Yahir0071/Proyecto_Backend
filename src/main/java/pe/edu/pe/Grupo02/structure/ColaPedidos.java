package pe.edu.pe.Grupo02.structure;

import pe.edu.pe.Grupo02.model.Pedido;

import java.util.LinkedList;
import java.util.Queue;

public class ColaPedidos {
    private Queue<Pedido> cola;

    public ColaPedidos() {
        this.cola = new LinkedList<>();
    }

    public void encolar(Pedido pedido) {
        cola.offer(pedido);
    }

    public Pedido desencolar() {
        return cola.poll();
    }

    public Pedido obtenerPrimero() {
        return cola.peek();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int tamanio() {
        return cola.size();
    }

    public void limpiar() {
        cola.clear();
    }
}
