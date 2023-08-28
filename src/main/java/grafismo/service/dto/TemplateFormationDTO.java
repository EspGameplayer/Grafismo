package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.TemplateFormation} entity.
 */
public class TemplateFormationDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private FormationDTO formation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FormationDTO getFormation() {
        return formation;
    }

    public void setFormation(FormationDTO formation) {
        this.formation = formation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateFormationDTO)) {
            return false;
        }

        TemplateFormationDTO templateFormationDTO = (TemplateFormationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateFormationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateFormationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", formation=" + getFormation() +
            "}";
    }
}
