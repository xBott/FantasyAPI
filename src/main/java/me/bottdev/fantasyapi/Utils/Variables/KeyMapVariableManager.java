package me.bottdev.fantasyapi.Utils.Variables;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class KeyMapVariableManager {

    final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);
    private final VariableType type;
    private final String id;
    private final NamespacedKey key;


    public KeyMapVariableManager(String id, VariableType type) {
        this.id = id;
        this.type = type;
        this.key = new NamespacedKey(plugin, id);
    }

    private Map<String, Object> getKeyMap(Entity e) {
        String source = e.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "none");
        HashMap<String, Object> result = new HashMap<>();
        if (!source.equalsIgnoreCase("none") && source.length() > 0) {
            if (source.contains(";")) {
                String[] elements = source.split(";");
                for (String element : elements) {
                    String[] parts = element.split("=");
                    result.put(parts[0], parts[1]);
                }
            } else {
                String[] parts = source.split("=");
                result.put(parts[0], parts[1]);
            }
        }
        return result;
    }

    private void generateKeyMap(Entity e, Map<String, Object> map) {
        int size = map.keySet().size();
        int counter = 0;
        StringBuilder result = new StringBuilder();
        for (String k : map.keySet()) {
            counter++;
            Object element = map.get(k);
            String value = String.valueOf(element);
            result.append(counter != size ? k + "=" + value + ";" : k + "=" + value);
        }
        e.getPersistentDataContainer().set(key, PersistentDataType.STRING, result.toString());
    }

    public String getId() {
        return id;
    }

    public Object getValue(Entity e, String k) {
        return switch (type) {
            case INTEGER, DOUBLE, STRING -> getKeyMap(e).get(k);
        };
    }

    public void setValue(Entity e, String k, Object value) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                Map<String, Object> map = getKeyMap(e);
                if (map.containsKey(k)) {
                    map.replace(k, value);
                } else {
                    map.put(k, value);
                }
                generateKeyMap(e, map);
            }
        }
    }

    public void removeValue(Entity e, String k) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                Map<String, Object> map = getKeyMap(e);
                map.remove(k);
                if (map.isEmpty()) {
                    e.getPersistentDataContainer().set(key, PersistentDataType.STRING, "none");
                }
                generateKeyMap(e, map);
            }
        }
    }

    public boolean containsKey(Entity e, String k) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                Map<String, Object> map = getKeyMap(e);
                return map.containsKey(k);
            }
        }
        return false;
    }



}
