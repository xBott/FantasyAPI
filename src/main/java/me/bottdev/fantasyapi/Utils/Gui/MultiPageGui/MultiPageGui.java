package me.bottdev.fantasyapi.Utils.Gui.MultiPageGui;

import me.bottdev.fantasyapi.FantasyAPI;
import me.bottdev.fantasyapi.Utils.Config.FantasyConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MultiPageGui {

    protected final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);
    private final MPGuiManager mpgManager = new MPGuiManager();
    protected MPGuiMode mode = MPGuiMode.HIDE_BUTTONS;

    protected final Player p;
    protected final FantasyConfig cfg;

    public Inventory gui;

    protected String id;
    protected String title;
    protected int size;
    protected int[] slots;

    protected int content_slots_length;
    protected int content_length;

    protected int current_page;

    public MultiPageGui(Player p, Configuration cfg) {

        this.p = p;

        this.cfg = new FantasyConfig(cfg);

        this.id = "default";
        this.title = "Default";
        this.size = 9;

        this.slots = new int[]{};
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public int getMaxPage() {
        return  (int)Math.ceil((double) content_length / content_slots_length) - 1;
    }


    protected List<MPGContentItem> getContents() {
        return new ArrayList<>();
    }

    protected List<MPGContentItem> getPersistentItems() {
        return new ArrayList<>();
    }

    protected List<MPGContentItem> getReplacedArrows() {
        return new ArrayList<>();
    }


    @SuppressWarnings("deprecation")
    protected void createInventory() {
        try {

            gui = Bukkit.createInventory(null, size, plugin.utils.setColors(title));

            this.content_slots_length = slots.length;
            this.content_length = getContents().size();

            Update(0);


        } catch (Exception ex) {
            ex.printStackTrace();

            gui = Bukkit.createInventory(null, 9, plugin.utils.setColors("&cError"));
        }
    }

    public void Update(int page) {
        current_page = page;
        clearContents();
        setContents(page);
        setPersistentItems();
    }

    public void smartUpdate(int page) {
        current_page = page;

        List<MPGContentItem> contentItems = getContents();
        if (contentItems.isEmpty()) return;
        int item_index = content_slots_length * page;

        for (int i = 0; i < content_slots_length; i++) {
            if (item_index >= content_length) break;
            ItemStack oldItem = gui.getItem(i);
            ItemStack newItem = contentItems.get(item_index).getItemStack();

            if (oldItem != newItem) {
                gui.setItem(slots[i], newItem);
            }

            item_index++;
        }

        setPersistentItems(true);
    }

    @SuppressWarnings("deprecation")
    private boolean compareItems(ItemStack oldItem, ItemStack newItem) {

        if (oldItem == null) return true;
        if (newItem == null) return true;

        ItemMeta oldMeta = oldItem.getItemMeta();
        String oldName = oldMeta.getDisplayName();
        List<String> oldLore = oldMeta.getLore();
        int oldCMD = oldMeta.getCustomModelData();
        Material oldMaterial = oldItem.getType();

        ItemMeta newMeta = newItem.getItemMeta();
        String newName = newMeta.getDisplayName();
        List<String> newLore = newMeta.getLore();
        int newCMD = newMeta.getCustomModelData();
        Material newMaterial = newItem.getType();

        if (!oldName.equalsIgnoreCase(newName)) return true;
        if (oldLore != newLore) return true;
        if (oldCMD != newCMD) return true;
        if (oldMaterial != newMaterial) return true;

        return false;
    }

    private void clearContents() {
        for (int slot : slots) {
            gui.setItem(slot, new ItemStack(Material.AIR));
        }
    }

    public void setContents(int page) {

        List<MPGContentItem> contentItems = getContents();

        if (contentItems.isEmpty()) return;
        int item_index = content_slots_length * page;
        for (int i = 0; i < content_slots_length; i++) {
            if (item_index >= content_length) break;
            gui.setItem(slots[i], contentItems.get(item_index).getItemStack());
            item_index++;
        }
    }

    public void setPersistentItems() {
        setPersistentItems(false);
    }

    public void setPersistentItems(boolean smart) {

        int max_page = Math.max(0, (int)Math.ceil((double) content_length / content_slots_length) - 1);
        List<MPGContentItem> replacedArrows = getReplacedArrows();

        for (MPGContentItem item : getPersistentItems()) {

            if (smart) {
                if (gui.getItem(item.getSlot()) == item.getItemStack()) continue;
            }

            String id = item.getId();

            if ((current_page == 0 && id.equalsIgnoreCase("previous"))) {
                if (mode == MPGuiMode.REPLACE_BUTTONS) {
                    gui.setItem(replacedArrows.get(0).getSlot(), replacedArrows.get(0).getItemStack());
                }
                continue;
            }
            if ((current_page == max_page && id.equalsIgnoreCase("next"))) {
                if (mode == MPGuiMode.REPLACE_BUTTONS) {
                    gui.setItem(replacedArrows.get(1).getSlot(), replacedArrows.get(1).getItemStack());
                }
                continue;
            }
            if (getContents().isEmpty()) {
                if (id.equalsIgnoreCase("previous")) {
                    if (mode == MPGuiMode.REPLACE_BUTTONS) {
                        gui.setItem(replacedArrows.get(0).getSlot(), replacedArrows.get(0).getItemStack());
                    }
                    continue;
                }
                else if (id.equalsIgnoreCase("next")) {
                    if (mode == MPGuiMode.REPLACE_BUTTONS) {
                        gui.setItem(replacedArrows.get(1).getSlot(), replacedArrows.get(1).getItemStack());
                    }
                    continue;
                }
            }
            gui.setItem(item.getSlot(), item.getItemStack());
        }
    }

    public int getPage() {
        return current_page;
    }

    public void Open(int page, boolean update) {
        try {

            createInventory();

            Update(page);
            p.openInventory(gui);

            if (update) Runnable();

            mpgManager.setGui(p, this);

            plugin.sounds.clientSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void Runnable() {
        new BukkitRunnable() {

            @Override
            public void run() {
                try {

                    if (p.isDead() || !p.isValid() || !p.isOnline()) {
                        this.cancel();
                        return;
                    }

                    Update(getPage());

                } catch (Exception ex) {
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, 2);
    }

    public void Close() {
        p.closeInventory();
        mpgManager.clearGui(p);
        plugin.sounds.clientSound(p, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }

    @SuppressWarnings("unused")
    public void clickButton(String id, ClickType clickType, int page) {}
}
