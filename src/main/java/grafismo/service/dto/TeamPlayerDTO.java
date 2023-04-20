package grafismo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.TeamPlayer} entity.
 */
public class TeamPlayerDTO implements Serializable {

    private Long id;

    private Integer preferredShirtNumber;

    private Integer isYouth;

    private LocalDate sinceDate;

    private LocalDate untilDate;

    private String miscData;

    private TeamDTO team;

    private PlayerDTO player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPreferredShirtNumber() {
        return preferredShirtNumber;
    }

    public void setPreferredShirtNumber(Integer preferredShirtNumber) {
        this.preferredShirtNumber = preferredShirtNumber;
    }

    public Integer getIsYouth() {
        return isYouth;
    }

    public void setIsYouth(Integer isYouth) {
        this.isYouth = isYouth;
    }

    public LocalDate getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(LocalDate sinceDate) {
        this.sinceDate = sinceDate;
    }

    public LocalDate getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(LocalDate untilDate) {
        this.untilDate = untilDate;
    }

    public String getMiscData() {
        return miscData;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamPlayerDTO)) {
            return false;
        }

        TeamPlayerDTO teamPlayerDTO = (TeamPlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamPlayerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamPlayerDTO{" +
            "id=" + getId() +
            ", preferredShirtNumber=" + getPreferredShirtNumber() +
            ", isYouth=" + getIsYouth() +
            ", sinceDate='" + getSinceDate() + "'" +
            ", untilDate='" + getUntilDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", team=" + getTeam() +
            ", player=" + getPlayer() +
            "}";
    }
}
