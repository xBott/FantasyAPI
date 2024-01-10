package me.bottdev.fantasyapi.Utils.Process;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ProcessListener implements Listener {

    private final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);

    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (ProcessManager.process.contains(p.getUniqueId())) {
                e.setCancelled(true);

                ProcessManager.cancel.add(p.getUniqueId());

                Bukkit.getScheduler().runTaskLater(plugin, () -> ProcessManager.cancel.remove(p.getUniqueId()), 2);

            }
        }
    }
}
