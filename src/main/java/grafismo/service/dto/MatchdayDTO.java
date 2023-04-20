package grafismo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Matchday} entity.
 */
public class MatchdayDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String graphicsName;

    private Instant moment;

    private String details;

    private String miscData;

    private CompetitionDTO competition;

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

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
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

    public CompetitionDTO getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionDTO competition) {
        this.competition = competition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchdayDTO)) {
            return false;
        }

        MatchdayDTO matchdayDTO = (MatchdayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchdayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchdayDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", moment='" + getMoment() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", competition=" + getCompetition() +
            "}";
    }
}
