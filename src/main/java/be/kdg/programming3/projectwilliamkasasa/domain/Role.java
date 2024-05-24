package be.kdg.programming3.projectwilliamkasasa.domain;

public enum Role {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String code;

    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
