package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.Almacen;
import pe.edu.pe.Grupo02.model.Ubicacion;
import pe.edu.pe.Grupo02.repository.AlmacenRepository;
import pe.edu.pe.Grupo02.repository.UbicacionRepository;
import pe.edu.pe.Grupo02.service.UbicacionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final AlmacenRepository almacenRepository;

    public List<Ubicacion> listarTodas() {
        return ubicacionRepository.findAll();
    }

    public Ubicacion obtenerPorId(int id) {
        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada: " + id));
    }

    @Transactional
    public Ubicacion crear(Ubicacion ubicacion) {
        if (ubicacion.getAlmacen() != null && ubicacion.getAlmacen().getId() != 0) {
            Almacen almacen = almacenRepository.findById(ubicacion.getAlmacen().getId())
                    .orElseThrow(() -> new RuntimeException("Almacén no válido"));
            ubicacion.setAlmacen(almacen);
        }
        return ubicacionRepository.save(ubicacion);
    }

    @Transactional
    public Ubicacion actualizar(int id, Ubicacion detalles) {
        Ubicacion ubicacion = obtenerPorId(id);
        ubicacion.setNombre(detalles.getNombre());
        ubicacion.setDescripcion(detalles.getDescripcion());
        if (detalles.getAlmacen() != null && detalles.getAlmacen().getId() != 0) {
            Almacen almacen = almacenRepository.findById(detalles.getAlmacen().getId())
                    .orElseThrow(() -> new RuntimeException("Almacén no válido"));
            ubicacion.setAlmacen(almacen);
        }
        return ubicacionRepository.save(ubicacion);
    }

    @Transactional
    public void eliminar(int id) {
        ubicacionRepository.deleteById(id);
    }
}
