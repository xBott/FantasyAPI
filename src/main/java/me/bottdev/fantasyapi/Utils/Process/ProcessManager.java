package me.bottdev.fantasyapi.Utils.Process;

import me.bottdev.fantasyapi.FantasyAPI;
import me.bottdev.fantasyapi.Utils.Attributes.AttributesUtils;
import me.bottdev.fantasyapi.Utils.Attributes.FantasyAttribute;
import me.bottdev.fantasyapi.Utils.LinearInterpolator;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.HashSet;
import java.util.UUID;

public abstract class ProcessManager {

    private final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);
    private static final HashSet<UUID> process = new HashSet<>();

    private final Entity entity;
    private final int time;
    private final double slow;

    private int timer = 0;

    public ProcessManager(Entity entity, int time, double slow) {
        this.entity = entity;
        this.time = time;
        this.slow = slow;

        Start();
    }

    @SuppressWarnings("deprecation")
    private void Start() {

        if (process.contains(entity.getUniqueId())) return;
        process.add(entity.getUniqueId());

        if (time == 0) {
            process.remove(entity.getUniqueId());
            Run();
            return;
        }

        if (entity instanceof Player p) {
            plugin.sounds.clientSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }

        AttributesUtils attributesUtils = new AttributesUtils();
        attributesUtils.subtractAttribute(entity, FantasyAttribute.MOVE_SPEED, slow);

        onStart();

        new BukkitRunnable() {

            @Override
            public void run() {
                timer++;
                if (timer == time) {
                    if (entity instanceof Player p) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(" "));
                    }
                    process.remove(entity.getUniqueId());
                    attributesUtils.resetAttribute(entity, FantasyAttribute.MOVE_SPEED);
                    Run();
                    this.cancel();
                    return;
                }

                if (entity instanceof Player p) {
                    sendActionBar(p);
                }

                onTick();
            }

        }.runTaskTimer(plugin, 0, 1);
    }

    @SuppressWarnings("deprecation")
    private void sendActionBar(Player p) {

        int count = 80;
        char c = '|';

        double percent = (double) timer / time;

        int filled = (int) Math.floor(count * percent);
        int empty = (int) Math.floor(count * (1 - percent));

        String message = FantasyAPI.utils.setColors(
                "&f[ " +
                FantasyAPI.utils.hsvGradient(String.valueOf(c).repeat(Math.max(0, filled)), new Color(255, 255, 0), new Color(204, 255, 51), new LinearInterpolator()) +
                        "&7" + String.valueOf(c).repeat(Math.max(0, empty)) + " &f]");
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }


    public abstract void Run();

    public abstract void onStart();

    public abstract void onTick();


}
