package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Producto;
import java.util.List;

public interface ProductoService {

    public List<Producto> listarTodos();

    public Producto obtenerPorId(int id);

    public Producto crear(Producto producto);

    public Producto actualizar(int id, Producto detalles);

    public void eliminar(int id);

    public Producto registrarEntradaMercancia(int productoId, int cantidad);

    public List<Producto> obtenerAlertasDeStockBajo();

    public List<Producto> buscarPorCategoria(String categoria);
}

