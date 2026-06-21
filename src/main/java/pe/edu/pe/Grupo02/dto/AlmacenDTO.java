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
public class AlmacenDTO {
    private int id;
    private String nombre;
    private String direccion;
    private int filas;
    private int columnas;
    private List<UbicacionDTO> ubicaciones;
}