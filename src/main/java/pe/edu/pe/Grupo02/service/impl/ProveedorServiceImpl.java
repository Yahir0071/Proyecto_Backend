package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.Proveedor;
import pe.edu.pe.Grupo02.repository.ProveedorRepository;
import pe.edu.pe.Grupo02.service.ProveedorService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor obtenerPorId(int id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Proveedor crear(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    @Transactional
    public Proveedor actualizar(int id, Proveedor detalles) {
        Proveedor proveedor = obtenerPorId(id);
        proveedor.setNombre(detalles.getNombre());
        proveedor.setRuc(detalles.getRuc());
        proveedor.setContacto(detalles.getContacto());
        proveedor.setTelefono(detalles.getTelefono());
        proveedor.setDireccion(detalles.getDireccion());
        return proveedorRepository.save(proveedor);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        proveedorRepository.deleteById(id);
    }
}