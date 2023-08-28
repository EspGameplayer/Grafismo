package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Broadcast.
 */
@Entity
@Table(name = "broadcast")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Broadcast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "misc_data")
    private String miscData;

    @JsonIgnoreProperties(
        value = {
            "motm",
            "homeLineup",
            "awayLineup",
            "homeTeam",
            "awayTeam",
            "venue",
            "matchDelegate",
            "homeShirt",
            "awayShirt",
            "matchday",
            "referees",
            "deductions",
            "suspensions",
            "injuries",
        },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Match match;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "currentSeason" }, allowSetters = true)
    private SystemConfiguration systemConfiguration;

    @ManyToMany
    @JoinTable(
        name = "rel_broadcast__broadcast_personnel_member",
        joinColumns = @JoinColumn(name = "broadcast_id"),
        inverseJoinColumns = @JoinColumn(name = "broadcast_personnel_member_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "person", "broadcasts" }, allowSetters = true)
    private Set<BroadcastPersonnelMember> broadcastPersonnelMembers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Broadcast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Broadcast miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Match getMatch() {
        return this.match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Broadcast match(Match match) {
        this.setMatch(match);
        return this;
    }

    public SystemConfiguration getSystemConfiguration() {
        return this.systemConfiguration;
    }

    public void setSystemConfiguration(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    public Broadcast systemConfiguration(SystemConfiguration systemConfiguration) {
        this.setSystemConfiguration(systemConfiguration);
        return this;
    }

    public Set<BroadcastPersonnelMember> getBroadcastPersonnelMembers() {
        return this.broadcastPersonnelMembers;
    }

    public void setBroadcastPersonnelMembers(Set<BroadcastPersonnelMember> broadcastPersonnelMembers) {
        this.broadcastPersonnelMembers = broadcastPersonnelMembers;
    }

    public Broadcast broadcastPersonnelMembers(Set<BroadcastPersonnelMember> broadcastPersonnelMembers) {
        this.setBroadcastPersonnelMembers(broadcastPersonnelMembers);
        return this;
    }

    public Broadcast addBroadcastPersonnelMember(BroadcastPersonnelMember broadcastPersonnelMember) {
        this.broadcastPersonnelMembers.add(broadcastPersonnelMember);
        broadcastPersonnelMember.getBroadcasts().add(this);
        return this;
    }

    public Broadcast removeBroadcastPersonnelMember(BroadcastPersonnelMember broadcastPersonnelMember) {
        this.broadcastPersonnelMembers.remove(broadcastPersonnelMember);
        broadcastPersonnelMember.getBroadcasts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Broadcast)) {
            return false;
        }
        return id != null && id.equals(((Broadcast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Broadcast{" +
            "id=" + getId() +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
