package grafismo.domain.enumeration;

/**
 * The Side enumeration.
 */
public enum Side {
    R("Derecha"),
    L("Izquierda"),
    B("Ambas");

    private final String value;

    Side(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
