package me.bottdev.fantasyapi;

import me.bottdev.fantasyapi.Utils.Attributes.AttributesUtils;
import me.bottdev.fantasyapi.Utils.CooldownManager;
import me.bottdev.fantasyapi.Utils.Gui.Default.GuiTemplateListener;
import me.bottdev.fantasyapi.Utils.Gui.MultiPageGui.MPGuiListener;
import me.bottdev.fantasyapi.Utils.Sounds.SoundUtils;
import me.bottdev.fantasyapi.Utils.Utils;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class FantasyAPI extends JavaPlugin {


    public static Utils utils;
    public SoundUtils sounds;
    public  ConsoleCommandSender console;

    @Override
    public void onEnable() {

        utils = new Utils();
        sounds = new SoundUtils();
        console = getServer().getConsoleSender();

        console.sendMessage(" ");
        console.sendMessage(" ");
        console.sendMessage(utils.setColors("&aFantasy API has been enabled!"));
        console.sendMessage(" ");
        console.sendMessage(" ");

        getServer().getPluginManager().registerEvents(new CooldownManager(), this);
        getServer().getPluginManager().registerEvents(new AttributesUtils(), this);
        getServer().getPluginManager().registerEvents(new MPGuiListener(), this);
        getServer().getPluginManager().registerEvents(new GuiTemplateListener(), this);

    }

    @Override
    public void onDisable() {

        console.sendMessage(" ");
        console.sendMessage(" ");
        console.sendMessage(utils.setColors("&cFantasy API has been disabled!"));
        console.sendMessage(" ");
        console.sendMessage(" ");

    }



}
