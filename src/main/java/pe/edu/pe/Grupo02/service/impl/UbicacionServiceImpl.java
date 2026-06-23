package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.Almacen;
import pe.edu.pe.Grupo02.model.Ubicacion;
import pe.edu.pe.Grupo02.repository.AlmacenRepository;
import pe.edu.pe.Grupo02.repository.ProductoRepository;
import pe.edu.pe.Grupo02.repository.UbicacionRepository;
import pe.edu.pe.Grupo02.service.UbicacionService;
import pe.edu.pe.Grupo02.structure.GrafoUbicaciones;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    private final ProductoRepository productoRepository;
    private final GrafoUbicaciones grafo = new GrafoUbicaciones();
    private final AlmacenRepository almacenRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Ubicacion> listarTodas() {
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        // Inicializar grafo con ubicaciones
        for (Ubicacion u : ubicaciones) {
            if (!grafo.contiene(u.getId())) {
                grafo.agregarNodo(u.getId(), u.getNombre());
            }
        }
        return ubicaciones;
    }

    @Override
    public Ubicacion obtenerPorId(int id) {
        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Ubicacion crear(Ubicacion ubicacion) {
        // 2. Reemplazamos el "Fantasma" por el Almacén real de la base de datos:
        if (ubicacion.getAlmacen() != null && ubicacion.getAlmacen().getId() > 0) {
            Almacen almacenReal = almacenRepository.findById(ubicacion.getAlmacen().getId())
                    .orElseThrow(() -> new RuntimeException("El Almacén especificado no existe"));

            ubicacion.setAlmacen(almacenReal);
        }

        return ubicacionRepository.save(ubicacion);
    }

    @Override
    @Transactional
    public Ubicacion actualizar(int id, Ubicacion detalles) {
        Ubicacion ubicacion = obtenerPorId(id);
        ubicacion.setNombre(detalles.getNombre());
        ubicacion.setDescripcion(detalles.getDescripcion());
        return ubicacionRepository.save(ubicacion);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        Ubicacion ubicacion = obtenerPorId(id);

        // ✅ Verificar si la ubicación tiene productos asignados
        int productosCount = productoRepository.countByUbicacionId(id);

        if (productosCount > 0) {
            throw new RuntimeException("No se puede eliminar la ubicación '" + ubicacion.getNombre()
                    + "' porque tiene " + productosCount + " producto(s) asignado(s). "
                    + "Desasigne los productos primero.");
        }

        // Si no hay productos, eliminar la ubicación
        // 1. Buscamos la ubicación real en la base de datos
        Ubicacion ubicacion1 = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada: " + id));

        // 2. Cortamos el lazo sagrado con el padre (Lo sacamos de su lista)
        Almacen almacen = ubicacion1.getAlmacen();
        if (almacen != null) {
            almacen.getUbicaciones().remove(ubicacion1);
        }

        // 3. Ahora sí, lo borramos para siempre
        ubicacionRepository.delete(ubicacion1);
    }

    // ===== MÉTODOS PARA GRAFO =====
    @Override
    public List<Integer> obtenerRutaMasCorta(int origen, int destino) {
        return grafo.rutaMasCorta(origen, destino);
    }

    @Override
    public List<Integer> obtenerRutaBFS(int origen, int destino) {
        return grafo.rutaBFS(origen, destino);
    }

    @Override
    public void conectarUbicaciones(int origen, int destino, double distancia) {
        grafo.agregarArista(origen, destino, distancia);
    }

    @Override
    public List<Ubicacion> listarPorAlmacen(int almacenId) {
        return ubicacionRepository.findByAlmacenId(almacenId);
    }

    @Override
    public int countProductosByUbicacion(int ubicacionId) {
        return productoRepository.countByUbicacionId(ubicacionId);
    }
}