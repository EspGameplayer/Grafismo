package grafismo.service.mapper;

import grafismo.domain.Person;
import grafismo.domain.StaffMember;
import grafismo.service.dto.PersonDTO;
import grafismo.service.dto.StaffMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StaffMember} and its DTO {@link StaffMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface StaffMemberMapper extends EntityMapper<StaffMemberDTO, StaffMember> {
    @Mapping(target = "person", source = "person", qualifiedByName = "personGraphicsName")
    StaffMemberDTO toDto(StaffMember s);

    @Named("personGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    PersonDTO toDtoPersonGraphicsName(Person person);
}
