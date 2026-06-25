package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private int id;
    private int clienteId;
    private String clienteNombre;
    // --- NUEVOS CAMPOS AGREGADOS ---
    private LocalDateTime fecha;
    private String estado;
    private double total;
    private int prioridad;
    private int cantidadProductos;
    // -------------------------------
    private List<DetallePedidoDTO> detalles;
}