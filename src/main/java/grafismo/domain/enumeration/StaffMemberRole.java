package grafismo.domain.enumeration;

/**
 * The StaffMemberRole enumeration.
 */
public enum StaffMemberRole {
    DT("Entrenador"),
    DT2("Segundo entrenador"),
    TEAM_DELEGATE("Delegado del equipo"),
    MATCH_DELEGATE("Delegado de campo"),
    PRESIDENT("Presidente"),
    BOARD_MEMBER("Miembro de la junta directiva"),
    MEMBER("Miembro");

    private final String value;

    StaffMemberRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
