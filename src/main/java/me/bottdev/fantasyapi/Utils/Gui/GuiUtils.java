package me.bottdev.fantasyapi.Utils.Gui;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GuiUtils {

    private final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);


    public boolean checkLocked(ItemStack itm) {

        ItemMeta meta = itm.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "locked");
        return meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER) &&
                meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER).equals(1);

    }

}
