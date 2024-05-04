package me.bottdev.fantasyapi.Utils.Inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

public class InventoryManager {

    public static final HashMap<UUID, ItemStack[]> hidden_inventory = new HashMap<>();

    private final Player p;

    public InventoryManager(Player p) {
        this.p = p;
    }

    public void clearInventory() {
        p.getInventory().clear();

        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
    }


    public void hideInventory() {
        hidden_inventory.put(p.getUniqueId(), p.getInventory().getContents());

        clearInventory();
    }

    public void showInventory() {

        ItemStack[] inv = hidden_inventory.getOrDefault(p.getUniqueId(), new ItemStack[]{});

        for (int i = 0; i < inv.length; i ++) {
            p.getInventory().setItem(i, inv[i]);
        }

        hidden_inventory.remove(p.getUniqueId());
    }

    public boolean hasFreeSpace(ItemStack itm, int amount) {

        PlayerInventory inv = p.getInventory();
        if(inv.firstEmpty() == -1) {
            return false;
        }

        int neededSpace = amount;

        for(ItemStack stack : inv.getContents()) {

            if (stack == null) continue;
            if (!stack.equals(itm))
                continue;

            int untilStackIsFull = stack.getMaxStackSize() - stack.getAmount();

            neededSpace -= untilStackIsFull;

            if(neededSpace <= 0)
                return true;
        }
        return false;
    }



}
