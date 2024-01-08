package me.bottdev.fantasyapi.Items;

import me.bottdev.fantasyapi.FantasyAPI;
import me.bottdev.fantasyapi.Utils.Config.FantasyConfigSection;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class LoreManager {

    private final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);

    public List<String> getLore(ConfigurationSection section) {
        FantasyConfigSection fantasyConfigSection = new FantasyConfigSection(section);
        List<String> raw_lore = fantasyConfigSection.getStringList("lore", List.of("&7none"));
        return formatLore(raw_lore);
    }

    public List<String> getLore(FantasyConfigSection fantasyConfigSection) {
        List<String> raw_lore = fantasyConfigSection.getStringList("lore", List.of("&7none"));
        return formatLore(raw_lore);
    }

    @SuppressWarnings("unused")
    public List<String> formatLore(List<String> raw_lore) {
        List<String> formatted_lore = new ArrayList<>();
        for (String line : raw_lore) {
            formatted_lore.add(plugin.utils.setColors(line));
        }
        return formatted_lore;
    }



}
