package grafismo.service.mapper;

import grafismo.domain.Formation;
import grafismo.domain.TemplateFormation;
import grafismo.service.dto.FormationDTO;
import grafismo.service.dto.TemplateFormationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateFormation} and its DTO {@link TemplateFormationDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateFormationMapper extends EntityMapper<TemplateFormationDTO, TemplateFormation> {
    @Mapping(target = "formation", source = "formation", qualifiedByName = "formationGraphicsName")
    TemplateFormationDTO toDto(TemplateFormation s);

    @Named("formationGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    FormationDTO toDtoFormationGraphicsName(Formation formation);
}
