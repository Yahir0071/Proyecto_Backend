package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.ProveedorDTO;
import pe.edu.pe.Grupo02.model.Proveedor;

@Mapper
public interface ProveedorMapper {
    ProveedorMapper INSTANCE = Mappers.getMapper(ProveedorMapper.class);
    ProveedorDTO toDTO(Proveedor proveedor);
    Proveedor toEntity(ProveedorDTO dto);
}
