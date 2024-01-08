package me.bottdev.fantasyapi.Utils.Config;

import org.bukkit.configuration.Configuration;

import java.util.List;

public class FantasyConfig {

    private final Configuration cfg;

    public FantasyConfig(Configuration cfg) {
        this.cfg = cfg;
    }

    public Configuration getConfig() {
        return cfg;
    }


    public String getString(String path, String default_value) {
        try {
            return cfg.getString(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public List<String> getStringList(String path, List<String> default_value) {
        try {
            return cfg.getStringList(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public int getInteger(String path, int default_value) {
        try {
            return cfg.getInt(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public List<Integer> getIntegerList(String path, List<Integer> default_value) {
        try {
            return cfg.getIntegerList(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public double getDouble(String path, double default_value) {
        try {
            return cfg.getDouble(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public boolean getBoolean(String path, boolean default_value) {
        try {
            return cfg.getBoolean(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public FantasyConfigSection getSection(String path, FantasyConfigSection default_value) {
        try {
            return new FantasyConfigSection(cfg.getConfigurationSection(path));
        } catch (NullPointerException ex) {
            return default_value;
        }
    }
}
