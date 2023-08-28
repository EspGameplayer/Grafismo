package grafismo.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SystemConfiguration.
 */
@Entity
@Table(name = "system_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "misc_data")
    private String miscData;

    @OneToOne
    @JoinColumn(unique = true)
    private Season currentSeason;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SystemConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public SystemConfiguration miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Season getCurrentSeason() {
        return this.currentSeason;
    }

    public void setCurrentSeason(Season season) {
        this.currentSeason = season;
    }

    public SystemConfiguration currentSeason(Season season) {
        this.setCurrentSeason(season);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemConfiguration)) {
            return false;
        }
        return id != null && id.equals(((SystemConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemConfiguration{" +
            "id=" + getId() +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
