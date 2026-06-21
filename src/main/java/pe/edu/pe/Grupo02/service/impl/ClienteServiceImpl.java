package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.Cliente;
import pe.edu.pe.Grupo02.repository.ClienteRepository;
import pe.edu.pe.Grupo02.service.ClienteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizar(int id, Cliente detalles) {
        Cliente cliente = obtenerPorId(id);
        cliente.setNombre(detalles.getNombre());
        cliente.setDni(detalles.getDni());
        cliente.setTelefono(detalles.getTelefono());
        cliente.setDireccion(detalles.getDireccion());
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminar(int id) {
        clienteRepository.deleteById(id);
    }
}
