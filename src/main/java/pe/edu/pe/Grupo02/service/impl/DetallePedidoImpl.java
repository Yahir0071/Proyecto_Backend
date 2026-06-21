package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.DetallePedido;
import pe.edu.pe.Grupo02.model.Pedido;
import pe.edu.pe.Grupo02.model.Producto;
import pe.edu.pe.Grupo02.repository.DetallePedidoRepository;
import pe.edu.pe.Grupo02.repository.PedidoRepository;
import pe.edu.pe.Grupo02.repository.ProductoRepository;
import pe.edu.pe.Grupo02.service.DetallePedidoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetallePedidoImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public List<DetallePedido> listarTodos() {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido obtenerPorId(int id) {
        return detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado: " + id));
    }

    public List<DetallePedido> listarPorPedido(int pedidoId) {
        return detallePedidoRepository.findByPedidoId(pedidoId);
    }

    public List<DetallePedido> listarPorProducto(int productoId) {
        return detallePedidoRepository.findByProductoId(productoId);
    }

    @Transactional
    public DetallePedido crear(DetallePedido detalle) {
        Pedido pedido = pedidoRepository.findById(detalle.getPedido().getId())
                .orElseThrow(() -> new RuntimeException("Pedido no válido: " + detalle.getPedido().getId()));
        Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no válido: " + detalle.getProducto().getId()));

        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());

        return detallePedidoRepository.save(detalle);
    }

    @Transactional
    public DetallePedido actualizar(int id, DetallePedido detalles) {
        DetallePedido detalle = obtenerPorId(id);
        Producto producto = productoRepository.findById(detalles.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no válido: " + detalles.getProducto().getId()));

        detalle.setProducto(producto);
        detalle.setCantidad(detalles.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio() * detalles.getCantidad());
        return detallePedidoRepository.save(detalle);
    }

    @Transactional
    public void eliminar(int id) {
        detallePedidoRepository.deleteById(id);
    }
}
