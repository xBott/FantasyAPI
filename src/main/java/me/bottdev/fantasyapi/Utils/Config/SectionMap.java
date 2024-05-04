package me.bottdev.fantasyapi.Utils.Config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class SectionMap {

    final HashMap<String, ConfigurationSection> loaded;
    final String path;

    public SectionMap(HashMap<String, ConfigurationSection> loaded, String path) {
        this.loaded = loaded;
        this.path = "plugins/DarkFantasy/" + path;

        loadFromFolder(this.path);
    }

    public SectionMap(HashMap<String, ConfigurationSection> loaded, String namespace, String path) {
        this.loaded = loaded;
        this.path = "plugins/" + namespace + "/" + path;

        loadFromFolder(this.path);
    }

    private void loadFromFolder(String path) {
        try {

            File file = new File(path);

            if (file.exists() && file.isDirectory()) {

                File[] files = file.listFiles();

                if (files == null || files.length == 0) return;

                for (File f : files) {

                    if (f.isDirectory()) {
                        loadFromFolder(f.getPath());
                        continue;
                    }

                    Configuration cfg = YamlConfiguration.loadConfiguration(f);

                    for (String house : Objects.requireNonNull(cfg.getConfigurationSection("")).getKeys(false)) {
                        loaded.put(house, cfg.getConfigurationSection(house));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HashMap<String, ConfigurationSection> getHashMap() {
        return loaded;
    }

    public String[] getKeys() {
        return loaded.keySet().toArray(new String[0]);
    }

    public ConfigurationSection getSection(String id) {
        return loaded.getOrDefault(id, null);
    }




}
