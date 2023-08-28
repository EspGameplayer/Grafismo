package grafismo.service.mapper;

import grafismo.domain.Lineup;
import grafismo.domain.Match;
import grafismo.domain.MatchPlayer;
import grafismo.domain.Matchday;
import grafismo.domain.Referee;
import grafismo.domain.Shirt;
import grafismo.domain.Team;
import grafismo.domain.TeamStaffMember;
import grafismo.domain.Venue;
import grafismo.service.dto.LineupDTO;
import grafismo.service.dto.MatchDTO;
import grafismo.service.dto.MatchPlayerDTO;
import grafismo.service.dto.MatchdayDTO;
import grafismo.service.dto.RefereeDTO;
import grafismo.service.dto.ShirtDTO;
import grafismo.service.dto.TeamDTO;
import grafismo.service.dto.TeamStaffMemberDTO;
import grafismo.service.dto.VenueDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Match} and its DTO {@link MatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchMapper extends EntityMapper<MatchDTO, Match> {
    @Mapping(target = "motm", source = "motm", qualifiedByName = "matchPlayerId")
    @Mapping(target = "homeLineup", source = "homeLineup", qualifiedByName = "lineupId")
    @Mapping(target = "awayLineup", source = "awayLineup", qualifiedByName = "lineupId")
    @Mapping(target = "homeTeam", source = "homeTeam", qualifiedByName = "teamGraphicsName")
    @Mapping(target = "awayTeam", source = "awayTeam", qualifiedByName = "teamGraphicsName")
    @Mapping(target = "venue", source = "venue", qualifiedByName = "venueGraphicsName")
    @Mapping(target = "matchDelegate", source = "matchDelegate", qualifiedByName = "teamStaffMemberId")
    @Mapping(target = "homeShirt", source = "homeShirt", qualifiedByName = "shirtId")
    @Mapping(target = "awayShirt", source = "awayShirt", qualifiedByName = "shirtId")
    @Mapping(target = "matchday", source = "matchday", qualifiedByName = "matchdayName")
    @Mapping(target = "referees", source = "referees", qualifiedByName = "refereeGraphicsNameSet")
    MatchDTO toDto(Match s);

    @Mapping(target = "removeReferee", ignore = true)
    Match toEntity(MatchDTO matchDTO);

    @Named("matchPlayerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchPlayerDTO toDtoMatchPlayerId(MatchPlayer matchPlayer);

    @Named("lineupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LineupDTO toDtoLineupId(Lineup lineup);

    @Named("teamGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    TeamDTO toDtoTeamGraphicsName(Team team);

    @Named("venueGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    VenueDTO toDtoVenueGraphicsName(Venue venue);

    @Named("teamStaffMemberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamStaffMemberDTO toDtoTeamStaffMemberId(TeamStaffMember teamStaffMember);

    @Named("shirtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShirtDTO toDtoShirtId(Shirt shirt);

    @Named("matchdayName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    MatchdayDTO toDtoMatchdayName(Matchday matchday);

    @Named("refereeGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    RefereeDTO toDtoRefereeGraphicsName(Referee referee);

    @Named("refereeGraphicsNameSet")
    default Set<RefereeDTO> toDtoRefereeGraphicsNameSet(Set<Referee> referee) {
        return referee.stream().map(this::toDtoRefereeGraphicsName).collect(Collectors.toSet());
    }
}
