package grafismo.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.MatchAction} entity.
 */
public class MatchActionDTO implements Serializable {

    private Long id;

    private String timestamp;

    private String details;

    private String miscData;

    private MatchDTO match;

    private Set<MatchPlayerDTO> matchPlayers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    public Set<MatchPlayerDTO> getMatchPlayers() {
        return matchPlayers;
    }

    public void setMatchPlayers(Set<MatchPlayerDTO> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchActionDTO)) {
            return false;
        }

        MatchActionDTO matchActionDTO = (MatchActionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchActionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchActionDTO{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", match=" + getMatch() +
            ", matchPlayers=" + getMatchPlayers() +
            "}";
    }
}
