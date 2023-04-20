package grafismo.service.mapper;

import grafismo.domain.StaffMember;
import grafismo.domain.Team;
import grafismo.domain.TeamStaffMember;
import grafismo.service.dto.StaffMemberDTO;
import grafismo.service.dto.TeamDTO;
import grafismo.service.dto.TeamStaffMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamStaffMember} and its DTO {@link TeamStaffMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamStaffMemberMapper extends EntityMapper<TeamStaffMemberDTO, TeamStaffMember> {
    @Mapping(target = "team", source = "team", qualifiedByName = "teamGraphicsName")
    @Mapping(target = "staffMember", source = "staffMember", qualifiedByName = "staffMemberId")
    TeamStaffMemberDTO toDto(TeamStaffMember s);

    @Named("teamGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    TeamDTO toDtoTeamGraphicsName(Team team);

    @Named("staffMemberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StaffMemberDTO toDtoStaffMemberId(StaffMember staffMember);
}
