package me.bottdev.fantasyapi.Utils.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerOptionListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player p) {
            PlayerOptionManager optionManager = new PlayerOptionManager(p);
            if (optionManager.isGod()) e.setCancelled(true);
         }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player p) {
            PlayerOptionManager optionManager = new PlayerOptionManager(p);
            if (optionManager.isGod()) e.setCancelled(true);
        }

    }



}
