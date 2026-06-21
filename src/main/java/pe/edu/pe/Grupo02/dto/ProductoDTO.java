package pe.edu.pe.Grupo02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stockActual;
    private int stockMinimo;
    private String unidadMedida;
    private int ubicacionId;
    private String nombreUbicacion;
    private boolean activo;
}