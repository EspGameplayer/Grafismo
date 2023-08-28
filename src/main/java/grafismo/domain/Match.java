package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Match.
 */
@Entity
@Table(name = "jhi_match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "moment")
    private Instant moment;

    @Column(name = "attendance")
    private Integer attendance;

    @Column(name = "hashtag")
    private String hashtag;

    @Column(name = "details")
    private String details;

    @Column(name = "misc_data")
    private String miscData;

    @JsonIgnoreProperties(value = { "teamPlayer", "position", "motmMatch", "captainLineup", "lineups", "actions" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private MatchPlayer motm;

    @JsonIgnoreProperties(
        value = { "captain", "dt", "dt2", "teamDelegate", "formation", "matchPlayers", "homeMatch", "awayMatch" },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Lineup homeLineup;

    @JsonIgnoreProperties(
        value = { "captain", "dt", "dt2", "teamDelegate", "formation", "matchPlayers", "homeMatch", "awayMatch" },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Lineup awayLineup;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parent", "preferredFormation", "location", "venues", "children", "competitions" }, allowSetters = true)
    private Team homeTeam;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parent", "preferredFormation", "location", "venues", "children", "competitions" }, allowSetters = true)
    private Team awayTeam;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location", "teams" }, allowSetters = true)
    private Venue venue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "team", "staffMember" }, allowSetters = true)
    private TeamStaffMember matchDelegate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "team", "season" }, allowSetters = true)
    private Shirt homeShirt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "team", "season" }, allowSetters = true)
    private Shirt awayShirt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "competition" }, allowSetters = true)
    private Matchday matchday;

    @ManyToMany
    @JoinTable(
        name = "rel_jhi_match__referee",
        joinColumns = @JoinColumn(name = "jhi_match_id"),
        inverseJoinColumns = @JoinColumn(name = "referee_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "person", "association", "matches", "competitions" }, allowSetters = true)
    private Set<Referee> referees = new HashSet<>();

    @OneToMany(mappedBy = "match")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "team", "competition", "match" }, allowSetters = true)
    private Set<Deduction> deductions = new HashSet<>();

    @OneToMany(mappedBy = "match")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "person", "competition", "match" }, allowSetters = true)
    private Set<Suspension> suspensions = new HashSet<>();

    @OneToMany(mappedBy = "match")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "player", "match" }, allowSetters = true)
    private Set<Injury> injuries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Match id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return this.moment;
    }

    public Match moment(Instant moment) {
        this.setMoment(moment);
        return this;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Integer getAttendance() {
        return this.attendance;
    }

    public Match attendance(Integer attendance) {
        this.setAttendance(attendance);
        return this;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public String getHashtag() {
        return this.hashtag;
    }

    public Match hashtag(String hashtag) {
        this.setHashtag(hashtag);
        return this;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getDetails() {
        return this.details;
    }

    public Match details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Match miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public MatchPlayer getMotm() {
        return this.motm;
    }

    public void setMotm(MatchPlayer matchPlayer) {
        this.motm = matchPlayer;
    }

    public Match motm(MatchPlayer matchPlayer) {
        this.setMotm(matchPlayer);
        return this;
    }

    public Lineup getHomeLineup() {
        return this.homeLineup;
    }

    public void setHomeLineup(Lineup lineup) {
        this.homeLineup = lineup;
    }

    public Match homeLineup(Lineup lineup) {
        this.setHomeLineup(lineup);
        return this;
    }

    public Lineup getAwayLineup() {
        return this.awayLineup;
    }

    public void setAwayLineup(Lineup lineup) {
        this.awayLineup = lineup;
    }

    public Match awayLineup(Lineup lineup) {
        this.setAwayLineup(lineup);
        return this;
    }

    public Team getHomeTeam() {
        return this.homeTeam;
    }

    public void setHomeTeam(Team team) {
        this.homeTeam = team;
    }

    public Match homeTeam(Team team) {
        this.setHomeTeam(team);
        return this;
    }

    public Team getAwayTeam() {
        return this.awayTeam;
    }

    public void setAwayTeam(Team team) {
        this.awayTeam = team;
    }

    public Match awayTeam(Team team) {
        this.setAwayTeam(team);
        return this;
    }

    public Venue getVenue() {
        return this.venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Match venue(Venue venue) {
        this.setVenue(venue);
        return this;
    }

    public TeamStaffMember getMatchDelegate() {
        return this.matchDelegate;
    }

    public void setMatchDelegate(TeamStaffMember teamStaffMember) {
        this.matchDelegate = teamStaffMember;
    }

    public Match matchDelegate(TeamStaffMember teamStaffMember) {
        this.setMatchDelegate(teamStaffMember);
        return this;
    }

    public Shirt getHomeShirt() {
        return this.homeShirt;
    }

    public void setHomeShirt(Shirt shirt) {
        this.homeShirt = shirt;
    }

    public Match homeShirt(Shirt shirt) {
        this.setHomeShirt(shirt);
        return this;
    }

    public Shirt getAwayShirt() {
        return this.awayShirt;
    }

    public void setAwayShirt(Shirt shirt) {
        this.awayShirt = shirt;
    }

    public Match awayShirt(Shirt shirt) {
        this.setAwayShirt(shirt);
        return this;
    }

    public Matchday getMatchday() {
        return this.matchday;
    }

    public void setMatchday(Matchday matchday) {
        this.matchday = matchday;
    }

    public Match matchday(Matchday matchday) {
        this.setMatchday(matchday);
        return this;
    }

    public Set<Referee> getReferees() {
        return this.referees;
    }

    public void setReferees(Set<Referee> referees) {
        this.referees = referees;
    }

    public Match referees(Set<Referee> referees) {
        this.setReferees(referees);
        return this;
    }

    public Match addReferee(Referee referee) {
        this.referees.add(referee);
        referee.getMatches().add(this);
        return this;
    }

    public Match removeReferee(Referee referee) {
        this.referees.remove(referee);
        referee.getMatches().remove(this);
        return this;
    }

    public Set<Deduction> getDeductions() {
        return this.deductions;
    }

    public void setDeductions(Set<Deduction> deductions) {
        if (this.deductions != null) {
            this.deductions.forEach(i -> i.setMatch(null));
        }
        if (deductions != null) {
            deductions.forEach(i -> i.setMatch(this));
        }
        this.deductions = deductions;
    }

    public Match deductions(Set<Deduction> deductions) {
        this.setDeductions(deductions);
        return this;
    }

    public Match addDeduction(Deduction deduction) {
        this.deductions.add(deduction);
        deduction.setMatch(this);
        return this;
    }

    public Match removeDeduction(Deduction deduction) {
        this.deductions.remove(deduction);
        deduction.setMatch(null);
        return this;
    }

    public Set<Suspension> getSuspensions() {
        return this.suspensions;
    }

    public void setSuspensions(Set<Suspension> suspensions) {
        if (this.suspensions != null) {
            this.suspensions.forEach(i -> i.setMatch(null));
        }
        if (suspensions != null) {
            suspensions.forEach(i -> i.setMatch(this));
        }
        this.suspensions = suspensions;
    }

    public Match suspensions(Set<Suspension> suspensions) {
        this.setSuspensions(suspensions);
        return this;
    }

    public Match addSuspension(Suspension suspension) {
        this.suspensions.add(suspension);
        suspension.setMatch(this);
        return this;
    }

    public Match removeSuspension(Suspension suspension) {
        this.suspensions.remove(suspension);
        suspension.setMatch(null);
        return this;
    }

    public Set<Injury> getInjuries() {
        return this.injuries;
    }

    public void setInjuries(Set<Injury> injuries) {
        if (this.injuries != null) {
            this.injuries.forEach(i -> i.setMatch(null));
        }
        if (injuries != null) {
            injuries.forEach(i -> i.setMatch(this));
        }
        this.injuries = injuries;
    }

    public Match injuries(Set<Injury> injuries) {
        this.setInjuries(injuries);
        return this;
    }

    public Match addInjury(Injury injury) {
        this.injuries.add(injury);
        injury.setMatch(this);
        return this;
    }

    public Match removeInjury(Injury injury) {
        this.injuries.remove(injury);
        injury.setMatch(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return id != null && id.equals(((Match) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", moment='" + getMoment() + "'" +
            ", attendance=" + getAttendance() +
            ", hashtag='" + getHashtag() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
