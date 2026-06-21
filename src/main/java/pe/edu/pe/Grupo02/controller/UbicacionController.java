package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.UbicacionDTO;
import pe.edu.pe.Grupo02.dto.mapper.UbicacionMapper;
import pe.edu.pe.Grupo02.model.Ubicacion;
import pe.edu.pe.Grupo02.service.UbicacionService;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionService ubicacionService;
    private final UbicacionMapper mapper = UbicacionMapper.INSTANCE;

    @GetMapping
    public List<UbicacionDTO> listar() {
        return ubicacionService.listarTodas().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toDTO(ubicacionService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<UbicacionDTO> crear(@RequestBody UbicacionDTO dto) {
        Ubicacion ubicacion = mapper.toEntity(dto);
        Ubicacion creado = ubicacionService.crear(ubicacion);
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> actualizar(@PathVariable int id, @RequestBody UbicacionDTO dto) {
        Ubicacion ubicacion = mapper.toEntity(dto);
        Ubicacion actualizado = ubicacionService.actualizar(id, ubicacion);
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        ubicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}