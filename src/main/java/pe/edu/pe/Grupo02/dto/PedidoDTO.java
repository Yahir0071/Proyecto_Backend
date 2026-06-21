package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private int id;
    private int clienteId;
    private String clienteNombre;
    private List<DetallePedidoDTO> detalles;
}