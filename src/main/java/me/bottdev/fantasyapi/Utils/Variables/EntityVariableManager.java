package me.bottdev.fantasyapi.Utils.Variables;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

public class EntityVariableManager {

    final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);
    private final VariableType type;
    private final String id;
    private final NamespacedKey key;


    public EntityVariableManager(String id, VariableType type) {
        this.id = id;
        this.type = type;
        this.key = new NamespacedKey(plugin, id);
    }

    public String getId() {
        return id;
    }

    public Object getValue(Entity e) {
        return switch (type) {
            case INTEGER -> e.getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
            case DOUBLE -> e.getPersistentDataContainer().getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
            case STRING -> e.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "none");
        };
    }

    public void setValue(Entity e, Object value) {
        switch (type) {
            case INTEGER -> e.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, (int) value);
            case DOUBLE -> e.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, (double) value);
            case STRING -> e.getPersistentDataContainer().set(key, PersistentDataType.STRING, (String) value);
        }
    }

    public void addValue(Entity e, double value) {
        switch (type) {
            case INTEGER -> setValue(e, (int) getValue(e) + value);
            case DOUBLE -> setValue(e, (double) getValue(e) + value);
        }
    }

    public void subtractValue(Entity e, double value) {
        switch (type) {
            case INTEGER -> setValue(e, (int) getValue(e) - value);
            case DOUBLE -> setValue(e, (double) getValue(e) - value);
        }
    }



}
