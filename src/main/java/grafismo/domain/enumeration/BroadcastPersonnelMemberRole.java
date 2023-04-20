package grafismo.domain.enumeration;

/**
 * The BroadcastPersonnelMemberRole enumeration.
 */
public enum BroadcastPersonnelMemberRole {
    NARRATOR("Narrador"),
    ANALYST("Analista"),
    COMENTATOR("Comentarista"),
    SIDELINE_REPORTER("Reportero a pie de campo"),
    PRODUCER("Realizador"),
    CAMERAMAN("Operador de cámara");

    private final String value;

    BroadcastPersonnelMemberRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
