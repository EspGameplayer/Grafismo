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

    private BroadcastPersonnelMemberRole role;

    private PersonDTO person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", role='" + getRole() + "'" +
            ", person=" + getPerson() +
            "}";
    }
}
