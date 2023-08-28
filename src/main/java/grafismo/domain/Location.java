package grafismo.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Location implements Serializable {

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

    @Column(name = "population")
    private Integer population;

    @Column(name = "census_year")
    private Integer censusYear;

    @Column(name = "denonym")
    private String denonym;

    @ManyToOne
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Location name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraphicsName() {
        return this.graphicsName;
    }

    public Location graphicsName(String graphicsName) {
        this.setGraphicsName(graphicsName);
        return this;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public Integer getPopulation() {
        return this.population;
    }

    public Location population(Integer population) {
        this.setPopulation(population);
        return this;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getCensusYear() {
        return this.censusYear;
    }

    public Location censusYear(Integer censusYear) {
        this.setCensusYear(censusYear);
        return this;
    }

    public void setCensusYear(Integer censusYear) {
        this.censusYear = censusYear;
    }

    public String getDenonym() {
        return this.denonym;
    }

    public Location denonym(String denonym) {
        this.setDenonym(denonym);
        return this;
    }

    public void setDenonym(String denonym) {
        this.denonym = denonym;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Location country(Country country) {
        this.setCountry(country);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", population=" + getPopulation() +
            ", censusYear=" + getCensusYear() +
            ", denonym='" + getDenonym() + "'" +
            "}";
    }
}
