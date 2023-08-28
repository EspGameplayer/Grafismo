package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Sponsor} entity.
 */
public class SponsorDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String graphicsName;

    @Lob
    private byte[] logo;

    private String logoContentType;

    @Lob
    private byte[] monocLogo;

    private String monocLogoContentType;
    private String details;

    private String miscData;

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

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public byte[] getMonocLogo() {
        return monocLogo;
    }

    public void setMonocLogo(byte[] monocLogo) {
        this.monocLogo = monocLogo;
    }

    public String getMonocLogoContentType() {
        return monocLogoContentType;
    }

    public void setMonocLogoContentType(String monocLogoContentType) {
        this.monocLogoContentType = monocLogoContentType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return miscData;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SponsorDTO)) {
            return false;
        }

        SponsorDTO sponsorDTO = (SponsorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sponsorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SponsorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", monocLogo='" + getMonocLogo() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
