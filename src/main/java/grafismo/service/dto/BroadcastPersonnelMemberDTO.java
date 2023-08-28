package grafismo.service.dto;

import grafismo.domain.enumeration.BroadcastPersonnelMemberRole;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.BroadcastPersonnelMember} entity.
 */
public class BroadcastPersonnelMemberDTO implements Serializable {

    private Long id;

    @NotNull
    private String graphicsName;

    private String longGraphicsName;

    private BroadcastPersonnelMemberRole role;

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

    public BroadcastPersonnelMemberRole getRole() {
        return role;
    }

    public void setRole(BroadcastPersonnelMemberRole role) {
        this.role = role;
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
        if (!(o instanceof BroadcastPersonnelMemberDTO)) {
            return false;
        }

        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = (BroadcastPersonnelMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, broadcastPersonnelMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BroadcastPersonnelMemberDTO{" +
            "id=" + getId() +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", longGraphicsName='" + getLongGraphicsName() + "'" +
            ", role='" + getRole() + "'" +
            ", person=" + getPerson() +
            "}";
    }
}
