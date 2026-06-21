package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Pedido;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {

    public List<Pedido> listarTodos();

    public Pedido obtenerPorId(int id);

    public Pedido crear(Pedido pedido);

    public Pedido actualizar(int id, Pedido detalles);

    public void eliminar(int id);

    public List<Pedido> buscarPorEstado(String estado);

    public List<Pedido> buscarPorCliente(int clienteId);

    public List<Pedido> buscarPorRangoFecha(LocalDateTime desde, LocalDateTime hasta);

    public Pedido actualizarEstadoPedido(int pedidoId, String nuevoEstado);
}

