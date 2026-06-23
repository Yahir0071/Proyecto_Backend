package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.UsuarioDTO;
import pe.edu.pe.Grupo02.dto.mapper.UsuarioMapper;
import pe.edu.pe.Grupo02.model.Usuario;
import pe.edu.pe.Grupo02.service.UsuarioService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper = UsuarioMapper.INSTANCE;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioDTO dto) {
        try {
            Usuario usuarioEntity = mapper.toEntity(dto);
            usuarioService.registrarUsuario(usuarioEntity);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO dto) {
        try {
            Usuario usuarioLogueado = usuarioService.login(dto.getUsername(), dto.getPassword());
            UsuarioDTO respuestaDTO = mapper.toDTO(usuarioLogueado);
            return ResponseEntity.ok(respuestaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
