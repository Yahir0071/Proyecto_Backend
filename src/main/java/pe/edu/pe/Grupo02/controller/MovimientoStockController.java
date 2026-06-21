package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.MovimientoStockDTO;
import pe.edu.pe.Grupo02.dto.mapper.MovimientoStockMapper;
import pe.edu.pe.Grupo02.model.MovimientoStock;
import pe.edu.pe.Grupo02.service.MovimientoStockService;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos-stock")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovimientoStockController {

    private final MovimientoStockService movimientoStockService;
    private final MovimientoStockMapper mapper = MovimientoStockMapper.INSTANCE;

    @GetMapping
    public List<MovimientoStockDTO> listar() {
        return movimientoStockService.listarTodos().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStockDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toDTO(movimientoStockService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<MovimientoStockDTO> crear(@RequestBody MovimientoStockDTO dto) {
        MovimientoStock movimiento = mapper.toEntity(dto);
        MovimientoStock creado = movimientoStockService.crear(movimiento);
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @GetMapping("/producto/{productoId}")
    public List<MovimientoStockDTO> listarPorProducto(@PathVariable int productoId) {
        return movimientoStockService.listarPorProducto(productoId).stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/tipo/{tipo}")
    public List<MovimientoStockDTO> listarPorTipo(@PathVariable String tipo) {
        return movimientoStockService.listarPorTipo(tipo).stream().map(mapper::toDTO).toList();
    }
}
