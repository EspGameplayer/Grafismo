package grafismo.service.dto;

import grafismo.domain.enumeration.StaffMemberRole;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.StaffMember} entity.
 */
public class StaffMemberDTO implements Serializable {

    private Long id;

    @NotNull
    private String graphicsName;

    private String longGraphicsName;

    private StaffMemberRole defaultRole;

    private String contractUntil;

    private String retirementDate;

    private String miscData;

    private PersonDTO person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getLongGraphicsName() {
        return longGraphicsName;
    }

    public void setLongGraphicsName(String longGraphicsName) {
        this.longGraphicsName = longGraphicsName;
    }

    public StaffMemberRole getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(StaffMemberRole defaultRole) {
        this.defaultRole = defaultRole;
    }

    public String getContractUntil() {
        return contractUntil;
    }

    public void setContractUntil(String contractUntil) {
        this.contractUntil = contractUntil;
    }

    public String getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(String retirementDate) {
        this.retirementDate = retirementDate;
    }

    public String getMiscData() {
        return miscData;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaffMemberDTO)) {
            return false;
        }

        StaffMemberDTO staffMemberDTO = (StaffMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, staffMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StaffMemberDTO{" +
            "id=" + getId() +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", longGraphicsName='" + getLongGraphicsName() + "'" +
            ", defaultRole='" + getDefaultRole() + "'" +
            ", contractUntil='" + getContractUntil() + "'" +
            ", retirementDate='" + getRetirementDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", person=" + getPerson() +
            "}";
    }
}
