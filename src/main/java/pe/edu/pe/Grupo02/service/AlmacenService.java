package pe.edu.pe.Grupo02.service;


import pe.edu.pe.Grupo02.model.Almacen;
import pe.edu.pe.Grupo02.model.Ubicacion;

import java.util.List;

public interface AlmacenService {

    public List<Almacen> listarTodos();

    public Almacen obtenerPorId(int id);

    public Almacen crear(Almacen almacen);

    public Almacen actualizar(int id, Almacen detalles);

    public void eliminar(int id);

    public List<Ubicacion> obtenerTodasUbicaciones();

    public Ubicacion sugerirUbicacionDisponible();
}

