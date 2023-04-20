package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grafismo.domain.enumeration.Side;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shirt_name")
    private String shirtName;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "stronger_foot")
    private Side strongerFoot;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_side")
    private Side preferredSide;

    @Column(name = "contract_until")
    private String contractUntil;

    @Column(name = "retirement_date")
    private String retirementDate;

    @Column(name = "misc_data")
    private String miscData;

    @JsonIgnoreProperties(value = { "nationality", "birthplace" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Person person;

    @ManyToMany
    @JoinTable(
        name = "rel_player__position",
        joinColumns = @JoinColumn(name = "player_id"),
        inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parents", "children", "players" }, allowSetters = true)
    private Set<Position> positions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Player id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShirtName() {
        return this.shirtName;
    }

    public Player shirtName(String shirtName) {
        this.setShirtName(shirtName);
        return this;
    }

    public void setShirtName(String shirtName) {
        this.shirtName = shirtName;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Player height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public Player weight(Integer weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Side getStrongerFoot() {
        return this.strongerFoot;
    }

    public Player strongerFoot(Side strongerFoot) {
        this.setStrongerFoot(strongerFoot);
        return this;
    }

    public void setStrongerFoot(Side strongerFoot) {
        this.strongerFoot = strongerFoot;
    }

    public Side getPreferredSide() {
        return this.preferredSide;
    }

    public Player preferredSide(Side preferredSide) {
        this.setPreferredSide(preferredSide);
        return this;
    }

    public void setPreferredSide(Side preferredSide) {
        this.preferredSide = preferredSide;
    }

    public String getContractUntil() {
        return this.contractUntil;
    }

    public Player contractUntil(String contractUntil) {
        this.setContractUntil(contractUntil);
        return this;
    }

    public void setContractUntil(String contractUntil) {
        this.contractUntil = contractUntil;
    }

    public String getRetirementDate() {
        return this.retirementDate;
    }

    public Player retirementDate(String retirementDate) {
        this.setRetirementDate(retirementDate);
        return this;
    }

    public void setRetirementDate(String retirementDate) {
        this.retirementDate = retirementDate;
    }

    public String getMiscData() {
        return this.miscData;
    }

    public Player miscData(String miscData) {
        this.setMiscData(miscData);
        return this;
    }

    public void setMiscData(String miscData) {
        this.miscData = miscData;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Player person(Person person) {
        this.setPerson(person);
        return this;
    }

    public Set<Position> getPositions() {
        return this.positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Player positions(Set<Position> positions) {
        this.setPositions(positions);
        return this;
    }

    public Player addPosition(Position position) {
        this.positions.add(position);
        position.getPlayers().add(this);
        return this;
    }

    public Player removePosition(Position position) {
        this.positions.remove(position);
        position.getPlayers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", shirtName='" + getShirtName() + "'" +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", strongerFoot='" + getStrongerFoot() + "'" +
            ", preferredSide='" + getPreferredSide() + "'" +
            ", contractUntil='" + getContractUntil() + "'" +
            ", retirementDate='" + getRetirementDate() + "'" +
            ", miscData='" + getMiscData() + "'" +
            "}";
    }
}
