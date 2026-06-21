package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.Almacen;
import pe.edu.pe.Grupo02.model.Ubicacion;
import pe.edu.pe.Grupo02.repository.AlmacenRepository;
import pe.edu.pe.Grupo02.service.AlmacenService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlmacenServiceImpl implements AlmacenService {

    private final AlmacenRepository almacenRepository;

    public List<Almacen> listarTodos() {
        return almacenRepository.findAll();
    }

    public Almacen obtenerPorId(int id) {
        return almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado: " + id));
    }

    @Transactional
    public Almacen crear(Almacen almacen) {
        if (almacen.getUbicaciones() != null) {
            almacen.getUbicaciones().forEach(u -> u.setAlmacen(almacen));
        }
        return almacenRepository.save(almacen);
    }

    @Transactional
    public Almacen actualizar(int id, Almacen detalles) {
        Almacen almacen = obtenerPorId(id);
        almacen.setNombre(detalles.getNombre());
        almacen.setDireccion(detalles.getDireccion());
        almacen.setFilas(detalles.getFilas());
        almacen.setColumnas(detalles.getColumnas());
        return almacenRepository.save(almacen);
    }

    @Transactional
    public void eliminar(int id) {
        almacenRepository.deleteById(id);
    }

    public List<Ubicacion> obtenerTodasUbicaciones() {
        return almacenRepository.findAll().stream()
                .flatMap(a -> a.getUbicaciones().stream())
                .toList();
    }

    public Ubicacion sugerirUbicacionDisponible() {
        return almacenRepository.findAll().stream()
                .flatMap(a -> a.getUbicaciones().stream())
                .findFirst()
                .orElse(null);
    }
}
