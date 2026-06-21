package pe.edu.pe.Grupo02.structure;

import java.time.LocalDateTime;
import java.util.Stack;

public class PilaHistorialEstados {

    private Stack<EstadoPedido> pila;

    public PilaHistorialEstados() {
        this.pila = new Stack<>();
    }

    public void apilar(String estado, int idPedido) {
        EstadoPedido estadoPedido = new EstadoPedido(estado, LocalDateTime.now(), idPedido);
        pila.push(estadoPedido);
    }

    public EstadoPedido deshacer() {
        if (!pila.isEmpty()) {
            return pila.pop();
        }
        return null;
    }

    public EstadoPedido obtenerUltimo() {
        return pila.peek();
    }

    public boolean estaVacia() {
        return pila.isEmpty();
    }

    public int tamanio() {
        return pila.size();
    }

    public static class EstadoPedido {
        private String nombre;
        private LocalDateTime fecha;
        private int idPedido;

        public EstadoPedido(String nombre, LocalDateTime fecha, int idPedido) {
            this.nombre = nombre;
            this.fecha = fecha;
            this.idPedido = idPedido;
        }

        public String getNombre() {
            return nombre;
        }

        public LocalDateTime getFecha() {
            return fecha;
        }

        public int getIdPedido() {
            return idPedido;
        }
    }
}
