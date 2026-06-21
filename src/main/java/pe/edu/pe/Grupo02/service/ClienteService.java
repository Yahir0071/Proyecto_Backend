package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Cliente;
import java.util.List;

public interface ClienteService {

    public List<Cliente> listarTodos();

    public Cliente obtenerPorId(int id);

    public Cliente crear(Cliente cliente);

    public Cliente actualizar(int id, Cliente detalles);

    public void eliminar(int id);

}
