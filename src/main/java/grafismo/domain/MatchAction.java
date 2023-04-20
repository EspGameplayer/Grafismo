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
 * A MatchAction.
 */
@Entity
@Table(name = "match_action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MatchAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "details")
    private String details;

    @Column(name = "misc_data")
    private String miscData;

    @ManyToOne(optional = false)
    @NotNull
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
    private Match match;

    @ManyToMany
    @JoinTable(
        name = "rel_match_action__match_player",
        joinColumns = @JoinColumn(name = "match_action_id"),
        inverseJoinColumns = @JoinColumn(name = "match_player_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "teamPlayer", "position", "motmMatch", "captainLineup", "lineups", "actions" }, allowSetters = true)
    private Set<MatchPlayer> matchPlayers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MatchAction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public MatchAction timestamp(String timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return this.details;
    }

    public MatchAction details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public MatchAction miscData(String miscData) {
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

    public MatchAction match(Match match) {
        this.setMatch(match);
        return this;
    }

    public Set<MatchPlayer> getMatchPlayers() {
        return this.matchPlayers;
    }

    public void setMatchPlayers(Set<MatchPlayer> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }

    public MatchAction matchPlayers(Set<MatchPlayer> matchPlayers) {
        this.setMatchPlayers(matchPlayers);
        return this;
    }

    public MatchAction addMatchPlayer(MatchPlayer matchPlayer) {
        this.matchPlayers.add(matchPlayer);
        matchPlayer.getActions().add(this);
        return this;
    }

    public MatchAction removeMatchPlayer(MatchPlayer matchPlayer) {
        this.matchPlayers.remove(matchPlayer);
        matchPlayer.getActions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchAction)) {
            return false;
        }
        return id != null && id.equals(((MatchAction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchAction{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
