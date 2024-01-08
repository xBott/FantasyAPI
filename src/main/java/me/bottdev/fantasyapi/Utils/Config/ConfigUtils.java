package me.bottdev.fantasyapi.Utils.Config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class ConfigUtils {

    private final JavaPlugin plugin;

    public ConfigUtils(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static HashMap<String, Configuration> loaded = new HashMap<>();
    public static HashMap<String, ConfigMap> configMaps = new HashMap<>();
    public static HashMap<String, SectionMap> sectionMaps = new HashMap<>();

    public HashMap<String, ConfigMap> getConfigMaps() {
        return configMaps;
    }

    public void loadConfigMaps() {
        ConfigMap weaponList = new ConfigMap(new HashMap<>(), "Items\\\\Weapon", "Items/Weapon");
        configMaps.put("weaponList", weaponList);
        ConfigMap armorList = new ConfigMap(new HashMap<>(),"Items\\\\Armor", "Items/Armor");
        configMaps.put("armorList", armorList);
        ConfigMap otherList = new ConfigMap(new HashMap<>(),"Items\\\\Other", "Items/Other");
        configMaps.put("otherList", otherList);
        ConfigMap furnitureList = new ConfigMap(new HashMap<>(),"Items\\\\Furniture", "Items/Furniture");
        configMaps.put("furnitureList", furnitureList);
    }

    public void reloadConfigMap(String id, String id2, String put) {
        ConfigMap list = new ConfigMap(new HashMap<>(), id2, id);
        configMaps.put(put, list);
    }

    public HashMap<String, SectionMap> getSectionMaps() {
        return sectionMaps;
    }

    @SuppressWarnings("unused")
    public void clear() {
        loaded.clear();
    }

    @SuppressWarnings("all")
    public void load(String path, String id) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

        Configuration cfg = YamlConfiguration.loadConfiguration(file);
        loaded.remove(id);
        loaded.put(id, cfg);
    }

    public Configuration get(String id) {
        for (String key : loaded.keySet()) {
            if (key.equalsIgnoreCase(id)) {
                return loaded.getOrDefault(key, null);
            }
        }
        return null;
    }

    @SuppressWarnings("all")
    public void loadResource(String path, String id) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        File file = new File(plugin.getDataFolder().getAbsolutePath(), path + "/" + id + ".yml");
        if (!file.exists()) {
            plugin.saveResource(path + "/" + id + ".yml", false);
        }
    }

    @SuppressWarnings("all")
    public void loadResource(String id) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        File file = new File(plugin.getDataFolder().getAbsolutePath(), id + ".yml");
        if (!file.exists()) {
            plugin.saveResource(id + ".yml", false);
        }
    }

}
