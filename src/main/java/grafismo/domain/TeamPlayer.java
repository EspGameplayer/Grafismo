package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TeamPlayer.
 */
@Entity
@Table(name = "team_player")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeamPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "preferred_shirt_number")
    private Integer preferredShirtNumber;

    @Column(name = "is_youth")
    private Integer isYouth;

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
    @JsonIgnoreProperties(value = { "person", "positions" }, allowSetters = true)
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamPlayer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPreferredShirtNumber() {
        return this.preferredShirtNumber;
    }

    public TeamPlayer preferredShirtNumber(Integer preferredShirtNumber) {
        this.setPreferredShirtNumber(preferredShirtNumber);
        return this;
    }

    public void setPreferredShirtNumber(Integer preferredShirtNumber) {
        this.preferredShirtNumber = preferredShirtNumber;
    }

    public Integer getIsYouth() {
        return this.isYouth;
    }

    public TeamPlayer isYouth(Integer isYouth) {
        this.setIsYouth(isYouth);
        return this;
    }

    public void setIsYouth(Integer isYouth) {
        this.isYouth = isYouth;
    }

    public LocalDate getSinceDate() {
        return this.sinceDate;
    }

    public TeamPlayer sinceDate(LocalDate sinceDate) {
        this.setSinceDate(sinceDate);
        return this;
    }

    public void setSinceDate(LocalDate sinceDate) {
        this.sinceDate = sinceDate;
    }

    public LocalDate getUntilDate() {
        return this.untilDate;
    }

    public TeamPlayer untilDate(LocalDate untilDate) {
        this.setUntilDate(untilDate);
        return this;
    }

    public void setUntilDate(LocalDate untilDate) {
        this.untilDate = untilDate;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public TeamPlayer miscData(String miscData) {
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

    public TeamPlayer team(Team team) {
        this.setTeam(team);
        return this;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TeamPlayer player(Player player) {
        this.setPlayer(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamPlayer)) {
            return false;
        }
        return id != null && id.equals(((TeamPlayer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamPlayer{" +
            "id=" + getId() +
            ", preferredShirtNumber=" + getPreferredShirtNumber() +
            ", isYouth=" + getIsYouth() +
            ", sinceDate='" + getSinceDate() + "'" +
            ", untilDate='" + getUntilDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
