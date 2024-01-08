package me.bottdev.fantasyapi.Utils;

import me.bottdev.fantasyapi.FantasyAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CooldownManager implements Listener {

    final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);

    public static HashMap<String, Long> put_time = new HashMap<>();

    public static HashMap<String, String> cooldown_name = new HashMap<>();
    public static HashMap<String, Long> cooldown_time = new HashMap<>();

    private String getHashMapId(Entity entity, String id) {
        return entity.getUniqueId() + "." + id;
    }

    public long getCooldown(Entity entity, String id) {
        return cooldown_time.getOrDefault(getHashMapId(entity, id), 0L);
    }

    public List<String> getCooldowns(Entity entity) {
        List<String> results = new ArrayList<>();
        for (String key : cooldown_time.keySet()) {
            if (!key.startsWith(entity.getUniqueId().toString())) continue;
            results.add(key.split("\\.")[1]);
        }
        return results;
    }

    private long getPutTime(Entity entity, String id) {
        return put_time.getOrDefault(getHashMapId(entity, id), 0L);
    }


    public void setCooldown(Entity entity, String id, double time) {

        long cd = System.currentTimeMillis() + (long)(time*1000);

        put_time.put(getHashMapId(entity, id), System.currentTimeMillis());
        cooldown_time.put(getHashMapId(entity, id), cd);
    }

    public void setCooldown(Entity entity, String id, double time, String name) {

        long cd = System.currentTimeMillis() + (long)(time*1000);

        put_time.put(getHashMapId(entity, id), System.currentTimeMillis());
        cooldown_time.put(getHashMapId(entity, id), cd);
        cooldown_name.put(getHashMapId(entity, id), FantasyAPI.utils.setColors(name));

        new BukkitRunnable() {
            @Override
            public void run() {
                resetCooldown(entity, id);
            }
        }.runTaskLater(plugin, (int)(time * 20));
    }

    @SuppressWarnings("deprecation")
    public void setCooldownWithMsg(Player p, String id, double time, String name) {

        setCooldown(p, id, time, name);

        new BukkitRunnable() {
            @Override
            public void run() {
                String msg = FantasyAPI.utils.setColors("&7>&8| &fCooldown &7\"" + name + "&7\" &fexpired.");

                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
                plugin.sounds.clientSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

            }
        }.runTaskLater(plugin, (int)(time * 20));
    }

    public void resetCooldown(Entity entity, String id) {
        cooldown_time.remove(getHashMapId(entity, id));
        cooldown_name.remove(getHashMapId(entity, id));
        put_time.remove(getHashMapId(entity, id));
    }

    public void resetAllCooldowns(Entity entity) {
        for (String key : cooldown_time.keySet()) {
            if (!key.startsWith(entity.getUniqueId().toString())) continue;
            put_time.remove(key);
            cooldown_name.remove(key);
            cooldown_time.remove(key);
        }
    }

    public void resetAllCooldowns() {
        for (String key : cooldown_time.keySet()) {
            put_time.remove(key);
            cooldown_name.remove(key);
            cooldown_time.remove(key);
        }
    }

    public boolean cooldownExpired(Entity entity, String id) {
        return System.currentTimeMillis() > getCooldown(entity, id);
    }

    public double getCooldownProgress(Entity entity, String id) {
        long time = System.currentTimeMillis() - getPutTime(entity, id);
        long time2 = getCooldown(entity, id) - getPutTime(entity, id);

        return  (double) time / time2 * 100;
    }

    public double getCooldownTime(Entity entity, String id) {
        double value = (double) ((getCooldown(entity, id) - getPutTime(entity, id))  - (System.currentTimeMillis() - getPutTime(entity, id))) / 1000;

        return Math.max(0, Double.parseDouble(String.valueOf(value).split("\\.")[0] + "." + String.valueOf(value).split("\\.")[1].charAt(0)));
    }

    public String getCooldownName(Entity entity, String id) {
        return cooldown_name.getOrDefault(getHashMapId(entity, id), "none");
    }

    public String getClockIcon(Entity entity, String id) {
        double progress = getCooldownProgress(entity, id);

        if (progress >= 0 && progress < 17) {
            return "&fጀ";
        }
        else if (progress >= 17 && progress < 34) {
            return "&fጁ";
        }
        else if (progress >= 34 && progress < 51) {
            return "&fጂ";
        }
        else if (progress >= 51 && progress < 68) {
            return "&fጃ";
        }
        else if (progress >= 68 && progress < 85) {
            return "&fጄ";
        }
        else if (progress >= 85 && progress <= 100) {
            return "&fጅ";
        }
        return " ";
    }

    @Deprecated
    public void sendCooldownMessage(Player p, String id) {
        try {

            plugin.sounds.clientSound(p, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1.0f, 1.0f);

            StringBuilder msg = new StringBuilder();

            msg.append(ChatColor.WHITE);
            for (int i = 0; i < 26; i++) {
                msg.append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801')
                        .append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801')
                        .append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801')
                        .append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801')
                        .append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801').append('\uF801')
                ;
            }

            double progress = getCooldownProgress(p, id);

            if (progress >= 0 && progress < 17) {
                msg.append("ጀ");
            }
            else if (progress >= 17 && progress < 34) {
                msg.append("ጁ");
            }
            else if (progress >= 34 && progress < 51) {
                msg.append("ጂ");
            }
            else if (progress >= 51 && progress < 68) {
                msg.append("ጃ");
            }
            else if (progress >= 68 && progress < 85) {
                msg.append("ጄ");
            }
            else if (progress >= 85 && progress <= 100) {
                msg.append("ጅ");
            }



            double time = getCooldownTime(p, id);
            String number = String.valueOf(time);

            number = number.replaceAll("1", "ጰ" + '\uF801').
                    replaceAll("2", "ጱ" + '\uF801')
                    .replaceAll("3", "ጲ" + '\uF801')
                    .replaceAll("4", "ጳ" + '\uF801')
                    .replaceAll("5", "ጴ" + '\uF801')
                    .replaceAll("6", "ጵ" + '\uF801')
                    .replaceAll("7", "ጶ" + '\uF801')
                    .replaceAll("8", "ጷ" + '\uF801' )
                    .replaceAll("9", "ጸ" + '\uF801')
                    .replaceAll("0", "ጹ" + '\uF801')
                    .replaceAll("\\.", "ጺ" + '\uF801');


            msg.append("  ").append(number).append("   ").append("ጻ" + '\uF801');


            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg.toString()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
