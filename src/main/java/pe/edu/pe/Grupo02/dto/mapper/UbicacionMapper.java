package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.UbicacionDTO;
import pe.edu.pe.Grupo02.model.Ubicacion;

@Mapper
public interface UbicacionMapper {
    UbicacionMapper INSTANCE = Mappers.getMapper(UbicacionMapper.class);

    @Mapping(source = "almacen.id", target = "almacenId")
    @Mapping(source = "almacen.nombre", target = "almacenNombre")
    UbicacionDTO toDTO(Ubicacion ubicacion);

    @Mapping(source = "almacenId", target = "almacen.id")
    Ubicacion toEntity(UbicacionDTO dto);
}