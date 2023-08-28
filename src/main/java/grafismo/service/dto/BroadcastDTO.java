package grafismo.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Broadcast} entity.
 */
public class BroadcastDTO implements Serializable {

    private Long id;

    private String miscData;

    private MatchDTO match;

    private SystemConfigurationDTO systemConfiguration;

    private Set<BroadcastPersonnelMemberDTO> broadcastPersonnelMembers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMiscData() {
        return miscData;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    public SystemConfigurationDTO getSystemConfiguration() {
        return systemConfiguration;
    }

    public void setSystemConfiguration(SystemConfigurationDTO systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    public Set<BroadcastPersonnelMemberDTO> getBroadcastPersonnelMembers() {
        return broadcastPersonnelMembers;
    }

    public void setBroadcastPersonnelMembers(Set<BroadcastPersonnelMemberDTO> broadcastPersonnelMembers) {
        this.broadcastPersonnelMembers = broadcastPersonnelMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BroadcastDTO)) {
            return false;
        }

        BroadcastDTO broadcastDTO = (BroadcastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, broadcastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BroadcastDTO{" +
            "id=" + getId() +
            ", miscData='" + getMiscData() + "'" +
            ", match=" + getMatch() +
            ", systemConfiguration=" + getSystemConfiguration() +
            ", broadcastPersonnelMembers=" + getBroadcastPersonnelMembers() +
            "}";
    }
}
