package me.bottdev.fantasyapi.Utils.Potions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PotionUtils {

    public static void parsePotionEffects(List<String> list, Entity entity) {
        for (String line : list) {
            String[] options = line.split(":");
            PotionEffectType type = PotionEffectType.getByName(options[0]);
            int amplifier = Integer.parseInt(options[1]);
            int duration = Integer.parseInt(options[2]);

            assert type != null;
            PotionEffect potionEffect = new PotionEffect(type, amplifier, duration, true, false);

            ((LivingEntity)entity).addPotionEffect(potionEffect);

        }
    }


}
