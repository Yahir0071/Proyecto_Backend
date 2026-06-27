package pe.edu.pe.Grupo02.structure;

import pe.edu.pe.Grupo02.model.Pedido;

import java.util.LinkedList;
import java.util.Queue;

public class ColaPedidos {
    private LinkedList<Pedido> cola;

    public ColaPedidos() {
        this.cola = new LinkedList<>();
    }

    // Inserta en posición correcta: prioridad 1 = más urgente
    // FIFO para misma prioridad (inserta antes de los de menor prioridad)
    public void encolar(Pedido pedido) {
        int i = 0;
        while (i < cola.size() && cola.get(i).getPrioridad() <= pedido.getPrioridad()) {
            i++;
        }
        cola.add(i, pedido);
    }

    public Pedido desencolar() {
        return cola.isEmpty() ? null : cola.poll();
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
