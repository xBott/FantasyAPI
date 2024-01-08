package me.bottdev.fantasyapi.Utils.Variables;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListVariableManager {

    final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);
    private final VariableType type;
    private final String id;
    private final NamespacedKey key;


    public ListVariableManager(String id, VariableType type) {
        this.id = id;
        this.type = type;
        this.key = new NamespacedKey(plugin, id);
    }

    private List<Object> getSourceList(Entity e) {
        String source = e.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "none");
        List<Object> result = new ArrayList<>();
        if (!source.equalsIgnoreCase("none")) {
            if (source.contains(";")) {
                String[] elements = source.split(";");
                result.addAll(Arrays.asList(elements));
            } else {
                result.add(source);
            }
        }
        return result;
    }

    private void generateList(Entity e, List<Object> list) {
        int size = list.size();
        int counter = 0;
        StringBuilder result = new StringBuilder();
        for (Object value : list) {
            counter++;
            result.append(counter != size ? value + ";" : value);
        }
        e.getPersistentDataContainer().set(key, PersistentDataType.STRING, result.toString());
    }

    public String getId() {
        return id;
    }

    public List<Object> getList(Entity e) {
        return switch (type) {
            case INTEGER, DOUBLE, STRING -> getSourceList(e);
        };
    }

    public Object getValue(Entity e, int index) {
        return switch (type) {
            case INTEGER, DOUBLE, STRING -> getSourceList(e).get(index);
        };
    }

    public void addValue(Entity e, Object value) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                List<Object> list = getSourceList(e);
                list.add(value);
                generateList(e, list);
            }
        }
    }

    public void removeValue(Entity e, Object value) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                List<Object> list = getSourceList(e);
                list.remove(value);
                generateList(e, list);
            }
        }
    }

    public void setValue(Entity e, int index, Object value) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                List<Object> list = getSourceList(e);
                list.set(index, value);
                generateList(e, list);
            }
        }
    }

    public boolean containsKey(Entity e, Object value) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                List<Object> list = getSourceList(e);
                return list.contains(value);
            }
        }
        return false;
    }

    public int getSize(Entity e) {
        switch (type) {
            case DOUBLE, STRING, INTEGER -> {
                List<Object> list = getSourceList(e);
                return list.size();
            }
        }
        return 0;
    }




}
