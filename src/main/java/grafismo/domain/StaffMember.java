package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grafismo.domain.enumeration.StaffMemberRole;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StaffMember.
 */
@Entity
@Table(name = "staff_member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StaffMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_role")
    private StaffMemberRole defaultRole;

    @Column(name = "contract_until")
    private String contractUntil;

    @Column(name = "retirement_date")
    private String retirementDate;

    @Column(name = "misc_data")
    private String miscData;

    @JsonIgnoreProperties(value = { "nationality", "birthplace" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StaffMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StaffMemberRole getDefaultRole() {
        return this.defaultRole;
    }

    public StaffMember defaultRole(StaffMemberRole defaultRole) {
        this.setDefaultRole(defaultRole);
        return this;
    }

    public void setDefaultRole(StaffMemberRole defaultRole) {
        this.defaultRole = defaultRole;
    }

    public String getContractUntil() {
        return this.contractUntil;
    }

    public StaffMember contractUntil(String contractUntil) {
        this.setContractUntil(contractUntil);
        return this;
    }

    public void setContractUntil(String contractUntil) {
        this.contractUntil = contractUntil;
    }

    public String getRetirementDate() {
        return this.retirementDate;
    }

    public StaffMember retirementDate(String retirementDate) {
        this.setRetirementDate(retirementDate);
        return this;
    }

    public void setRetirementDate(String retirementDate) {
        this.retirementDate = retirementDate;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public StaffMember miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public StaffMember person(Person person) {
        this.setPerson(person);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaffMember)) {
            return false;
        }
        return id != null && id.equals(((StaffMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaffMember{" +
            "id=" + getId() +
            ", defaultRole='" + getDefaultRole() + "'" +
            ", contractUntil='" + getContractUntil() + "'" +
            ", retirementDate='" + getRetirementDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
