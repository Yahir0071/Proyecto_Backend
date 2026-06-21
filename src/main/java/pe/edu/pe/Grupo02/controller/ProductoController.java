package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.ProductoDTO;
import pe.edu.pe.Grupo02.dto.mapper.ProductoMapper;
import pe.edu.pe.Grupo02.model.Producto;
import pe.edu.pe.Grupo02.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper mapper = ProductoMapper.INSTANCE;

    @GetMapping
    public List<ProductoDTO> listar() {
        return productoService.listarTodos().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable int id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(mapper.toDTO(producto));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO dto) {
        Producto producto = mapper.toEntity(dto);
        Producto creado = productoService.crear(producto);
        return ResponseEntity.ok(mapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable int id, @RequestBody ProductoDTO dto) {
        Producto producto = mapper.toEntity(dto);
        Producto actualizado = productoService.actualizar(id, producto);
        return ResponseEntity.ok(mapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entrada")
    public ResponseEntity<String> registrarEntrada(@PathVariable int id, @RequestParam int cantidad) {
        productoService.registrarEntradaMercancia(id, cantidad);
        return ResponseEntity.ok("Stock actualizado correctamente");
    }

    @GetMapping("/alertas")
    public List<ProductoDTO> obtenerAlertas() {
        return productoService.obtenerAlertasDeStockBajo().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/categoria/{categoria}")
    public List<ProductoDTO> buscarPorCategoria(@PathVariable String categoria) {
        return productoService.buscarPorCategoria(categoria).stream().map(mapper::toDTO).toList();
    }

    // LISTO: Método PUT corregido usando tu propio Service e IDs de tipo int
    @PutMapping("/{id}/stock")
    public ResponseEntity<String> agregarStock(@PathVariable int id, @RequestParam int cantidad) {
        // Reutilizamos tu lógica existente del service que ya maneja la persistencia
        productoService.registrarEntradaMercancia(id, cantidad);
        return ResponseEntity.ok("Stock actualizado correctamente");
    }
}