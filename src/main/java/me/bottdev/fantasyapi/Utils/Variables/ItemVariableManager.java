package me.bottdev.fantasyapi.Utils.Variables;

import me.bottdev.fantasyapi.FantasyAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemVariableManager {

    final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);
    private final VariableType type;
    private final String id;
    private final NamespacedKey key;


    public ItemVariableManager(String id, VariableType type) {
        this.id = id;
        this.type = type;
        this.key = new NamespacedKey(plugin, id);
    }

    public String getId() {
        return id;
    }

    public Object getValue(ItemStack itm) {

        if (itm == null) return null;

        ItemMeta meta = itm.getItemMeta();
        assert meta != null;

        return switch (type) {
            case INTEGER -> meta.getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
            case DOUBLE -> meta.getPersistentDataContainer().getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
            case STRING -> meta.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "none");
        };
    }

    public void setValue(ItemStack itm, Object value) {

        if (itm == null) return;

        ItemMeta meta = itm.getItemMeta();
        assert meta != null;

        switch (type) {
            case INTEGER -> meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, (int) value);
            case DOUBLE -> meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, (double) value);
            case STRING -> meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, (String) value);
        }

        itm.setItemMeta(meta);
    }

    public void addValue(ItemStack itm, double value) {

        if (itm == null) return;

        ItemMeta meta = itm.getItemMeta();
        assert meta != null;

        switch (type) {
            case INTEGER -> setValue(itm, (int) getValue(itm) + value);
            case DOUBLE -> setValue(itm, (double) getValue(itm) + value);
        }
    }

    public void subtractValue(ItemStack itm, double value) {

        if (itm == null) return;

        ItemMeta meta = itm.getItemMeta();
        assert meta != null;

        switch (type) {
            case INTEGER -> setValue(itm, (int) getValue(itm) - value);
            case DOUBLE -> setValue(itm, (double) getValue(itm) - value);
        }
    }


}
