package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Pedido;
import pe.edu.pe.Grupo02.structure.PilaHistorialEstados;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();
    Pedido obtenerPorId(int id);
    Pedido crear(Pedido pedido);
    Pedido actualizar(int id, Pedido detalles);
    void eliminar(int id);

    // MÉTODOS PARA COLA
    void encolarPedido(Pedido pedido);
    Pedido procesarSiguientePedido();
    Pedido obtenerPrimeroEnCola();
    int obtenerTamanioCola();

    // MÉTODOS PARA PILA
    void agregarEstadoPedido(int idPedido, String estado);
    PilaHistorialEstados.EstadoPedido deshacerEstadoPedido(int idPedido);
    String obtenerUltimoEstadoPedido(int idPedido);
}

