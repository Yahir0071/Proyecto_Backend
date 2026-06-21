package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private int id;
    private String nombre;
    private String dni;
    private String telefono;
    private String direccion;
}