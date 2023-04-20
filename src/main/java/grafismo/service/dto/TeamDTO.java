package grafismo.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Team} entity.
 */
public class TeamDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String abb;

    private String graphicsName;

    private String shortName;

    private String nicknames;

    private String establishmentDate;

    @Lob
    private byte[] badge;

    private String badgeContentType;

    @Lob
    private byte[] monocBadge;

    private String monocBadgeContentType;
    private String details;

    private String miscData;

    private TeamDTO parent;

    private FormationDTO preferredFormation;

    private LocationDTO location;

    private Set<VenueDTO> venues = new HashSet<>();

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

    public String getAbb() {
        return abb;
    }

    public void setAbb(String abb) {
        this.abb = abb;
    }

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNicknames() {
        return nicknames;
    }

    public void setNicknames(String nicknames) {
        this.nicknames = nicknames;
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public byte[] getBadge() {
        return badge;
    }

    public void setBadge(byte[] badge) {
        this.badge = badge;
    }

    public String getBadgeContentType() {
        return badgeContentType;
    }

    public void setBadgeContentType(String badgeContentType) {
        this.badgeContentType = badgeContentType;
    }

    public byte[] getMonocBadge() {
        return monocBadge;
    }

    public void setMonocBadge(byte[] monocBadge) {
        this.monocBadge = monocBadge;
    }

    public String getMonocBadgeContentType() {
        return monocBadgeContentType;
    }

    public void setMonocBadgeContentType(String monocBadgeContentType) {
        this.monocBadgeContentType = monocBadgeContentType;
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

    public TeamDTO getParent() {
        return parent;
    }

    public void setParent(TeamDTO parent) {
        this.parent = parent;
    }

    public FormationDTO getPreferredFormation() {
        return preferredFormation;
    }

    public void setPreferredFormation(FormationDTO preferredFormation) {
        this.preferredFormation = preferredFormation;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public Set<VenueDTO> getVenues() {
        return venues;
    }

    public void setVenues(Set<VenueDTO> venues) {
        this.venues = venues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamDTO)) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", abb='" + getAbb() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", nicknames='" + getNicknames() + "'" +
            ", establishmentDate='" + getEstablishmentDate() + "'" +
            ", badge='" + getBadge() + "'" +
            ", monocBadge='" + getMonocBadge() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", parent=" + getParent() +
            ", preferredFormation=" + getPreferredFormation() +
            ", location=" + getLocation() +
            ", venues=" + getVenues() +
            "}";
    }
}
