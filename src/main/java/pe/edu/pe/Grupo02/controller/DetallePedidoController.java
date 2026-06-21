package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.DetallePedidoDTO;
import pe.edu.pe.Grupo02.dto.mapper.DetallePedidoMapper;
import pe.edu.pe.Grupo02.model.DetallePedido;
import pe.edu.pe.Grupo02.service.DetallePedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedidos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;
    private final DetallePedidoMapper mapper = DetallePedidoMapper.INSTANCE;

    @GetMapping
    public List<DetallePedidoDTO> listar() {
        return detallePedidoService.listarTodos().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toDTO(detallePedidoService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoDTO> crear(@RequestBody DetallePedidoDTO dto) {
        DetallePedido detalle = mapper.toEntity(dto);
        DetallePedido creado = detallePedidoService.crear(detalle);
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> actualizar(@PathVariable int id, @RequestBody DetallePedidoDTO dto) {
        DetallePedido detalle = mapper.toEntity(dto);
        DetallePedido actualizado = detallePedidoService.actualizar(id, detalle);
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        detallePedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
