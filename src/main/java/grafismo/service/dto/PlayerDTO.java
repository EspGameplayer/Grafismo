package grafismo.service.dto;

import grafismo.domain.enumeration.Side;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Player} entity.
 */
public class PlayerDTO implements Serializable {

    private Long id;

    @NotNull
    private String graphicsName;

    private String longGraphicsName;

    private String shirtName;

    private Integer height;

    private Integer weight;

    private Side strongerFoot;

    private Side preferredSide;

    private String contractUntil;

    private String retirementDate;

    private String miscData;

    private PersonDTO person;

    private Set<PositionDTO> positions = new HashSet<>();

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

    public String getShirtName() {
        return shirtName;
    }

    public void setShirtName(String shirtName) {
        this.shirtName = shirtName;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Side getStrongerFoot() {
        return strongerFoot;
    }

    public void setStrongerFoot(Side strongerFoot) {
        this.strongerFoot = strongerFoot;
    }

    public Side getPreferredSide() {
        return preferredSide;
    }

    public void setPreferredSide(Side preferredSide) {
        this.preferredSide = preferredSide;
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

    public Set<PositionDTO> getPositions() {
        return positions;
    }

    public void setPositions(Set<PositionDTO> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerDTO)) {
            return false;
        }

        PlayerDTO playerDTO = (PlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, playerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerDTO{" +
            "id=" + getId() +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", longGraphicsName='" + getLongGraphicsName() + "'" +
            ", shirtName='" + getShirtName() + "'" +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", strongerFoot='" + getStrongerFoot() + "'" +
            ", preferredSide='" + getPreferredSide() + "'" +
            ", contractUntil='" + getContractUntil() + "'" +
            ", retirementDate='" + getRetirementDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", person=" + getPerson() +
            ", positions=" + getPositions() +
            "}";
    }
}
