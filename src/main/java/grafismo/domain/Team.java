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
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "abb", nullable = false)
    private String abb;

    @Column(name = "graphics_name")
    private String graphicsName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "nicknames")
    private String nicknames;

    @Column(name = "establishment_date")
    private String establishmentDate;

    @Lob
    @Column(name = "badge")
    private byte[] badge;

    @Column(name = "badge_content_type")
    private String badgeContentType;

    @Lob
    @Column(name = "monoc_badge")
    private byte[] monocBadge;

    @Column(name = "monoc_badge_content_type")
    private String monocBadgeContentType;

    @Column(name = "details")
    private String details;

    @Column(name = "misc_data")
    private String miscData;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent", "preferredFormation", "location", "venues", "children", "competitions" }, allowSetters = true)
    private Team parent;

    @ManyToOne
    private Formation preferredFormation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Location location;

    @ManyToMany
    @JoinTable(name = "rel_team__venue", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "venue_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "location", "teams" }, allowSetters = true)
    private Set<Venue> venues = new HashSet<>();

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parent", "preferredFormation", "location", "venues", "children", "competitions" }, allowSetters = true)
    private Set<Team> children = new HashSet<>();

    @ManyToMany(mappedBy = "teams")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parent", "country", "teams", "referees", "children" }, allowSetters = true)
    private Set<Competition> competitions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Team name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbb() {
        return this.abb;
    }

    public Team abb(String abb) {
        this.setAbb(abb);
        return this;
    }

    public void setAbb(String abb) {
        this.abb = abb;
    }

    public String getGraphicsName() {
        return this.graphicsName;
    }

    public Team graphicsName(String graphicsName) {
        this.setGraphicsName(graphicsName);
        return this;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Team shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNicknames() {
        return this.nicknames;
    }

    public Team nicknames(String nicknames) {
        this.setNicknames(nicknames);
        return this;
    }

    public void setNicknames(String nicknames) {
        this.nicknames = nicknames;
    }

    public String getEstablishmentDate() {
        return this.establishmentDate;
    }

    public Team establishmentDate(String establishmentDate) {
        this.setEstablishmentDate(establishmentDate);
        return this;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public byte[] getBadge() {
        return this.badge;
    }

    public Team badge(byte[] badge) {
        this.setBadge(badge);
        return this;
    }

    public void setBadge(byte[] badge) {
        this.badge = badge;
    }

    public String getBadgeContentType() {
        return this.badgeContentType;
    }

    public Team badgeContentType(String badgeContentType) {
        this.badgeContentType = badgeContentType;
        return this;
    }

    public void setBadgeContentType(String badgeContentType) {
        this.badgeContentType = badgeContentType;
    }

    public byte[] getMonocBadge() {
        return this.monocBadge;
    }

    public Team monocBadge(byte[] monocBadge) {
        this.setMonocBadge(monocBadge);
        return this;
    }

    public void setMonocBadge(byte[] monocBadge) {
        this.monocBadge = monocBadge;
    }

    public String getMonocBadgeContentType() {
        return this.monocBadgeContentType;
    }

    public Team monocBadgeContentType(String monocBadgeContentType) {
        this.monocBadgeContentType = monocBadgeContentType;
        return this;
    }

    public void setMonocBadgeContentType(String monocBadgeContentType) {
        this.monocBadgeContentType = monocBadgeContentType;
    }

    public String getDetails() {
        return this.details;
    }

    public Team details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Team miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Team getParent() {
        return this.parent;
    }

    public void setParent(Team team) {
        this.parent = team;
    }

    public Team parent(Team team) {
        this.setParent(team);
        return this;
    }

    public Formation getPreferredFormation() {
        return this.preferredFormation;
    }

    public void setPreferredFormation(Formation formation) {
        this.preferredFormation = formation;
    }

    public Team preferredFormation(Formation formation) {
        this.setPreferredFormation(formation);
        return this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Team location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Set<Venue> getVenues() {
        return this.venues;
    }

    public void setVenues(Set<Venue> venues) {
        this.venues = venues;
    }

    public Team venues(Set<Venue> venues) {
        this.setVenues(venues);
        return this;
    }

    public Team addVenue(Venue venue) {
        this.venues.add(venue);
        venue.getTeams().add(this);
        return this;
    }

    public Team removeVenue(Venue venue) {
        this.venues.remove(venue);
        venue.getTeams().remove(this);
        return this;
    }

    public Set<Team> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Team> teams) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setParent(this));
        }
        this.children = teams;
    }

    public Team children(Set<Team> teams) {
        this.setChildren(teams);
        return this;
    }

    public Team addChild(Team team) {
        this.children.add(team);
        team.setParent(this);
        return this;
    }

    public Team removeChild(Team team) {
        this.children.remove(team);
        team.setParent(null);
        return this;
    }

    public Set<Competition> getCompetitions() {
        return this.competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        if (this.competitions != null) {
            this.competitions.forEach(i -> i.removeTeam(this));
        }
        if (competitions != null) {
            competitions.forEach(i -> i.addTeam(this));
        }
        this.competitions = competitions;
    }

    public Team competitions(Set<Competition> competitions) {
        this.setCompetitions(competitions);
        return this;
    }

    public Team addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.getTeams().add(this);
        return this;
    }

    public Team removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.getTeams().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", abb='" + getAbb() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", nicknames='" + getNicknames() + "'" +
            ", establishmentDate='" + getEstablishmentDate() + "'" +
            ", badge='" + getBadge() + "'" +
            ", badgeContentType='" + getBadgeContentType() + "'" +
            ", monocBadge='" + getMonocBadge() + "'" +
            ", monocBadgeContentType='" + getMonocBadgeContentType() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
