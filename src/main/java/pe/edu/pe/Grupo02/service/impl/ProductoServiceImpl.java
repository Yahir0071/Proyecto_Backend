package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.Producto;
import pe.edu.pe.Grupo02.model.Ubicacion;
import pe.edu.pe.Grupo02.repository.ProductoRepository;
import pe.edu.pe.Grupo02.repository.UbicacionRepository;
import pe.edu.pe.Grupo02.service.ProductoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final UbicacionRepository ubicacionRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Producto crear(Producto producto) {
        if (producto.getUbicacion() != null && producto.getUbicacion().getId() != 0) {
            Ubicacion ubicacion = ubicacionRepository.findById(producto.getUbicacion().getId())
                    .orElseThrow(() -> new RuntimeException("Ubicación no válida"));
            producto.setUbicacion(ubicacion);
        }
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(int id, Producto detalles) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(detalles.getNombre());
        producto.setCategoria(detalles.getCategoria());
        producto.setPrecio(detalles.getPrecio());
        producto.setStockActual(detalles.getStockActual());
        producto.setStockMinimo(detalles.getStockMinimo());
        producto.setUnidadMedida(detalles.getUnidadMedida());
        producto.setActivo(detalles.isActivo());

        if (detalles.getUbicacion() != null && detalles.getUbicacion().getId() != 0) {
            Ubicacion ubicacion = ubicacionRepository.findById(detalles.getUbicacion().getId())
                    .orElseThrow(() -> new RuntimeException("Ubicación no válida"));
            producto.setUbicacion(ubicacion);
        }

        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(int id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }

    @Transactional
    public Producto registrarEntradaMercancia(int productoId, int cantidad) {
        Producto producto = obtenerPorId(productoId);
        producto.setStockActual(producto.getStockActual() + cantidad);
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerAlertasDeStockBajo() {
        return productoRepository.findAll().stream()
                .filter(p -> p.getStockActual() <= p.getStockMinimo())
                .toList();
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
}

