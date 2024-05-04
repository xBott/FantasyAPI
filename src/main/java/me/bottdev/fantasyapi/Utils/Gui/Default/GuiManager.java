package me.bottdev.fantasyapi.Utils.Gui.Default;

import me.bottdev.fantasyapi.FantasyAPI;
import me.bottdev.fantasyapi.Items.LoreManager;
import me.bottdev.fantasyapi.Utils.Config.FantasyConfigSection;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuiManager {

    private final LoreManager loreManager = new LoreManager();

    public static final HashMap<UUID, GuiTemplate> gui_open = new HashMap<>();

    public boolean isOpen(Player p) {
        return gui_open.containsKey(p.getUniqueId());
    }

    public GuiTemplate getGui(Player p) {
        return gui_open.getOrDefault(p.getUniqueId(), null);
    }

    public void setGui(Player p, GuiTemplate gui) {
        if (gui_open.containsKey(p.getUniqueId())) {
            gui_open.replace(p.getUniqueId(), gui);
        } else {
            gui_open.put(p.getUniqueId(), gui);
        }
    }

    public void clearGui(Player p) {
        gui_open.remove(p.getUniqueId());
    }


    @SuppressWarnings("deprecation")
    public GuiContentItem getGuiContentItem(FantasyConfigSection fantasyConfigSection, String id) {

        Material material = Material.valueOf(fantasyConfigSection.getString("material", "BARRIER"));
        ItemStack itm = new ItemStack(material);
        ItemMeta meta = itm.getItemMeta();
        assert meta != null;

        int cmd = fantasyConfigSection.getInteger("custom_model_data", 0);
        meta.setCustomModelData(cmd);

        String name = FantasyAPI.utils.setColors(fantasyConfigSection.getString("name", "&cNONE"));
        meta.setDisplayName(name);

        List<String> lore = new ArrayList<>(loreManager.getLore(fantasyConfigSection));

        meta.setLore(lore);
        itm.setItemMeta(meta);

        int slot = fantasyConfigSection.getInteger("slot", 0);

        return new GuiContentItem(itm, id, slot);
    }


}
