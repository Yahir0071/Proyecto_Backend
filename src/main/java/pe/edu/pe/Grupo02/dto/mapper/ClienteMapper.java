package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.ClienteDTO;
import pe.edu.pe.Grupo02.model.Cliente;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    ClienteDTO toDTO(Cliente cliente);
    Cliente toEntity(ClienteDTO dto);
}