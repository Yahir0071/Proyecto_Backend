package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDTO {
    private int id;

    @NotBlank(message = "El código o nombre de ubicación es obligatorio.")
    @Size(max = 50, message = "La ubicación no puede superar los 50 caracteres.")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres.")
    private String descripcion;

    private int almacenId;
    private String almacenNombre;
}