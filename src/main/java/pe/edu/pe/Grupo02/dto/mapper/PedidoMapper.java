package pe.edu.pe.Grupo02.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.edu.pe.Grupo02.dto.PedidoDTO;
import pe.edu.pe.Grupo02.model.Pedido;

@Mapper(uses = {DetallePedidoMapper.class})
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    PedidoDTO toDTO(Pedido pedido);

    @Mapping(source = "clienteId", target = "cliente.id")
    Pedido toEntity(PedidoDTO dto);
}