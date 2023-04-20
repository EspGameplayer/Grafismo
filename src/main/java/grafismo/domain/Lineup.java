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
 * A Lineup.
 */
@Entity
@Table(name = "lineup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lineup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "details")
    private String details;

    @Column(name = "misc_data")
    private String miscData;

    @JsonIgnoreProperties(value = { "teamPlayer", "position", "motmMatch", "captainLineup", "lineups", "actions" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private MatchPlayer captain;

    @ManyToOne
    @JsonIgnoreProperties(value = { "team", "staffMember" }, allowSetters = true)
    private TeamStaffMember dt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "team", "staffMember" }, allowSetters = true)
    private TeamStaffMember dt2;

    @ManyToOne
    @JsonIgnoreProperties(value = { "team", "staffMember" }, allowSetters = true)
    private TeamStaffMember teamDelegate;

    @ManyToOne
    private Formation formation;

    @ManyToMany
    @JoinTable(
        name = "rel_lineup__match_player",
        joinColumns = @JoinColumn(name = "lineup_id"),
        inverseJoinColumns = @JoinColumn(name = "match_player_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "teamPlayer", "position", "motmMatch", "captainLineup", "lineups", "actions" }, allowSetters = true)
    private Set<MatchPlayer> matchPlayers = new HashSet<>();

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
    @OneToOne(mappedBy = "homeLineup")
    private Match homeMatch;

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
    @OneToOne(mappedBy = "awayLineup")
    private Match awayMatch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lineup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return this.details;
    }

    public Lineup details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Lineup miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public MatchPlayer getCaptain() {
        return this.captain;
    }

    public void setCaptain(MatchPlayer matchPlayer) {
        this.captain = matchPlayer;
    }

    public Lineup captain(MatchPlayer matchPlayer) {
        this.setCaptain(matchPlayer);
        return this;
    }

    public TeamStaffMember getDt() {
        return this.dt;
    }

    public void setDt(TeamStaffMember teamStaffMember) {
        this.dt = teamStaffMember;
    }

    public Lineup dt(TeamStaffMember teamStaffMember) {
        this.setDt(teamStaffMember);
        return this;
    }

    public TeamStaffMember getDt2() {
        return this.dt2;
    }

    public void setDt2(TeamStaffMember teamStaffMember) {
        this.dt2 = teamStaffMember;
    }

    public Lineup dt2(TeamStaffMember teamStaffMember) {
        this.setDt2(teamStaffMember);
        return this;
    }

    public TeamStaffMember getTeamDelegate() {
        return this.teamDelegate;
    }

    public void setTeamDelegate(TeamStaffMember teamStaffMember) {
        this.teamDelegate = teamStaffMember;
    }

    public Lineup teamDelegate(TeamStaffMember teamStaffMember) {
        this.setTeamDelegate(teamStaffMember);
        return this;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Lineup formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    public Set<MatchPlayer> getMatchPlayers() {
        return this.matchPlayers;
    }

    public void setMatchPlayers(Set<MatchPlayer> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }

    public Lineup matchPlayers(Set<MatchPlayer> matchPlayers) {
        this.setMatchPlayers(matchPlayers);
        return this;
    }

    public Lineup addMatchPlayer(MatchPlayer matchPlayer) {
        this.matchPlayers.add(matchPlayer);
        matchPlayer.getLineups().add(this);
        return this;
    }

    public Lineup removeMatchPlayer(MatchPlayer matchPlayer) {
        this.matchPlayers.remove(matchPlayer);
        matchPlayer.getLineups().remove(this);
        return this;
    }

    public Match getHomeMatch() {
        return this.homeMatch;
    }

    public void setHomeMatch(Match match) {
        if (this.homeMatch != null) {
            this.homeMatch.setHomeLineup(null);
        }
        if (match != null) {
            match.setHomeLineup(this);
        }
        this.homeMatch = match;
    }

    public Lineup homeMatch(Match match) {
        this.setHomeMatch(match);
        return this;
    }

    public Match getAwayMatch() {
        return this.awayMatch;
    }

    public void setAwayMatch(Match match) {
        if (this.awayMatch != null) {
            this.awayMatch.setAwayLineup(null);
        }
        if (match != null) {
            match.setAwayLineup(this);
        }
        this.awayMatch = match;
    }

    public Lineup awayMatch(Match match) {
        this.setAwayMatch(match);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lineup)) {
            return false;
        }
        return id != null && id.equals(((Lineup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lineup{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
