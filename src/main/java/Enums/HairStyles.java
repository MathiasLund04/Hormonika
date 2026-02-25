package Enums;

public enum HairStyles {
    MANCUT("Mande klipning"),
    WOMANCUT("Kvinde klipning"),
    CHILDCUT("Børne klipning"),
    COLOUR("Farve"),
    PERM("Permanent"),
    BEARD("Skæg klipning"),
    OTHER("Andet");

    private final String description;
    HairStyles(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

}
