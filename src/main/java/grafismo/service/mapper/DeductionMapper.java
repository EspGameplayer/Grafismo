package grafismo.service.mapper;

import grafismo.domain.Competition;
import grafismo.domain.Deduction;
import grafismo.domain.Match;
import grafismo.domain.Team;
import grafismo.service.dto.CompetitionDTO;
import grafismo.service.dto.DeductionDTO;
import grafismo.service.dto.MatchDTO;
import grafismo.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deduction} and its DTO {@link DeductionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeductionMapper extends EntityMapper<DeductionDTO, Deduction> {
    @Mapping(target = "team", source = "team", qualifiedByName = "teamGraphicsName")
    @Mapping(target = "competition", source = "competition", qualifiedByName = "competitionGraphicsName")
    @Mapping(target = "match", source = "match", qualifiedByName = "matchId")
    DeductionDTO toDto(Deduction s);

    @Named("teamGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    TeamDTO toDtoTeamGraphicsName(Team team);

    @Named("competitionGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    CompetitionDTO toDtoCompetitionGraphicsName(Competition competition);

    @Named("matchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchDTO toDtoMatchId(Match match);
}
