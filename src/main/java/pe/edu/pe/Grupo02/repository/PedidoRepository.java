package pe.edu.pe.Grupo02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pe.Grupo02.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstadoOrderByPrioridadDesc(String estado);
    List<Pedido> findByClienteId(int clienteId);
    List<Pedido> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}

