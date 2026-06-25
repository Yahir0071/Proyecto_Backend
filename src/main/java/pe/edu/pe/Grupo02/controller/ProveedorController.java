package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.ProveedorDTO;
import pe.edu.pe.Grupo02.dto.mapper.ProveedorMapper;
import pe.edu.pe.Grupo02.model.Proveedor;
import pe.edu.pe.Grupo02.service.ProveedorService;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final ProveedorMapper mapper = ProveedorMapper.INSTANCE;

    @GetMapping
    public List<ProveedorDTO> listar() {
        return proveedorService.listarTodos().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toDTO(proveedorService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> crear(@RequestBody ProveedorDTO dto) {
        Proveedor creado = proveedorService.crear(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> actualizar(@PathVariable int id, @RequestBody ProveedorDTO dto) {
        Proveedor actualizado = proveedorService.actualizar(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}