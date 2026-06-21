package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.ClienteDTO;
import pe.edu.pe.Grupo02.dto.mapper.ClienteMapper;
import pe.edu.pe.Grupo02.model.Cliente;
import pe.edu.pe.Grupo02.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper mapper = ClienteMapper.INSTANCE;

    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteService.listarTodos().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toDTO(clienteService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@RequestBody ClienteDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        Cliente creado = clienteService.crear(cliente);
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable int id, @RequestBody ClienteDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        Cliente actualizado = clienteService.actualizar(id, cliente);
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
