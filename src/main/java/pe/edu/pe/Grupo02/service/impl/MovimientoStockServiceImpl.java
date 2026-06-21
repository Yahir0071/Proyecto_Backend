package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.MovimientoStock;
import pe.edu.pe.Grupo02.model.Producto;
import pe.edu.pe.Grupo02.repository.MovimientoStockRepository;
import pe.edu.pe.Grupo02.repository.ProductoRepository;
import pe.edu.pe.Grupo02.service.MovimientoStockService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoStockServiceImpl implements MovimientoStockService {

    private final MovimientoStockRepository movimientoStockRepository;
    private final ProductoRepository productoRepository;

    public List<MovimientoStock> listarTodos() {
        return movimientoStockRepository.findAll();
    }

    public MovimientoStock obtenerPorId(int id) {
        return movimientoStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento de stock no encontrado: " + id));
    }

    public List<MovimientoStock> listarPorProducto(int productoId) {
        return movimientoStockRepository.findByProductoId(productoId);
    }

    public List<MovimientoStock> listarPorTipo(String tipo) {
        return movimientoStockRepository.findByTipo(tipo);
    }

    @Transactional
    public MovimientoStock crear(MovimientoStock movimiento) {
        Producto producto = productoRepository.findById(movimiento.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no válido: " + movimiento.getProducto().getId()));

        movimiento.setProducto(producto);
        movimiento.setFecha(movimiento.getFecha() == null ? LocalDateTime.now() : movimiento.getFecha());

        if ("ENTRADA".equalsIgnoreCase(movimiento.getTipo())) {
            producto.setStockActual(producto.getStockActual() + movimiento.getCantidad());
        } else if ("SALIDA".equalsIgnoreCase(movimiento.getTipo())) {
            if (producto.getStockActual() < movimiento.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para registrar salida");
            }
            producto.setStockActual(producto.getStockActual() - movimiento.getCantidad());
        } else {
            throw new RuntimeException("Tipo de movimiento no válido. Use ENTRADA o SALIDA.");
        }

        productoRepository.save(producto);
        return movimientoStockRepository.save(movimiento);
    }

    @Transactional
    public MovimientoStock registrarMovimiento(Producto producto, String tipo, int cantidad) {
        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setProducto(producto);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setFecha(LocalDateTime.now());
        return crear(movimiento);
    }
}
