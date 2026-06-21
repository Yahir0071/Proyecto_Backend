package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.MovimientoStock;
import pe.edu.pe.Grupo02.model.Producto;

import java.util.List;

public interface MovimientoStockService {

    public List<MovimientoStock> listarTodos();

    public MovimientoStock obtenerPorId(int id);

    public List<MovimientoStock> listarPorProducto(int productoId);

    public List<MovimientoStock> listarPorTipo(String tipo);

    public MovimientoStock crear(MovimientoStock movimiento);

    public MovimientoStock registrarMovimiento(Producto producto, String tipo, int cantidad);
}
