package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Proveedor;
import java.util.List;

public interface ProveedorService {
    List<Proveedor> listarTodos();
    Proveedor obtenerPorId(int id);
    Proveedor crear(Proveedor proveedor);
    Proveedor actualizar(int id, Proveedor detalles);
    void eliminar(int id);
}