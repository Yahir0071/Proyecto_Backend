package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.ProductoDTO;
import pe.edu.pe.Grupo02.model.Producto;

@Mapper
public interface ProductoMapper {
    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    @Mapping(source = "ubicacion.id", target = "ubicacionId")
    @Mapping(source = "ubicacion.nombre", target = "nombreUbicacion")
    // AGREGA ESTAS DOS LÍNEAS:
    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "proveedor.nombre", target = "proveedorNombre")
    ProductoDTO toDTO(Producto producto);

    @Mapping(source = "ubicacionId", target = "ubicacion.id")
    @Mapping(source = "proveedorId", target = "proveedor.id")
    Producto toEntity(ProductoDTO dto);
}