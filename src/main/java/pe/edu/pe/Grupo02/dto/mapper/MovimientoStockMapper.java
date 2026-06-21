package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.MovimientoStockDTO;
import pe.edu.pe.Grupo02.model.MovimientoStock;

@Mapper
public interface MovimientoStockMapper {
    MovimientoStockMapper INSTANCE = Mappers.getMapper(MovimientoStockMapper.class);

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    MovimientoStockDTO toDTO(MovimientoStock movimiento);

    @Mapping(source = "productoId", target = "producto.id")
    MovimientoStock toEntity(MovimientoStockDTO dto);
}