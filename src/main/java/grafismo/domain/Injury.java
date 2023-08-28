package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Injury.
 */
@Entity
@Table(name = "injury")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Injury implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "moment")
    private Instant moment;

    @Column(name = "est_healing_time")
    private String estHealingTime;

    @Column(name = "est_comeback_date")
    private Instant estComebackDate;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "person", "positions" }, allowSetters = true)
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "motm",
            "homeLineup",
            "awayLineup",
            "homeTeam",
            "awayTeam",
            "venue",
            "matchDelegate",
            "homeShirt",
            "awayShirt",
            "matchday",
            "referees",
            "deductions",
            "suspensions",
            "injuries",
        },
        allowSetters = true
    )
    private Match match;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Injury id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return this.moment;
    }

    public Injury moment(Instant moment) {
        this.setMoment(moment);
        return this;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public String getEstHealingTime() {
        return this.estHealingTime;
    }

    public Injury estHealingTime(String estHealingTime) {
        this.setEstHealingTime(estHealingTime);
        return this;
    }

    public void setEstHealingTime(String estHealingTime) {
        this.estHealingTime = estHealingTime;
    }

    public Instant getEstComebackDate() {
        return this.estComebackDate;
    }

    public Injury estComebackDate(Instant estComebackDate) {
        this.setEstComebackDate(estComebackDate);
        return this;
    }

    public void setEstComebackDate(Instant estComebackDate) {
        this.estComebackDate = estComebackDate;
    }

    public String getReason() {
        return this.reason;
    }

    public Injury reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Injury player(Player player) {
        this.setPlayer(player);
        return this;
    }

    public Match getMatch() {
        return this.match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Injury match(Match match) {
        this.setMatch(match);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Injury)) {
            return false;
        }
        return id != null && id.equals(((Injury) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Injury{" +
            "id=" + getId() +
            ", moment='" + getMoment() + "'" +
            ", estHealingTime='" + getEstHealingTime() + "'" +
            ", estComebackDate='" + getEstComebackDate() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
