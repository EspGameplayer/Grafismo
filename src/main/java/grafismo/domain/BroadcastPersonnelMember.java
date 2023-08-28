package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grafismo.domain.enumeration.BroadcastPersonnelMemberRole;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BroadcastPersonnelMember.
 */
@Entity
@Table(name = "broadcast_personnel_member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BroadcastPersonnelMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "graphics_name", nullable = false)
    private String graphicsName;

    @Column(name = "long_graphics_name")
    private String longGraphicsName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private BroadcastPersonnelMemberRole role;

    @JsonIgnoreProperties(value = { "nationality", "birthplace" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Person person;

    @ManyToMany(mappedBy = "broadcastPersonnelMembers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "match", "systemConfiguration", "broadcastPersonnelMembers" }, allowSetters = true)
    private Set<Broadcast> broadcasts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BroadcastPersonnelMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGraphicsName() {
        return this.graphicsName;
    }

    public BroadcastPersonnelMember graphicsName(String graphicsName) {
        this.setGraphicsName(graphicsName);
        return this;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getLongGraphicsName() {
        return this.longGraphicsName;
    }

    public BroadcastPersonnelMember longGraphicsName(String longGraphicsName) {
        this.setLongGraphicsName(longGraphicsName);
        return this;
    }

    public void setLongGraphicsName(String longGraphicsName) {
        this.longGraphicsName = longGraphicsName;
    }

    public BroadcastPersonnelMemberRole getRole() {
        return this.role;
    }

    public BroadcastPersonnelMember role(BroadcastPersonnelMemberRole role) {
        this.setRole(role);
        return this;
    }

    public void setRole(BroadcastPersonnelMemberRole role) {
        this.role = role;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public BroadcastPersonnelMember person(Person person) {
        this.setPerson(person);
        return this;
    }

    public Set<Broadcast> getBroadcasts() {
        return this.broadcasts;
    }

    public void setBroadcasts(Set<Broadcast> broadcasts) {
        if (this.broadcasts != null) {
            this.broadcasts.forEach(i -> i.removeBroadcastPersonnelMember(this));
        }
        if (broadcasts != null) {
            broadcasts.forEach(i -> i.addBroadcastPersonnelMember(this));
        }
        this.broadcasts = broadcasts;
    }

    public BroadcastPersonnelMember broadcasts(Set<Broadcast> broadcasts) {
        this.setBroadcasts(broadcasts);
        return this;
    }

    public BroadcastPersonnelMember addBroadcast(Broadcast broadcast) {
        this.broadcasts.add(broadcast);
        broadcast.getBroadcastPersonnelMembers().add(this);
        return this;
    }

    public BroadcastPersonnelMember removeBroadcast(Broadcast broadcast) {
        this.broadcasts.remove(broadcast);
        broadcast.getBroadcastPersonnelMembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BroadcastPersonnelMember)) {
            return false;
        }
        return id != null && id.equals(((BroadcastPersonnelMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BroadcastPersonnelMember{" +
            "id=" + getId() +
            ", graphicsName='" + getGraphicsName() + "'" +
            ", longGraphicsName='" + getLongGraphicsName() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
