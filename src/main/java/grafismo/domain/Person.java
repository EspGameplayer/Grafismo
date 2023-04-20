package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "surname_1")
    private String surname1;

    @Column(name = "surname_2")
    private String surname2;

    @Column(name = "nicknames")
    private String nicknames;

    @NotNull
    @Column(name = "graphics_name", nullable = false)
    private String graphicsName;

    @Column(name = "long_graphics_name")
    private String longGraphicsName;

    @Column(name = "callnames")
    private String callnames;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "death_date")
    private String deathDate;

    @Lob
    @Column(name = "medium_shot_photo")
    private byte[] mediumShotPhoto;

    @Column(name = "medium_shot_photo_content_type")
    private String mediumShotPhotoContentType;

    @Lob
    @Column(name = "full_shot_photo")
    private byte[] fullShotPhoto;

    @Column(name = "full_shot_photo_content_type")
    private String fullShotPhotoContentType;

    @Column(name = "social_media")
    private String socialMedia;

    @Column(name = "details")
    private String details;

    @Column(name = "misc_data")
    private String miscData;

    @ManyToOne
    private Country nationality;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Location birthplace;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Person name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Person middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname1() {
        return this.surname1;
    }

    public Person surname1(String surname1) {
        this.setSurname1(surname1);
        return this;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return this.surname2;
    }

    public Person surname2(String surname2) {
        this.setSurname2(surname2);
        return this;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getNicknames() {
        return this.nicknames;
    }

    public Person nicknames(String nicknames) {
        this.setNicknames(nicknames);
        return this;
    }

    public void setNicknames(String nicknames) {
        this.nicknames = nicknames;
    }

    public String getGraphicsName() {
        return this.graphicsName;
    }

    public Person graphicsName(String graphicsName) {
        this.setGraphicsName(graphicsName);
        return this;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getLongGraphicsName() {
        return this.longGraphicsName;
    }

    public Person longGraphicsName(String longGraphicsName) {
        this.setLongGraphicsName(longGraphicsName);
        return this;
    }

    public void setLongGraphicsName(String longGraphicsName) {
        this.longGraphicsName = longGraphicsName;
    }

    public String getCallnames() {
        return this.callnames;
    }

    public Person callnames(String callnames) {
        this.setCallnames(callnames);
        return this;
    }

    public void setCallnames(String callnames) {
        this.callnames = callnames;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public Person birthDate(String birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return this.deathDate;
    }

    public Person deathDate(String deathDate) {
        this.setDeathDate(deathDate);
        return this;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public byte[] getMediumShotPhoto() {
        return this.mediumShotPhoto;
    }

    public Person mediumShotPhoto(byte[] mediumShotPhoto) {
        this.setMediumShotPhoto(mediumShotPhoto);
        return this;
    }

    public void setMediumShotPhoto(byte[] mediumShotPhoto) {
        this.mediumShotPhoto = mediumShotPhoto;
    }

    public String getMediumShotPhotoContentType() {
        return this.mediumShotPhotoContentType;
    }

    public Person mediumShotPhotoContentType(String mediumShotPhotoContentType) {
        this.mediumShotPhotoContentType = mediumShotPhotoContentType;
        return this;
    }

    public void setMediumShotPhotoContentType(String mediumShotPhotoContentType) {
        this.mediumShotPhotoContentType = mediumShotPhotoContentType;
    }

    public byte[] getFullShotPhoto() {
        return this.fullShotPhoto;
    }

    public Person fullShotPhoto(byte[] fullShotPhoto) {
        this.setFullShotPhoto(fullShotPhoto);
        return this;
    }

    public void setFullShotPhoto(byte[] fullShotPhoto) {
        this.fullShotPhoto = fullShotPhoto;
    }

    public String getFullShotPhotoContentType() {
        return this.fullShotPhotoContentType;
    }

    public Person fullShotPhotoContentType(String fullShotPhotoContentType) {
        this.fullShotPhotoContentType = fullShotPhotoContentType;
        return this;
    }

    public void setFullShotPhotoContentType(String fullShotPhotoContentType) {
        this.fullShotPhotoContentType = fullShotPhotoContentType;
    }

    public String getSocialMedia() {
        return this.socialMedia;
    }

    public Person socialMedia(String socialMedia) {
        this.setSocialMedia(socialMedia);
        return this;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getDetails() {
        return this.details;
    }

    public Person details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Person miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Country getNationality() {
        return this.nationality;
    }

    public void setNationality(Country country) {
        this.nationality = country;
    }

    public Person nationality(Country country) {
        this.setNationality(country);
        return this;
    }

    public Location getBirthplace() {
        return this.birthplace;
    }

    public void setBirthplace(Location location) {
        this.birthplace = location;
    }

    public Person birthplace(Location location) {
        this.setBirthplace(location);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
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
            ", mediumShotPhotoContentType='" + getMediumShotPhotoContentType() + "'" +
            ", fullShotPhoto='" + getFullShotPhoto() + "'" +
            ", fullShotPhotoContentType='" + getFullShotPhotoContentType() + "'" +
            ", socialMedia='" + getSocialMedia() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
