package me.bottdev.fantasyapi.Utils.Particles;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.List;

public class ParticleUtils {

    private static final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);

    public static void parseParticles(Location loc, List<String> list) {

        if (list.isEmpty()) return;

        for (String part : list) {

            try {

                String[] options = part.split(":");

                Particle particle = Particle.valueOf(options[0]);
                int count = Integer.parseInt(options[1]);

                double x = Double.parseDouble(options[2]);
                double y = Double.parseDouble(options[3]);
                double z = Double.parseDouble(options[4]);

                double speed = Double.parseDouble(options[5]);

                double chance = 100;
                if (options.length > 6) {
                    chance = Double.parseDouble(options[6]);
                }

                if (FantasyAPI.utils.getRandomNumber(0, 100) > (100 - chance) && loc != null)
                    loc.getWorld().spawnParticle(particle, loc, count, x, y, z, speed);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
