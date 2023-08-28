package grafismo.service.mapper;

import grafismo.domain.Player;
import grafismo.domain.Team;
import grafismo.domain.TeamPlayer;
import grafismo.service.dto.PlayerDTO;
import grafismo.service.dto.TeamDTO;
import grafismo.service.dto.TeamPlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamPlayer} and its DTO {@link TeamPlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamPlayerMapper extends EntityMapper<TeamPlayerDTO, TeamPlayer> {
    @Mapping(target = "team", source = "team", qualifiedByName = "teamGraphicsName")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerGraphicsName")
    TeamPlayerDTO toDto(TeamPlayer s);

    @Named("teamGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    TeamDTO toDtoTeamGraphicsName(Team team);

    @Named("playerGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    PlayerDTO toDtoPlayerGraphicsName(Player player);
}
