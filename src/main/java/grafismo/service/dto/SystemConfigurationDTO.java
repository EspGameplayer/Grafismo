package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link grafismo.domain.SystemConfiguration} entity.
 */
public class SystemConfigurationDTO implements Serializable {

    private Long id;

    private String miscData;

    private SeasonDTO currentSeason;

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

    public SeasonDTO getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(SeasonDTO currentSeason) {
        this.currentSeason = currentSeason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemConfigurationDTO)) {
            return false;
        }

        SystemConfigurationDTO systemConfigurationDTO = (SystemConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, systemConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemConfigurationDTO{" +
            "id=" + getId() +
            ", miscData='" + getMiscData() + "'" +
            ", currentSeason=" + getCurrentSeason() +
            "}";
    }
}
