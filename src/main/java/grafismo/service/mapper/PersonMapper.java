package grafismo.service.mapper;

import grafismo.domain.Country;
import grafismo.domain.Location;
import grafismo.domain.Person;
import grafismo.service.dto.CountryDTO;
import grafismo.service.dto.LocationDTO;
import grafismo.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "nationality", source = "nationality", qualifiedByName = "countryName")
    @Mapping(target = "birthplace", source = "birthplace", qualifiedByName = "locationGraphicsName")
    PersonDTO toDto(Person s);

    @Named("countryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDTO toDtoCountryName(Country country);

    @Named("locationGraphicsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "graphicsName", source = "graphicsName")
    LocationDTO toDtoLocationGraphicsName(Location location);
}
