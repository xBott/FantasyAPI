package me.bottdev.fantasyapi.Utils.Attributes;

public enum FantasyAttribute {
    MOVE_SPEED("move_speed", 0.10000000149315),
    WALKING_SPEED("walk_speed", 0.2);

    private final String id;
    private final double default_value;

    FantasyAttribute(String id, double defaultValue) {
        this.id = id;
        this.default_value = defaultValue;
    }

    public String getId() {
        return id;
    }

    public double getDefaultValue() {
        return default_value;
    }
}
