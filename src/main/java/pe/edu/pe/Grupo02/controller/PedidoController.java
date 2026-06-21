package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.PedidoDTO;
import pe.edu.pe.Grupo02.dto.mapper.PedidoMapper;
import pe.edu.pe.Grupo02.model.Pedido;
import pe.edu.pe.Grupo02.service.PedidoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper mapper = PedidoMapper.INSTANCE;

    @GetMapping
    public List<PedidoDTO> listar() {
        return pedidoService.listarTodos().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(mapper.toDTO(pedidoService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO dto) {
        Pedido pedido = mapper.toEntity(dto);
        Pedido creado = pedidoService.crear(pedido);
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable int id, @RequestBody PedidoDTO dto) {
        Pedido pedido = mapper.toEntity(dto);
        Pedido actualizado = pedidoService.actualizar(id, pedido);
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<PedidoDTO> buscarPorCliente(@PathVariable int clienteId) {
        return pedidoService.buscarPorCliente(clienteId).stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/estado/{estado}")
    public List<PedidoDTO> buscarPorEstado(@PathVariable String estado) {
        return pedidoService.buscarPorEstado(estado).stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/rango")
    public List<PedidoDTO> buscarPorRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return pedidoService.buscarPorRangoFecha(desde, hasta).stream().map(mapper::toDTO).toList();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> cambiarEstado(@PathVariable int id, @RequestParam String estado) {
        return ResponseEntity.ok(mapper.toDTO(pedidoService.actualizarEstadoPedido(id, estado)));
    }
}
