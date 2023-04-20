package grafismo.service.mapper;

import grafismo.domain.Formation;
import grafismo.domain.Location;
import grafismo.domain.Team;
import grafismo.domain.Venue;
import grafismo.service.dto.FormationDTO;
import grafismo.service.dto.LocationDTO;
import grafismo.service.dto.TeamDTO;
import grafismo.service.dto.VenueDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "teamGraphicsName")
    @Mapping(target = "preferredFormation", source = "preferredFormation", qualifiedByName = "formationGraphicsName")
    @Mapping(target = "location", source = "location", qualifiedByName = "locationGraphicsName")
    @Mapping(target = "venues", source = "venues", qualifiedByName = "venueGraphicsNameSet")
    TeamDTO toDto(Team s);

    @Mapping(target = "removeVenue", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @Named("teamGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    TeamDTO toDtoTeamGraphicsName(Team team);

    @Named("formationGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    FormationDTO toDtoFormationGraphicsName(Formation formation);

    @Named("locationGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    LocationDTO toDtoLocationGraphicsName(Location location);

    @Named("venueGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    VenueDTO toDtoVenueGraphicsName(Venue venue);

    @Named("venueGraphicsNameSet")
    default Set<VenueDTO> toDtoVenueGraphicsNameSet(Set<Venue> venue) {
        return venue.stream().map(this::toDtoVenueGraphicsName).collect(Collectors.toSet());
    }
}
