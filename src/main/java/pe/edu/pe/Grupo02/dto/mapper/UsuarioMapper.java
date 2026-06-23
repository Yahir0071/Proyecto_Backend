package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.UsuarioDTO;
import pe.edu.pe.Grupo02.model.Usuario;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    // 🔒 Por seguridad, ignoramos el password al enviar los datos hacia el frontend (toDTO)
    @Mapping(target = "password", ignore = true)
    UsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(UsuarioDTO dto);
}
