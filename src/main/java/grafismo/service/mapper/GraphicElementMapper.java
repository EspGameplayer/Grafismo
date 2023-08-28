package grafismo.service.mapper;

import grafismo.domain.GraphicElement;
import grafismo.service.dto.GraphicElementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GraphicElement} and its DTO {@link GraphicElementDTO}.
 */
@Mapper(componentModel = "spring")
public interface GraphicElementMapper extends EntityMapper<GraphicElementDTO, GraphicElement> {}
