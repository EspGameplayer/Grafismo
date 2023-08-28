package grafismo.service.dto;

import grafismo.domain.enumeration.CompetitionType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Competition} entity.
 */
public class CompetitionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String graphicsName;

    @NotNull
    private CompetitionType type;

    @Pattern(regexp = "^[0-9a-fA-F]{6}$|^$")
    private String colour;

    private String regulations;

    private String details;

    private String miscData;

    private CompetitionDTO parent;

    private CountryDTO country;

    private Set<TeamDTO> teams = new HashSet<>();

    private Set<RefereeDTO> referees = new HashSet<>();

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

    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getRegulations() {
        return regulations;
    }

    public void setRegulations(String regulations) {
        this.regulations = regulations;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return miscData;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public CompetitionDTO getParent() {
        return parent;
    }

    public void setParent(CompetitionDTO parent) {
        this.parent = parent;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public Set<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamDTO> teams) {
        this.teams = teams;
    }

    public Set<RefereeDTO> getReferees() {
        return referees;
    }

    public void setReferees(Set<RefereeDTO> referees) {
        this.referees = referees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetitionDTO)) {
            return false;
        }

        CompetitionDTO competitionDTO = (CompetitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, competitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", type='" + getType() + "'" +
            ", colour='" + getColour() + "'" +
            ", regulations='" + getRegulations() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", parent=" + getParent() +
            ", country=" + getCountry() +
            ", teams=" + getTeams() +
            ", referees=" + getReferees() +
            "}";
    }
}
