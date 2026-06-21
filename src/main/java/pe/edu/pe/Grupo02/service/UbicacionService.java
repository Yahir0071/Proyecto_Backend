package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Ubicacion;
import java.util.List;

public interface UbicacionService {

    public List<Ubicacion> listarTodas();

    public Ubicacion obtenerPorId(int id);

    public Ubicacion crear(Ubicacion ubicacion);

    public Ubicacion actualizar(int id, Ubicacion detalles);

    public void eliminar(int id);
}
