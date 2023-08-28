package grafismo.service.mapper;

import grafismo.domain.Country;
import grafismo.domain.Location;
import grafismo.service.dto.CountryDTO;
import grafismo.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryName")
    LocationDTO toDto(Location s);

    @Named("countryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDTO toDtoCountryName(Country country);
}
