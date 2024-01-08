package me.bottdev.fantasyapi.Utils.Gui.MultiPageGui;

import me.bottdev.fantasyapi.FantasyAPI;
import me.bottdev.fantasyapi.Utils.Gui.Default.GuiManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MPGuiListener implements Listener {

    private final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);
    private final MPGuiManager guiManager = new MPGuiManager();

    @EventHandler
    private void onGuiClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        guiManager.clearGui(p);
    }

    @EventHandler
    private void onGuiClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (guiManager.isOpen(p) && !new GuiManager().isOpen(p)) {

            MultiPageGui gui = guiManager.getGui(p);

            ItemStack clicked = e.getCurrentItem();
            if (p.getInventory().contains(clicked)) return;
            if (clicked == null || clicked.getType() == Material.AIR) return;

            e.setCancelled(true);

            ItemMeta meta = clicked.getItemMeta();
            String id = meta.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin, "id"), PersistentDataType.STRING, "none");

            int page = gui.getPage();

            switch (id) {
                case "previous" -> gui.Update(Math.max(page - 1, 0));

                case "next" -> gui.Update(Math.min(page + 1, gui.getMaxPage()));

                case "close" -> gui.Close();
            }

            gui.clickButton(id, e.getClick(), page);

        }
    }
}
