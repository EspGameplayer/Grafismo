package grafismo.service.mapper;

import grafismo.domain.MatchPlayer;
import grafismo.domain.Position;
import grafismo.domain.TeamPlayer;
import grafismo.service.dto.MatchPlayerDTO;
import grafismo.service.dto.PositionDTO;
import grafismo.service.dto.TeamPlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchPlayer} and its DTO {@link MatchPlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchPlayerMapper extends EntityMapper<MatchPlayerDTO, MatchPlayer> {
    @Mapping(target = "teamPlayer", source = "teamPlayer", qualifiedByName = "teamPlayerId")
    @Mapping(target = "position", source = "position", qualifiedByName = "positionAbb")
    MatchPlayerDTO toDto(MatchPlayer s);

    @Named("teamPlayerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamPlayerDTO toDtoTeamPlayerId(TeamPlayer teamPlayer);

    @Named("positionAbb")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "abb", source = "abb")
    PositionDTO toDtoPositionAbb(Position position);
}
