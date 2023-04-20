package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grafismo.domain.enumeration.StaffMemberRole;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TeamStaffMember.
 */
@Entity
@Table(name = "team_staff_member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeamStaffMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private StaffMemberRole role;

    @Column(name = "since_date")
    private LocalDate sinceDate;

    @Column(name = "until_date")
    private LocalDate untilDate;

    @Column(name = "misc_data")
    private String miscData;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent", "preferredFormation", "location", "venues", "children", "competitions" }, allowSetters = true)
    private Team team;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "person" }, allowSetters = true)
    private StaffMember staffMember;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamStaffMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StaffMemberRole getRole() {
        return this.role;
    }

    public TeamStaffMember role(StaffMemberRole role) {
        this.setRole(role);
        return this;
    }

    public void setRole(StaffMemberRole role) {
        this.role = role;
    }

    public LocalDate getSinceDate() {
        return this.sinceDate;
    }

    public TeamStaffMember sinceDate(LocalDate sinceDate) {
        this.setSinceDate(sinceDate);
        return this;
    }

    public void setSinceDate(LocalDate sinceDate) {
        this.sinceDate = sinceDate;
    }

    public LocalDate getUntilDate() {
        return this.untilDate;
    }

    public TeamStaffMember untilDate(LocalDate untilDate) {
        this.setUntilDate(untilDate);
        return this;
    }

    public void setUntilDate(LocalDate untilDate) {
        this.untilDate = untilDate;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public TeamStaffMember miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamStaffMember team(Team team) {
        this.setTeam(team);
        return this;
    }

    public StaffMember getStaffMember() {
        return this.staffMember;
    }

    public void setStaffMember(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public TeamStaffMember staffMember(StaffMember staffMember) {
        this.setStaffMember(staffMember);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamStaffMember)) {
            return false;
        }
        return id != null && id.equals(((TeamStaffMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamStaffMember{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", sinceDate='" + getSinceDate() + "'" +
            ", untilDate='" + getUntilDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
