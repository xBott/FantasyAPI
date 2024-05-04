package me.bottdev.fantasyapi.Utils.Player;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class PlayerOptionManager {

    public static HashSet<UUID> god = new HashSet<>();

    private final Player p;

    public PlayerOptionManager(Player p) {
        this.p = p;
    }

    public boolean isGod() {
        return god.contains(p.getUniqueId());
    }

    public void setGod(boolean value) {
        if (value) {
            god.add(p.getUniqueId());
        } else {
            god.remove(p.getUniqueId());
        }
    }

}
