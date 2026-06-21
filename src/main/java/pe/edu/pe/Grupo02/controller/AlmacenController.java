package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.AlmacenDTO;
import pe.edu.pe.Grupo02.dto.mapper.AlmacenMapper;
import pe.edu.pe.Grupo02.model.Almacen;
import pe.edu.pe.Grupo02.service.AlmacenService;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AlmacenController {

    private final AlmacenService almacenService;
    private final AlmacenMapper mapper = AlmacenMapper.INSTANCE;

    @GetMapping
    public List<AlmacenDTO> listar() {
        return almacenService.listarTodos().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlmacenDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toDTO(almacenService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<AlmacenDTO> crear(@RequestBody AlmacenDTO dto) {
        Almacen almacen = mapper.toEntity(dto);
        Almacen creado = almacenService.crear(almacen);
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlmacenDTO> actualizar(@PathVariable int id, @RequestBody AlmacenDTO dto) {
        Almacen almacen = mapper.toEntity(dto);
        Almacen actualizado = almacenService.actualizar(id, almacen);
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        almacenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
