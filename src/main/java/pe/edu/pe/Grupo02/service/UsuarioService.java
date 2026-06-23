package pe.edu.pe.Grupo02.service;

import pe.edu.pe.Grupo02.model.Usuario;

public interface UsuarioService {

    Usuario registrarUsuario(Usuario usuario);

    Usuario login(String username, String password);
}
