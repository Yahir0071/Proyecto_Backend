package pe.edu.pe.Grupo02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pe.Grupo02.model.MovimientoStock;

import java.util.List;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Integer> {
    List<MovimientoStock> findByProductoId(int productoId);
    List<MovimientoStock> findByTipo(String tipo);
}

