package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.MatchPlayer} entity.
 */
public class MatchPlayerDTO implements Serializable {

    private Long id;

    private Integer shirtNumber;

    private Integer isYouth;

    private Integer isWarned;

    private String miscData;

    private TeamPlayerDTO teamPlayer;

    private PositionDTO position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(Integer shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public Integer getIsYouth() {
        return isYouth;
    }

    public void setIsYouth(Integer isYouth) {
        this.isYouth = isYouth;
    }

    public Integer getIsWarned() {
        return isWarned;
    }

    public void setIsWarned(Integer isWarned) {
        this.isWarned = isWarned;
    }

    public String getMiscData() {
        return miscData;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public TeamPlayerDTO getTeamPlayer() {
        return teamPlayer;
    }

    public void setTeamPlayer(TeamPlayerDTO teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchPlayerDTO)) {
            return false;
        }

        MatchPlayerDTO matchPlayerDTO = (MatchPlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchPlayerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchPlayerDTO{" +
            "id=" + getId() +
            ", shirtNumber=" + getShirtNumber() +
            ", isYouth=" + getIsYouth() +
            ", isWarned=" + getIsWarned() +
            ", miscData='" + getMiscData() + "'" +
            ", teamPlayer=" + getTeamPlayer() +
            ", position=" + getPosition() +
            "}";
    }
}
