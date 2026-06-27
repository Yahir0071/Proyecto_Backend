// archivo: src/main/java/pe/edu/pe/Grupo02/controller/PedidosController.java
package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.PedidoDTO;
import pe.edu.pe.Grupo02.dto.PedidoColasDTO;
import pe.edu.pe.Grupo02.dto.EstadoHistorialDTO;
import pe.edu.pe.Grupo02.dto.mapper.PedidoMapper;
import pe.edu.pe.Grupo02.model.Pedido;
import pe.edu.pe.Grupo02.service.PedidoService;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PedidosController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper = PedidoMapper.INSTANCE;

    // ===== CRUD BÁSICO =====
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        List<PedidoDTO> dtos = pedidos.stream()
                .map(pedidoMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable int id) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoCreado = pedidoService.crear(pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedidoCreado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable int id, @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoActualizado = pedidoService.actualizar(id, pedido);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedidoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        pedidoService.eliminar(id);
        return ResponseEntity.ok("Pedido eliminado");
    }

    // ===== COLA DE PEDIDOS =====
    @PostMapping("/encolar")
    public ResponseEntity<String> encolarPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedidoService.encolarPedido(pedido);
        return ResponseEntity.ok("Pedido encolado exitosamente");
    }

    @PostMapping("/procesar-siguiente")
    public ResponseEntity<PedidoDTO> procesarSiguiente() {
        Pedido pedido = pedidoService.procesarSiguientePedido();
        if (pedido != null) {
            return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cola/primero")
    public ResponseEntity<PedidoDTO> obtenerPrimeroEnCola() {
        Pedido pedido = pedidoService.obtenerPrimeroEnCola();
        if (pedido != null) {
            return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cola/tamanio")
    public ResponseEntity<PedidoColasDTO> obtenerTamanioCola() {
        PedidoColasDTO dto = new PedidoColasDTO();
        int tamanio = pedidoService.obtenerTamanioCola();
        Pedido primero = pedidoService.obtenerPrimeroEnCola();

        dto.setTamanio(tamanio);
        dto.setHayPedidosPendientes(tamanio > 0);
        if (primero != null) {
            dto.setIdPrimero(primero.getId());
            dto.setEstatusPrimero(primero.getEstado());
        }

        return ResponseEntity.ok(dto);
    }

    // ===== HISTORIAL DE ESTADOS =====
    @PostMapping("/{idPedido}/estados")
    public ResponseEntity<String> agregarEstado(
            @PathVariable int idPedido,
            @RequestParam String estado) {
        pedidoService.agregarEstadoPedido(idPedido, estado);
        return ResponseEntity.ok("Estado agregado exitosamente");
    }

    @PostMapping("/{idPedido}/estados/deshacer")
    public ResponseEntity<EstadoHistorialDTO> deshacerEstado(@PathVariable int idPedido) {
        var estadoPop = pedidoService.deshacerEstadoPedido(idPedido);
        if (estadoPop != null) {
            EstadoHistorialDTO dto = new EstadoHistorialDTO(
                    estadoPop.getNombre(),
                    estadoPop.getFecha(),
                    estadoPop.getIdPedido()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idPedido}/estados/ultimo")
    public ResponseEntity<?> obtenerUltimoEstado(@PathVariable int idPedido) {
        String estado = pedidoService.obtenerUltimoEstadoPedido(idPedido);
        if (estado == null) {
            return ResponseEntity.ok("Sin historial en esta sesión");
        }
        return ResponseEntity.ok(estado);
    }
}