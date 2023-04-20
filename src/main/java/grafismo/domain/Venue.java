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
 * A Venue.
 */
@Entity
@Table(name = "venue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "graphics_name")
    private String graphicsName;

    @Column(name = "long_graphics_name")
    private String longGraphicsName;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "opening_date")
    private String openingDate;

    @Column(name = "field_size")
    private String fieldSize;

    @Column(name = "is_artificial_grass")
    private Integer isArtificialGrass;

    @Column(name = "details")
    private String details;

    @Column(name = "misc_data")
    private String miscData;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Location location;

    @ManyToMany(mappedBy = "venues")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parent", "preferredFormation", "location", "venues", "children", "competitions" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Venue name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraphicsName() {
        return this.graphicsName;
    }

    public Venue graphicsName(String graphicsName) {
        this.setGraphicsName(graphicsName);
        return this;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getLongGraphicsName() {
        return this.longGraphicsName;
    }

    public Venue longGraphicsName(String longGraphicsName) {
        this.setLongGraphicsName(longGraphicsName);
        return this;
    }

    public void setLongGraphicsName(String longGraphicsName) {
        this.longGraphicsName = longGraphicsName;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Venue capacity(Integer capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getOpeningDate() {
        return this.openingDate;
    }

    public Venue openingDate(String openingDate) {
        this.setOpeningDate(openingDate);
        return this;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getFieldSize() {
        return this.fieldSize;
    }

    public Venue fieldSize(String fieldSize) {
        this.setFieldSize(fieldSize);
        return this;
    }

    public void setFieldSize(String fieldSize) {
        this.fieldSize = fieldSize;
    }

    public Integer getIsArtificialGrass() {
        return this.isArtificialGrass;
    }

    public Venue isArtificialGrass(Integer isArtificialGrass) {
        this.setIsArtificialGrass(isArtificialGrass);
        return this;
    }

    public void setIsArtificialGrass(Integer isArtificialGrass) {
        this.isArtificialGrass = isArtificialGrass;
    }

    public String getDetails() {
        return this.details;
    }

    public Venue details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Venue miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Venue location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.removeVenue(this));
        }
        if (teams != null) {
            teams.forEach(i -> i.addVenue(this));
        }
        this.teams = teams;
    }

    public Venue teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Venue addTeam(Team team) {
        this.teams.add(team);
        team.getVenues().add(this);
        return this;
    }

    public Venue removeTeam(Team team) {
        this.teams.remove(team);
        team.getVenues().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venue)) {
            return false;
        }
        return id != null && id.equals(((Venue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venue{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", longGraphicsName='" + getLongGraphicsName() + "'" +
            ", capacity=" + getCapacity() +
            ", openingDate='" + getOpeningDate() + "'" +
            ", fieldSize='" + getFieldSize() + "'" +
            ", isArtificialGrass=" + getIsArtificialGrass() +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
