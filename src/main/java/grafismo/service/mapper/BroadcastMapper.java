package grafismo.service.mapper;

import grafismo.domain.Broadcast;
import grafismo.domain.BroadcastPersonnelMember;
import grafismo.domain.Match;
import grafismo.domain.SystemConfiguration;
import grafismo.service.dto.BroadcastDTO;
import grafismo.service.dto.BroadcastPersonnelMemberDTO;
import grafismo.service.dto.MatchDTO;
import grafismo.service.dto.SystemConfigurationDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Broadcast} and its DTO {@link BroadcastDTO}.
 */
@Mapper(componentModel = "spring")
public interface BroadcastMapper extends EntityMapper<BroadcastDTO, Broadcast> {
    @Mapping(target = "match", source = "match", qualifiedByName = "matchId")
    @Mapping(target = "systemConfiguration", source = "systemConfiguration", qualifiedByName = "systemConfigurationId")
    @Mapping(
        target = "broadcastPersonnelMembers",
        source = "broadcastPersonnelMembers",
        qualifiedByName = "broadcastPersonnelMemberGraphicsNameSet"
    )
    BroadcastDTO toDto(Broadcast s);

    @Mapping(target = "removeBroadcastPersonnelMember", ignore = true)
    Broadcast toEntity(BroadcastDTO broadcastDTO);

    @Named("matchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchDTO toDtoMatchId(Match match);

    @Named("systemConfigurationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SystemConfigurationDTO toDtoSystemConfigurationId(SystemConfiguration systemConfiguration);

    @Named("broadcastPersonnelMemberGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    BroadcastPersonnelMemberDTO toDtoBroadcastPersonnelMemberGraphicsName(BroadcastPersonnelMember broadcastPersonnelMember);

    @Named("broadcastPersonnelMemberGraphicsNameSet")
    default Set<BroadcastPersonnelMemberDTO> toDtoBroadcastPersonnelMemberGraphicsNameSet(
        Set<BroadcastPersonnelMember> broadcastPersonnelMember
    ) {
        return broadcastPersonnelMember.stream().map(this::toDtoBroadcastPersonnelMemberGraphicsName).collect(Collectors.toSet());
    }
}
