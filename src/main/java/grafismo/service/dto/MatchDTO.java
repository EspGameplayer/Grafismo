package grafismo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link grafismo.domain.Match} entity.
 */
public class MatchDTO implements Serializable {

    private Long id;

    private Instant moment;

    private Integer attendance;

    private String hashtag;

    private String details;

    private String miscData;

    private MatchPlayerDTO motm;

    private LineupDTO homeLineup;

    private LineupDTO awayLineup;

    private TeamDTO homeTeam;

    private TeamDTO awayTeam;

    private VenueDTO venue;

    private TeamStaffMemberDTO matchDelegate;

    private ShirtDTO homeShirt;

    private ShirtDTO awayShirt;

    private MatchdayDTO matchday;

    private Set<RefereeDTO> referees = new HashSet<>();

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

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
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

    public MatchPlayerDTO getMotm() {
        return motm;
    }

    public void setMotm(MatchPlayerDTO motm) {
        this.motm = motm;
    }

    public LineupDTO getHomeLineup() {
        return homeLineup;
    }

    public void setHomeLineup(LineupDTO homeLineup) {
        this.homeLineup = homeLineup;
    }

    public LineupDTO getAwayLineup() {
        return awayLineup;
    }

    public void setAwayLineup(LineupDTO awayLineup) {
        this.awayLineup = awayLineup;
    }

    public TeamDTO getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamDTO homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamDTO getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamDTO awayTeam) {
        this.awayTeam = awayTeam;
    }

    public VenueDTO getVenue() {
        return venue;
    }

    public void setVenue(VenueDTO venue) {
        this.venue = venue;
    }

    public TeamStaffMemberDTO getMatchDelegate() {
        return matchDelegate;
    }

    public void setMatchDelegate(TeamStaffMemberDTO matchDelegate) {
        this.matchDelegate = matchDelegate;
    }

    public ShirtDTO getHomeShirt() {
        return homeShirt;
    }

    public void setHomeShirt(ShirtDTO homeShirt) {
        this.homeShirt = homeShirt;
    }

    public ShirtDTO getAwayShirt() {
        return awayShirt;
    }

    public void setAwayShirt(ShirtDTO awayShirt) {
        this.awayShirt = awayShirt;
    }

    public MatchdayDTO getMatchday() {
        return matchday;
    }

    public void setMatchday(MatchdayDTO matchday) {
        this.matchday = matchday;
    }

    public Set<RefereeDTO> getReferees() {
        return referees;
    }

    public void setReferees(Set<RefereeDTO> referees) {
        this.referees = referees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchDTO)) {
            return false;
        }

        MatchDTO matchDTO = (MatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchDTO{" +
            "id=" + getId() +
            ", moment='" + getMoment() + "'" +
            ", attendance=" + getAttendance() +
            ", hashtag='" + getHashtag() + "'" +
            ", details='" + getDetails() + "'" +
            ", miscData='" + getMiscData() + "'" +
            ", motm=" + getMotm() +
            ", homeLineup=" + getHomeLineup() +
            ", awayLineup=" + getAwayLineup() +
            ", homeTeam=" + getHomeTeam() +
            ", awayTeam=" + getAwayTeam() +
            ", venue=" + getVenue() +
            ", matchDelegate=" + getMatchDelegate() +
            ", homeShirt=" + getHomeShirt() +
            ", awayShirt=" + getAwayShirt() +
            ", matchday=" + getMatchday() +
            ", referees=" + getReferees() +
            "}";
    }
}
