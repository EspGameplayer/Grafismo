package grafismo.service.mapper;

import grafismo.domain.Formation;
import grafismo.domain.Lineup;
import grafismo.domain.MatchPlayer;
import grafismo.domain.TeamStaffMember;
import grafismo.service.dto.FormationDTO;
import grafismo.service.dto.LineupDTO;
import grafismo.service.dto.MatchPlayerDTO;
import grafismo.service.dto.TeamStaffMemberDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lineup} and its DTO {@link LineupDTO}.
 */
@Mapper(componentModel = "spring")
public interface LineupMapper extends EntityMapper<LineupDTO, Lineup> {
    @Mapping(target = "captain", source = "captain", qualifiedByName = "matchPlayerId")
    @Mapping(target = "dt", source = "dt", qualifiedByName = "teamStaffMemberId")
    @Mapping(target = "dt2", source = "dt2", qualifiedByName = "teamStaffMemberId")
    @Mapping(target = "teamDelegate", source = "teamDelegate", qualifiedByName = "teamStaffMemberId")
    @Mapping(target = "formation", source = "formation", qualifiedByName = "formationGraphicsName")
    @Mapping(target = "matchPlayers", source = "matchPlayers", qualifiedByName = "matchPlayerIdSet")
    LineupDTO toDto(Lineup s);

    @Mapping(target = "removeMatchPlayer", ignore = true)
    Lineup toEntity(LineupDTO lineupDTO);

    @Named("matchPlayerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchPlayerDTO toDtoMatchPlayerId(MatchPlayer matchPlayer);

    @Named("matchPlayerIdSet")
    default Set<MatchPlayerDTO> toDtoMatchPlayerIdSet(Set<MatchPlayer> matchPlayer) {
        return matchPlayer.stream().map(this::toDtoMatchPlayerId).collect(Collectors.toSet());
    }

    @Named("teamStaffMemberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamStaffMemberDTO toDtoTeamStaffMemberId(TeamStaffMember teamStaffMember);

    @Named("formationGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    FormationDTO toDtoFormationGraphicsName(Formation formation);
}
