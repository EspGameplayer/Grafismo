package grafismo.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Lineup} entity.
 */
public class LineupDTO implements Serializable {

    private Long id;

    private String details;

    private String miscData;

    private MatchPlayerDTO captain;

    private TeamStaffMemberDTO dt;

    private TeamStaffMemberDTO dt2;

    private TeamStaffMemberDTO teamDelegate;

    private FormationDTO formation;

    private Set<MatchPlayerDTO> matchPlayers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MatchPlayerDTO getCaptain() {
        return captain;
    }

    public void setCaptain(MatchPlayerDTO captain) {
        this.captain = captain;
    }

    public TeamStaffMemberDTO getDt() {
        return dt;
    }

    public void setDt(TeamStaffMemberDTO dt) {
        this.dt = dt;
    }

    public TeamStaffMemberDTO getDt2() {
        return dt2;
    }

    public void setDt2(TeamStaffMemberDTO dt2) {
        this.dt2 = dt2;
    }

    public TeamStaffMemberDTO getTeamDelegate() {
        return teamDelegate;
    }

    public void setTeamDelegate(TeamStaffMemberDTO teamDelegate) {
        this.teamDelegate = teamDelegate;
    }

    public FormationDTO getFormation() {
        return formation;
    }

    public void setFormation(FormationDTO formation) {
        this.formation = formation;
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
        if (!(o instanceof LineupDTO)) {
            return false;
        }

        LineupDTO lineupDTO = (LineupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lineupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LineupDTO{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", captain=" + getCaptain() +
            ", dt=" + getDt() +
            ", dt2=" + getDt2() +
            ", teamDelegate=" + getTeamDelegate() +
            ", formation=" + getFormation() +
            ", matchPlayers=" + getMatchPlayers() +
            "}";
    }
}
