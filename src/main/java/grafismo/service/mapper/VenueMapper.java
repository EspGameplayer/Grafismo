package grafismo.service.mapper;

import grafismo.domain.Location;
import grafismo.domain.Venue;
import grafismo.service.dto.LocationDTO;
import grafismo.service.dto.VenueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venue} and its DTO {@link VenueDTO}.
 */
@Mapper(componentModel = "spring")
public interface VenueMapper extends EntityMapper<VenueDTO, Venue> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationName")
    VenueDTO toDto(Venue s);

    @Named("locationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    LocationDTO toDtoLocationName(Location location);
}
