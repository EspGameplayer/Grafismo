package grafismo.service.mapper;

import grafismo.domain.ActionKey;
import grafismo.domain.GraphicElement;
import grafismo.service.dto.ActionKeyDTO;
import grafismo.service.dto.GraphicElementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActionKey} and its DTO {@link ActionKeyDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActionKeyMapper extends EntityMapper<ActionKeyDTO, ActionKey> {
    @Mapping(target = "graphicElement", source = "graphicElement", qualifiedByName = "graphicElementName")
    ActionKeyDTO toDto(ActionKey s);

    @Named("graphicElementName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GraphicElementDTO toDtoGraphicElementName(GraphicElement graphicElement);
}
