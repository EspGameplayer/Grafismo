package grafismo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Injury} entity.
 */
public class InjuryDTO implements Serializable {

    private Long id;

    private Instant moment;

    private String estHealingTime;

    private Instant estComebackDate;

    private String reason;

    private PlayerDTO player;

    private MatchDTO match;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public String getEstHealingTime() {
        return estHealingTime;
    }

    public void setEstHealingTime(String estHealingTime) {
        this.estHealingTime = estHealingTime;
    }

    public Instant getEstComebackDate() {
        return estComebackDate;
    }

    public void setEstComebackDate(Instant estComebackDate) {
        this.estComebackDate = estComebackDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InjuryDTO)) {
            return false;
        }

        InjuryDTO injuryDTO = (InjuryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, injuryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InjuryDTO{" +
            "id=" + getId() +
            ", moment='" + getMoment() + "'" +
            ", estHealingTime='" + getEstHealingTime() + "'" +
            ", estComebackDate='" + getEstComebackDate() + "'" +
            ", reason='" + getReason() + "'" +
            ", player=" + getPlayer() +
            ", match=" + getMatch() +
            "}";
    }
}
