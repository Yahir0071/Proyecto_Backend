package pe.edu.pe.Grupo02.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.Grupo02.model.Usuario;
import pe.edu.pe.Grupo02.repository.UsuarioRepository;
import pe.edu.pe.Grupo02.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario login(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
            return usuarioOpt.get();
        }

        throw new RuntimeException("Usuario o contraseña incorrectos");
    }
}

