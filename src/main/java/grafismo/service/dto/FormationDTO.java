package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Formation} entity.
 */
public class FormationDTO implements Serializable {

    private Long id;

    @NotNull
    private String graphicsName;

    private String detailedName;

    private String distribution;

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

    public String getDetailedName() {
        return detailedName;
    }

    public void setDetailedName(String detailedName) {
        this.detailedName = detailedName;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormationDTO)) {
            return false;
        }

        FormationDTO formationDTO = (FormationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, formationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormationDTO{" +
            "id=" + getId() +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", detailedName='" + getDetailedName() + "'" +
            ", distribution='" + getDistribution() + "'" +
            "}";
    }
}
