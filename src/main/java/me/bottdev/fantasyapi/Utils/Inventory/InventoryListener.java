package me.bottdev.fantasyapi.Utils.Inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class InventoryListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    private void onPickUp(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();

        if (InventoryManager.hidden_inventory.containsKey(p.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onPickUp(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();

        if (InventoryManager.hidden_inventory.containsKey(p.getUniqueId())) {
            e.setCancelled(true);
        }
    }


}

