package pe.edu.pe.Grupo02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pe.Grupo02.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
