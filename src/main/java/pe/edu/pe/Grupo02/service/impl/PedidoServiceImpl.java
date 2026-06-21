package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.DetallePedido;
import pe.edu.pe.Grupo02.model.Pedido;
import pe.edu.pe.Grupo02.model.Producto;
import pe.edu.pe.Grupo02.repository.ClienteRepository;
import pe.edu.pe.Grupo02.repository.PedidoRepository;
import pe.edu.pe.Grupo02.repository.ProductoRepository;
import pe.edu.pe.Grupo02.service.MovimientoStockService;
import pe.edu.pe.Grupo02.service.PedidoService;
import pe.edu.pe.Grupo02.structure.ListaPedidos;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoStockService movimientoStockService;

    private final ListaPedidos listaPedidos = new ListaPedidos();

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido obtenerPorId(int id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Transactional
    @Override
    public Pedido crear(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == 0) {
            throw new RuntimeException("El pedido debe tener un cliente válido.");
        }
        pedido.setCliente(clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no válido")));

        if (pedido.getFecha() == null) {
            pedido.setFecha(LocalDateTime.now());
        }
        pedido.setTotal(0);
        pedido.setCantidadProductos(0);

        if (pedido.getDetalles() != null) {
            double total = 0;
            int totalProductos = 0;
            for (DetallePedido detalle : pedido.getDetalles()) {
                Producto producto = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no válido: " + detalle.getProducto().getId()));
                if (producto.getStockActual() < detalle.getCantidad()) {
                    throw new RuntimeException("Cantidad insuficiente en stock para producto: " + producto.getNombre());
                }
                detalle.setProducto(producto);
                detalle.setPedido(pedido);
                detalle.setPrecioUnitario(producto.getPrecio());
                detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
                total += detalle.getSubtotal();
                totalProductos += detalle.getCantidad();

                producto.setStockActual(producto.getStockActual() - detalle.getCantidad());
                productoRepository.save(producto);
                movimientoStockService.registrarMovimiento(producto, "SALIDA", detalle.getCantidad());
            }
            pedido.setTotal(total);
            pedido.setCantidadProductos(totalProductos);
        }
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        listaPedidos.insertar(pedidoGuardado);

        return pedidoGuardado;
    }

    @Transactional
    @Override
    public Pedido actualizar(int id, Pedido detalles) {
        Pedido pedido = obtenerPorId(id);
        pedido.setEstado(detalles.getEstado());
        pedido.setPrioridad(detalles.getPrioridad());
        pedido.setFecha(detalles.getFecha() != null ? detalles.getFecha() : pedido.getFecha());

        if (detalles.getDetalles() != null) {
            pedido.getDetalles().clear();
            for (DetallePedido detalle : detalles.getDetalles()) {
                Producto producto = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no válido: " + detalle.getProducto().getId()));
                detalle.setProducto(producto);
                detalle.setPedido(pedido);
                detalle.setPrecioUnitario(producto.getPrecio());
                detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
                pedido.getDetalles().add(detalle);
            }
            pedido.setCantidadProductos(pedido.getDetalles().stream().mapToInt(DetallePedido::getCantidad).sum());
            pedido.setTotal(pedido.getDetalles().stream().mapToDouble(DetallePedido::getSubtotal).sum());
        }
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void eliminar(int id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> buscarPorEstado(String estado) {
        return pedidoRepository.findByEstadoOrderByPrioridadDesc(estado);
    }

    public List<Pedido> buscarPorCliente(int clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> buscarPorRangoFecha(LocalDateTime desde, LocalDateTime hasta) {
        return pedidoRepository.findByFechaBetween(desde, hasta);
    }

    @Transactional
    public Pedido actualizarEstadoPedido(int pedidoId, String nuevoEstado) {
        Pedido pedido = obtenerPorId(pedidoId);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}
