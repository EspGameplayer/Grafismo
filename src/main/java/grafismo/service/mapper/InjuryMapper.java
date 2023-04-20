package grafismo.service.mapper;

import grafismo.domain.Injury;
import grafismo.domain.Match;
import grafismo.domain.Player;
import grafismo.service.dto.InjuryDTO;
import grafismo.service.dto.MatchDTO;
import grafismo.service.dto.PlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Injury} and its DTO {@link InjuryDTO}.
 */
@Mapper(componentModel = "spring")
public interface InjuryMapper extends EntityMapper<InjuryDTO, Injury> {
    @Mapping(target = "player", source = "player", qualifiedByName = "playerId")
    @Mapping(target = "match", source = "match", qualifiedByName = "matchId")
    InjuryDTO toDto(Injury s);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);

    @Named("matchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchDTO toDtoMatchId(Match match);
}
