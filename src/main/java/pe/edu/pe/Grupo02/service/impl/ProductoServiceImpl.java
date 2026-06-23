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
        // ✅ IMPORTANTE: Solo asignar ubicación si se proporciona un ID válido
        if (producto.getUbicacion() != null && producto.getUbicacion().getId() > 0) {
            Ubicacion ubicacion = ubicacionRepository.findById(producto.getUbicacion().getId())
                    .orElseThrow(() -> new RuntimeException("Ubicación no válida con ID: " + producto.getUbicacion().getId()));
            producto.setUbicacion(ubicacion);
        } else {
            // Si no se proporciona ubicación, dejarlo en null
            producto.setUbicacion(null);
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

        // ✅ IMPORTANTE: Solo actualizar ubicación si se proporciona un ID válido
        if (detalles.getUbicacion() != null && detalles.getUbicacion().getId() > 0) {
            Ubicacion ubicacion = ubicacionRepository.findById(detalles.getUbicacion().getId())
                    .orElseThrow(() -> new RuntimeException("Ubicación no válida con ID: " + detalles.getUbicacion().getId()));
            producto.setUbicacion(ubicacion);
        }
        // Si detalles.getUbicacion() es null, mantener la ubicación actual del producto

        return productoRepository.save(producto);
    }
    @Override
    @Transactional
    public void eliminar(int id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }
    @Override
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

    @Transactional
    public Producto asignarUbicacion(int productoId, int ubicacionId) {
        Producto producto = obtenerPorId(productoId);

        if (ubicacionId > 0) {
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionId)
                    .orElseThrow(() -> new RuntimeException("Ubicación no válida con ID: " + ubicacionId));
            producto.setUbicacion(ubicacion);
        } else {
            throw new RuntimeException("Ubicación ID debe ser mayor a 0");
        }

        return productoRepository.save(producto);
    }
}