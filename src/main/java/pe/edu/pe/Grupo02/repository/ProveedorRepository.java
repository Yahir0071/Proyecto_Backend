package pe.edu.pe.Grupo02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pe.Grupo02.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}