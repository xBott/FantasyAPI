package me.bottdev.fantasyapi.Utils.Attributes;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class AttributesUtils implements Listener {

    final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);

    public double getAttribute(Entity entity, FantasyAttribute attribute) {

        if (attribute == FantasyAttribute.MOVE_SPEED) {
            try {

                double delta = Objects.requireNonNull(((LivingEntity) entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).getBaseValue() - attribute.getDefaultValue();

                return attribute.getDefaultValue() + delta;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return attribute.getDefaultValue();
        }

        return 0;
    }

    public void setAttribute(Entity entity, FantasyAttribute attribute, double value) {

        if (attribute == FantasyAttribute.MOVE_SPEED) {
            try {

                Objects.requireNonNull(((LivingEntity) entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(value);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void resetAttribute(Entity entity, FantasyAttribute attribute) {

        if (attribute == FantasyAttribute.MOVE_SPEED) {
            try {

                setAttribute(entity, attribute, attribute.getDefaultValue());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addAttribute(Entity entity, FantasyAttribute attribute, double value) {

        if (attribute == FantasyAttribute.MOVE_SPEED) {
            try {

                double cur = getAttribute(entity, attribute);
                setAttribute(entity, attribute, cur + value);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void subtractAttribute(Entity entity, FantasyAttribute attribute, double value) {

        if (attribute == FantasyAttribute.MOVE_SPEED) {
            try {

                double cur = getAttribute(entity, attribute);
                setAttribute(entity, attribute, cur - value);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        resetAttribute(p, FantasyAttribute.MOVE_SPEED);
    }

}
