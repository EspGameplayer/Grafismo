package grafismo.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "graphics_name", nullable = false)
    private String graphicsName;

    @Column(name = "detailed_name")
    private String detailedName;

    @Column(name = "distribution")
    private String distribution;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGraphicsName() {
        return this.graphicsName;
    }

    public Formation graphicsName(String graphicsName) {
        this.setGraphicsName(graphicsName);
        return this;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getDetailedName() {
        return this.detailedName;
    }

    public Formation detailedName(String detailedName) {
        this.setDetailedName(detailedName);
        return this;
    }

    public void setDetailedName(String detailedName) {
        this.detailedName = detailedName;
    }

    public String getDistribution() {
        return this.distribution;
    }

    public Formation distribution(String distribution) {
        this.setDistribution(distribution);
        return this;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", detailedName='" + getDetailedName() + "'" +
            ", distribution='" + getDistribution() + "'" +
            "}";
    }
}
