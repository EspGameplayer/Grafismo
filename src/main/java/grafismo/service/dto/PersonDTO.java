package grafismo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Person} entity.
 */
public class PersonDTO implements Serializable {

    private Long id;

    private String name;

    private String middleName;

    private String surname1;

    private String surname2;

    private String nicknames;

    @NotNull
    private String graphicsName;

    private String longGraphicsName;

    private String callnames;

    private String birthDate;

    private String deathDate;

    @Lob
    private byte[] mediumShotPhoto;

    private String mediumShotPhotoContentType;

    @Lob
    private byte[] fullShotPhoto;

    private String fullShotPhotoContentType;
    private String socialMedia;

    private String details;

    private String miscData;

    private CountryDTO nationality;

    private LocationDTO birthplace;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getNicknames() {
        return nicknames;
    }

    public void setNicknames(String nicknames) {
        this.nicknames = nicknames;
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

    public String getCallnames() {
        return callnames;
    }

    public void setCallnames(String callnames) {
        this.callnames = callnames;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public byte[] getMediumShotPhoto() {
        return mediumShotPhoto;
    }

    public void setMediumShotPhoto(byte[] mediumShotPhoto) {
        this.mediumShotPhoto = mediumShotPhoto;
    }

    public String getMediumShotPhotoContentType() {
        return mediumShotPhotoContentType;
    }

    public void setMediumShotPhotoContentType(String mediumShotPhotoContentType) {
        this.mediumShotPhotoContentType = mediumShotPhotoContentType;
    }

    public byte[] getFullShotPhoto() {
        return fullShotPhoto;
    }

    public void setFullShotPhoto(byte[] fullShotPhoto) {
        this.fullShotPhoto = fullShotPhoto;
    }

    public String getFullShotPhotoContentType() {
        return fullShotPhotoContentType;
    }

    public void setFullShotPhotoContentType(String fullShotPhotoContentType) {
        this.fullShotPhotoContentType = fullShotPhotoContentType;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
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

    public CountryDTO getNationality() {
        return nationality;
    }

    public void setNationality(CountryDTO nationality) {
        this.nationality = nationality;
    }

    public LocationDTO getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(LocationDTO birthplace) {
        this.birthplace = birthplace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDTO)) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", surname1='" + getSurname1() + "'" +
            ", surname2='" + getSurname2() + "'" +
            ", nicknames='" + getNicknames() + "'" +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", longGraphicsName='" + getLongGraphicsName() + "'" +
            ", callnames='" + getCallnames() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", deathDate='" + getDeathDate() + "'" +
            ", mediumShotPhoto='" + getMediumShotPhoto() + "'" +
            ", fullShotPhoto='" + getFullShotPhoto() + "'" +
            ", socialMedia='" + getSocialMedia() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", nationality=" + getNationality() +
            ", birthplace=" + getBirthplace() +
            "}";
    }
}
