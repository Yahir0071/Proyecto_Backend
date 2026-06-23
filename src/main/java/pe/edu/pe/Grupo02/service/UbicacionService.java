package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Ubicacion;

import java.util.List;

public interface UbicacionService {

    public List<Ubicacion> listarTodas();

    public Ubicacion obtenerPorId(int id);

    public Ubicacion crear(Ubicacion ubicacion);

    public Ubicacion actualizar(int id, Ubicacion detalles);

    public void eliminar(int id);

    public List<Ubicacion> listarPorAlmacen(int almacenId);

    int countProductosByUbicacion(int ubicacionId);

    // MÉTODOS PARA GRAFO
    List<Integer> obtenerRutaMasCorta(int origen, int destino);

    List<Integer> obtenerRutaBFS(int origen, int destino);

    void conectarUbicaciones(int origen, int destino, double distancia);


}
