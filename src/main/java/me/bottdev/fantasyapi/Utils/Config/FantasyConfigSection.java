package me.bottdev.fantasyapi.Utils.Config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class FantasyConfigSection {

    private final ConfigurationSection section;

    public FantasyConfigSection(ConfigurationSection section) {
        this.section = section;
    }

    @SuppressWarnings("unused")
    public FantasyConfigSection(Configuration cfg, String id) {
        this.section = cfg.getConfigurationSection(id);
    }

    public ConfigurationSection getSourceSection() {
        return section;
    }

    public String getString(String path, String default_value) {
        try {
            return section.getString(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public List<String> getStringList(String path, List<String> default_value) {
        try {
            return section.getStringList(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public int getInteger(String path, int default_value) {
        try {
            return section.getInt(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public List<Integer> getIntegerList(String path, List<Integer> default_value) {
        try {
            return section.getIntegerList(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public double getDouble(String path, double default_value) {
        try {
            return section.getDouble(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public boolean getBoolean(String path, boolean default_value) {
        try {
            return section.getBoolean(path);
        } catch (NullPointerException ex) {
            return default_value;
        }
    }

    public FantasyConfigSection getSection(String path, FantasyConfigSection default_value) {
        try {
            return new FantasyConfigSection(section.getConfigurationSection(path));
        } catch (NullPointerException ex) {
            return default_value;
        }
    }
}
