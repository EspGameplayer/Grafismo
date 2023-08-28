package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Matchday.
 */
@Entity
@Table(name = "matchday")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Matchday implements Serializable {

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

    @Column(name = "moment")
    private Instant moment;

    @Column(name = "details")
    private String details;

    @Column(name = "misc_data")
    private String miscData;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent", "country", "teams", "referees", "children" }, allowSetters = true)
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Matchday id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Matchday name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraphicsName() {
        return this.graphicsName;
    }

    public Matchday graphicsName(String graphicsName) {
        this.setGraphicsName(graphicsName);
        return this;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public Instant getMoment() {
        return this.moment;
    }

    public Matchday moment(Instant moment) {
        this.setMoment(moment);
        return this;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public String getDetails() {
        return this.details;
    }

    public Matchday details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Matchday miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Matchday competition(Competition competition) {
        this.setCompetition(competition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matchday)) {
            return false;
        }
        return id != null && id.equals(((Matchday) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Matchday{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", moment='" + getMoment() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
