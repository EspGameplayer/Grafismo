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
 * A MatchPlayer.
 */
@Entity
@Table(name = "match_player")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MatchPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shirt_number")
    private Integer shirtNumber;

    @Column(name = "is_youth")
    private Integer isYouth;

    @Column(name = "is_warned")
    private Integer isWarned;

    @Column(name = "misc_data")
    private String miscData;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "team", "player" }, allowSetters = true)
    private TeamPlayer teamPlayer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parents", "children", "players" }, allowSetters = true)
    private Position position;

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
    @OneToOne(mappedBy = "motm")
    private Match motmMatch;

    @JsonIgnoreProperties(
        value = { "captain", "dt", "dt2", "teamDelegate", "formation", "matchPlayers", "homeMatch", "awayMatch" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "captain")
    private Lineup captainLineup;

    @ManyToMany(mappedBy = "matchPlayers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "captain", "dt", "dt2", "teamDelegate", "formation", "matchPlayers", "homeMatch", "awayMatch" },
        allowSetters = true
    )
    private Set<Lineup> lineups = new HashSet<>();

    @ManyToMany(mappedBy = "matchPlayers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "match", "matchPlayers" }, allowSetters = true)
    private Set<MatchAction> actions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MatchPlayer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShirtNumber() {
        return this.shirtNumber;
    }

    public MatchPlayer shirtNumber(Integer shirtNumber) {
        this.setShirtNumber(shirtNumber);
        return this;
    }

    public void setShirtNumber(Integer shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public Integer getIsYouth() {
        return this.isYouth;
    }

    public MatchPlayer isYouth(Integer isYouth) {
        this.setIsYouth(isYouth);
        return this;
    }

    public void setIsYouth(Integer isYouth) {
        this.isYouth = isYouth;
    }

    public Integer getIsWarned() {
        return this.isWarned;
    }

    public MatchPlayer isWarned(Integer isWarned) {
        this.setIsWarned(isWarned);
        return this;
    }

    public void setIsWarned(Integer isWarned) {
        this.isWarned = isWarned;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public MatchPlayer miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public TeamPlayer getTeamPlayer() {
        return this.teamPlayer;
    }

    public void setTeamPlayer(TeamPlayer teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    public MatchPlayer teamPlayer(TeamPlayer teamPlayer) {
        this.setTeamPlayer(teamPlayer);
        return this;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public MatchPlayer position(Position position) {
        this.setPosition(position);
        return this;
    }

    public Match getMotmMatch() {
        return this.motmMatch;
    }

    public void setMotmMatch(Match match) {
        if (this.motmMatch != null) {
            this.motmMatch.setMotm(null);
        }
        if (match != null) {
            match.setMotm(this);
        }
        this.motmMatch = match;
    }

    public MatchPlayer motmMatch(Match match) {
        this.setMotmMatch(match);
        return this;
    }

    public Lineup getCaptainLineup() {
        return this.captainLineup;
    }

    public void setCaptainLineup(Lineup lineup) {
        if (this.captainLineup != null) {
            this.captainLineup.setCaptain(null);
        }
        if (lineup != null) {
            lineup.setCaptain(this);
        }
        this.captainLineup = lineup;
    }

    public MatchPlayer captainLineup(Lineup lineup) {
        this.setCaptainLineup(lineup);
        return this;
    }

    public Set<Lineup> getLineups() {
        return this.lineups;
    }

    public void setLineups(Set<Lineup> lineups) {
        if (this.lineups != null) {
            this.lineups.forEach(i -> i.removeMatchPlayer(this));
        }
        if (lineups != null) {
            lineups.forEach(i -> i.addMatchPlayer(this));
        }
        this.lineups = lineups;
    }

    public MatchPlayer lineups(Set<Lineup> lineups) {
        this.setLineups(lineups);
        return this;
    }

    public MatchPlayer addLineup(Lineup lineup) {
        this.lineups.add(lineup);
        lineup.getMatchPlayers().add(this);
        return this;
    }

    public MatchPlayer removeLineup(Lineup lineup) {
        this.lineups.remove(lineup);
        lineup.getMatchPlayers().remove(this);
        return this;
    }

    public Set<MatchAction> getActions() {
        return this.actions;
    }

    public void setActions(Set<MatchAction> matchActions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.removeMatchPlayer(this));
        }
        if (matchActions != null) {
            matchActions.forEach(i -> i.addMatchPlayer(this));
        }
        this.actions = matchActions;
    }

    public MatchPlayer actions(Set<MatchAction> matchActions) {
        this.setActions(matchActions);
        return this;
    }

    public MatchPlayer addAction(MatchAction matchAction) {
        this.actions.add(matchAction);
        matchAction.getMatchPlayers().add(this);
        return this;
    }

    public MatchPlayer removeAction(MatchAction matchAction) {
        this.actions.remove(matchAction);
        matchAction.getMatchPlayers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchPlayer)) {
            return false;
        }
        return id != null && id.equals(((MatchPlayer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchPlayer{" +
            "id=" + getId() +
            ", shirtNumber=" + getShirtNumber() +
            ", isYouth=" + getIsYouth() +
            ", isWarned=" + getIsWarned() +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
