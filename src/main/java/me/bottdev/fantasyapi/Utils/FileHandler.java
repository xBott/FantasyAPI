package me.bottdev.fantasyapi.Utils;

import me.bottdev.fantasyapi.FantasyAPI;
import org.apache.commons.io.FileUtils;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;

public class FileHandler {

    public void copyDirectory(String sourcePath, String destinationPath) {
        try {
            File sourceDirectory = new File(sourcePath);
            File destinationDirectory = new File(destinationPath);
            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void copyModelEngineData() {
        try {

            copyDirectory("plugins/ModelEngine/resource pack/assets/minecraft", "plugins/ItemsAdder/data/resource_pack/assets/minecraft");
            copyDirectory("plugins/ModelEngine/resource pack/assets/modelengine", "plugins/ItemsAdder/data/resource_pack/assets/modelengine");

            ConsoleCommandSender console = FantasyAPI.getPlugin(FantasyAPI.class).getServer().getConsoleSender();
            console.sendMessage(" ");
            console.sendMessage("  ModelEngine data copied to ItemsAdder");
            console.sendMessage(" ");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
