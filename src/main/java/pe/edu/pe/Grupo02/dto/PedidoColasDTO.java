package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoColasDTO {
    private int tamanio;
    private boolean hayPedidosPendientes;
    private int idPrimero;
    private String estatusPrimero;
}