package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {
    private int id;
    private int productoId;
    private String productoNombre;
    private int pedidoId;
    private int cantidad;
    private double precioUnitario;
}