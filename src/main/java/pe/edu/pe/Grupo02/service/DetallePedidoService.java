package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.DetallePedido;
import java.util.List;

public interface DetallePedidoService {

    public List<DetallePedido> listarTodos();

    public DetallePedido obtenerPorId(int id);

    public List<DetallePedido> listarPorPedido(int pedidoId);

    public List<DetallePedido> listarPorProducto(int productoId);

    public DetallePedido crear(DetallePedido detalle);

    public DetallePedido actualizar(int id, DetallePedido detalles) ;

    public void eliminar(int id);
}
