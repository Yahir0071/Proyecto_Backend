package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoStockDTO {
    private int id;
    private int productoId;
    private String productoNombre;
    private String tipo;
    private int cantidad;
    private LocalDateTime fecha;
    private String motivo;
}