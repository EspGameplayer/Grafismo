package grafismo.service.mapper;

import grafismo.domain.Match;
import grafismo.domain.MatchAction;
import grafismo.domain.MatchPlayer;
import grafismo.service.dto.MatchActionDTO;
import grafismo.service.dto.MatchDTO;
import grafismo.service.dto.MatchPlayerDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchAction} and its DTO {@link MatchActionDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchActionMapper extends EntityMapper<MatchActionDTO, MatchAction> {
    @Mapping(target = "match", source = "match", qualifiedByName = "matchId")
    @Mapping(target = "matchPlayers", source = "matchPlayers", qualifiedByName = "matchPlayerIdSet")
    MatchActionDTO toDto(MatchAction s);

    @Mapping(target = "removeMatchPlayer", ignore = true)
    MatchAction toEntity(MatchActionDTO matchActionDTO);

    @Named("matchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchDTO toDtoMatchId(Match match);

    @Named("matchPlayerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchPlayerDTO toDtoMatchPlayerId(MatchPlayer matchPlayer);

    @Named("matchPlayerIdSet")
    default Set<MatchPlayerDTO> toDtoMatchPlayerIdSet(Set<MatchPlayer> matchPlayer) {
        return matchPlayer.stream().map(this::toDtoMatchPlayerId).collect(Collectors.toSet());
    }
}
