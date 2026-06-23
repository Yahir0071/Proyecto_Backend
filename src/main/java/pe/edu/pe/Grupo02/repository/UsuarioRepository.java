package pe.edu.pe.Grupo02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pe.Grupo02.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Genera automáticamente el SQL: SELECT * FROM usuarios WHERE username = ?
    Optional<Usuario> findByUsername(String username);
}
