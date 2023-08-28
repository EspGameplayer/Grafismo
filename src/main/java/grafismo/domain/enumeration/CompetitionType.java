package grafismo.domain.enumeration;

/**
 * The CompetitionType enumeration.
 */
public enum CompetitionType {
    LEAGUE("Liga"),
    LEAGUE2("Liga (2p"),
    CUP("Copa");

    private final String value;

    CompetitionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
