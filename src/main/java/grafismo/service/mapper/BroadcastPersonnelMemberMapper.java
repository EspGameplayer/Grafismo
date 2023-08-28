package grafismo.service.mapper;

import grafismo.domain.BroadcastPersonnelMember;
import grafismo.domain.Person;
import grafismo.service.dto.BroadcastPersonnelMemberDTO;
import grafismo.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BroadcastPersonnelMember} and its DTO {@link BroadcastPersonnelMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface BroadcastPersonnelMemberMapper extends EntityMapper<BroadcastPersonnelMemberDTO, BroadcastPersonnelMember> {
    @Mapping(target = "person", source = "person", qualifiedByName = "personGraphicsName")
    BroadcastPersonnelMemberDTO toDto(BroadcastPersonnelMember s);

    @Named("personGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    PersonDTO toDtoPersonGraphicsName(Person person);
}
