// archivo: src/main/java/pe/edu/pe/Grupo02/controller/UbicacionesController.java
package pe.edu.pe.Grupo02.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.Grupo02.dto.UbicacionDTO;
import pe.edu.pe.Grupo02.dto.RutaUbicacionDTO;
import pe.edu.pe.Grupo02.dto.mapper.UbicacionMapper;
import pe.edu.pe.Grupo02.model.Ubicacion;
import pe.edu.pe.Grupo02.service.UbicacionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UbicacionesController {

    private final UbicacionService ubicacionService;
    private final UbicacionMapper ubicacionMapper = UbicacionMapper.INSTANCE;

    // ===== CRUD BÁSICO =====
    @GetMapping
    public ResponseEntity<List<UbicacionDTO>> listarTodas() {
        List<Ubicacion> ubicaciones = ubicacionService.listarTodas();
        List<UbicacionDTO> dtos = ubicaciones.stream()
                .map(ubicacionMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> obtenerPorId(@PathVariable int id) {
        Ubicacion ubicacion = ubicacionService.obtenerPorId(id);
        return ResponseEntity.ok(ubicacionMapper.toDTO(ubicacion));
    }

    @PostMapping
    public ResponseEntity<UbicacionDTO> crear(@RequestBody UbicacionDTO ubicacionDTO) {
        Ubicacion ubicacion = ubicacionMapper.toEntity(ubicacionDTO);
        Ubicacion ubicacionCreada = ubicacionService.crear(ubicacion);
        return ResponseEntity.ok(ubicacionMapper.toDTO(ubicacionCreada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> actualizar(@PathVariable int id, @RequestBody UbicacionDTO ubicacionDTO) {
        Ubicacion ubicacion = ubicacionMapper.toEntity(ubicacionDTO);
        Ubicacion ubicacionActualizada = ubicacionService.actualizar(id, ubicacion);
        return ResponseEntity.ok(ubicacionMapper.toDTO(ubicacionActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Verificar si tiene productos
            int productosCount = ubicacionService.countProductosByUbicacion(id);

            if (productosCount > 0) {
                // ❌ No se puede eliminar
                response.put("exitoso", false);
                response.put("mensaje", "No se puede eliminar. Esta ubicación tiene " + productosCount + " producto(s) asignado(s).");
                return ResponseEntity.ok(response);
            }

            // ✅ Eliminar
            ubicacionService.eliminar(id);
            response.put("exitoso", true);
            response.put("mensaje", "✅ Ubicación eliminada exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // ===== RUTAS Y LOGÍSTICA =====
    @GetMapping("/ruta-corta")
    public ResponseEntity<RutaUbicacionDTO> obtenerRutaMasCorta(
            @RequestParam int origen,
            @RequestParam int destino) {
        List<Integer> ruta = ubicacionService.obtenerRutaMasCorta(origen, destino);
        RutaUbicacionDTO dto = new RutaUbicacionDTO();
        dto.setRuta(ruta);
        dto.setDescripcion("Ruta usando algoritmo Dijkstra");
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/ruta-bfs")
    public ResponseEntity<RutaUbicacionDTO> obtenerRutaBFS(
            @RequestParam int origen,
            @RequestParam int destino) {
        List<Integer> ruta = ubicacionService.obtenerRutaBFS(origen, destino);
        RutaUbicacionDTO dto = new RutaUbicacionDTO();
        dto.setRuta(ruta);
        dto.setDescripcion("Ruta usando algoritmo BFS");
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/conectar")
    public ResponseEntity<String> conectarUbicaciones(
            @RequestParam int origen,
            @RequestParam int destino,
            @RequestParam double distancia) {
        ubicacionService.conectarUbicaciones(origen, destino, distancia);
        return ResponseEntity.ok("Ubicaciones conectadas exitosamente");
    }

    @GetMapping("/por-almacen/{almacenId}")
    public ResponseEntity<List<UbicacionDTO>> listarPorAlmacen(@PathVariable int almacenId) {
        List<Ubicacion> ubicaciones = ubicacionService.listarPorAlmacen(almacenId);
        List<UbicacionDTO> dtos = ubicaciones.stream()
                .map(ubicacionMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

}