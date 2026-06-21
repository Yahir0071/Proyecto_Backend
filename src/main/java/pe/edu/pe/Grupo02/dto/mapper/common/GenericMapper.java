package pe.edu.pe.Grupo02.dto.mapper.common;

import java.util.List;

import org.mapstruct.MappingTarget;
/**
 * https://github.com/heyuall/Java-record-dto-entity-mapper/blob/master/src/main/java/com/houssem/dtojavarecordmapper/mapper/common/GenericMapper.java * @param <D>
 * @param <E>
 */
public interface GenericMapper <D, E> {

    E dtoToEntity(D dto);

    D entityToDto(E entity);

    List<E> dtoToEntity(Iterable<D> dtos);

    List<D> entityToDto (Iterable<E> entities);

    void updateEntityFromDto(D dto, @MappingTarget E entity);

}