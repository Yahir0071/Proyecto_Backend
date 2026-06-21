package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.DetallePedidoDTO;
import pe.edu.pe.Grupo02.model.DetallePedido;

@Mapper
public interface DetallePedidoMapper {
    DetallePedidoMapper INSTANCE = Mappers.getMapper(DetallePedidoMapper.class);

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    @Mapping(source = "pedido.id", target = "pedidoId")
    DetallePedidoDTO toDTO(DetallePedido detalle);

    @Mapping(source = "productoId", target = "producto.id")
    @Mapping(source = "pedidoId", target = "pedido.id")
    DetallePedido toEntity(DetallePedidoDTO dto);
}