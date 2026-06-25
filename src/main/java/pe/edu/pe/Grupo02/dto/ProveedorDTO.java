package pe.edu.pe.Grupo02.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProveedorDTO {
    private int id;
    private String nombre;
    private String ruc;
    private String contacto;
    private String telefono;
    private String direccion;
}
