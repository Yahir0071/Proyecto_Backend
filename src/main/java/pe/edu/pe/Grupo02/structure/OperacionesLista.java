package pe.edu.pe.Grupo02.structure;

import pe.edu.pe.Grupo02.model.Pedido;

public interface OperacionesLista {

    void insertar(Pedido pedido);
    void eliminar(int id);
    Pedido buscar(int id);

}
