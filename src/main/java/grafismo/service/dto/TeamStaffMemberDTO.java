package grafismo.service.dto;

import grafismo.domain.enumeration.StaffMemberRole;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.TeamStaffMember} entity.
 */
public class TeamStaffMemberDTO implements Serializable {

    private Long id;

    private StaffMemberRole role;

    private LocalDate sinceDate;

    private LocalDate untilDate;

    private String miscData;

    private TeamDTO team;

    private StaffMemberDTO staffMember;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StaffMemberRole getRole() {
        return role;
    }

    public void setRole(StaffMemberRole role) {
        this.role = role;
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

    public StaffMemberDTO getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(StaffMemberDTO staffMember) {
        this.staffMember = staffMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamStaffMemberDTO)) {
            return false;
        }

        TeamStaffMemberDTO teamStaffMemberDTO = (TeamStaffMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamStaffMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamStaffMemberDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", sinceDate='" + getSinceDate() + "'" +
            ", untilDate='" + getUntilDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", team=" + getTeam() +
            ", staffMember=" + getStaffMember() +
            "}";
    }
}
