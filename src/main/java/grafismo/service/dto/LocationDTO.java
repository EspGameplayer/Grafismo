package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Location} entity.
 */
public class LocationDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String graphicsName;

    private Integer population;

    private Integer censusYear;

    private String denonym;

    private CountryDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getCensusYear() {
        return censusYear;
    }

    public void setCensusYear(Integer censusYear) {
        this.censusYear = censusYear;
    }

    public String getDenonym() {
        return denonym;
    }

    public void setDenonym(String denonym) {
        this.denonym = denonym;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationDTO)) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", population=" + getPopulation() +
            ", censusYear=" + getCensusYear() +
            ", denonym='" + getDenonym() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}
