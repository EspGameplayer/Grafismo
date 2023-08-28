package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Referee} entity.
 */
public class RefereeDTO implements Serializable {

    private Long id;

    @NotNull
    private String graphicsName;

    private String longGraphicsName;

    private String retirementDate;

    private String miscData;

    private PersonDTO person;

    private AssociationDTO association;

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

    public AssociationDTO getAssociation() {
        return association;
    }

    public void setAssociation(AssociationDTO association) {
        this.association = association;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefereeDTO)) {
            return false;
        }

        RefereeDTO refereeDTO = (RefereeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, refereeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefereeDTO{" +
            "id=" + getId() +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", longGraphicsName='" + getLongGraphicsName() + "'" +
            ", retirementDate='" + getRetirementDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", person=" + getPerson() +
            ", association=" + getAssociation() +
            "}";
    }
}
