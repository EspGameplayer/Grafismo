package grafismo.service.mapper;

import grafismo.domain.Competition;
import grafismo.domain.Country;
import grafismo.domain.Referee;
import grafismo.domain.Team;
import grafismo.service.dto.CompetitionDTO;
import grafismo.service.dto.CountryDTO;
import grafismo.service.dto.RefereeDTO;
import grafismo.service.dto.TeamDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competition} and its DTO {@link CompetitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetitionMapper extends EntityMapper<CompetitionDTO, Competition> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "competitionGraphicsName")
    @Mapping(target = "country", source = "country", qualifiedByName = "countryName")
    @Mapping(target = "teams", source = "teams", qualifiedByName = "teamGraphicsNameSet")
    @Mapping(target = "referees", source = "referees", qualifiedByName = "refereeGraphicsNameSet")
    CompetitionDTO toDto(Competition s);

    @Mapping(target = "removeTeam", ignore = true)
    @Mapping(target = "removeReferee", ignore = true)
    Competition toEntity(CompetitionDTO competitionDTO);

    @Named("competitionGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    CompetitionDTO toDtoCompetitionGraphicsName(Competition competition);

    @Named("countryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDTO toDtoCountryName(Country country);

    @Named("teamGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    TeamDTO toDtoTeamGraphicsName(Team team);

    @Named("teamGraphicsNameSet")
    default Set<TeamDTO> toDtoTeamGraphicsNameSet(Set<Team> team) {
        return team.stream().map(this::toDtoTeamGraphicsName).collect(Collectors.toSet());
    }

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
