package me.alen_alex.bridgepractice.configurations;

import de.leonhard.storage.Yaml;
import me.alen_alex.bridgepractice.BridgePractice;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenaConfigurations {

    private static BridgePractice plugin = BridgePractice.getPlugin();
    private static YamlConfiguration arenaConfiguration;
    private static File arenaFolder = new File(plugin.getDataFolder()+File.separator+"ArenaData");
    private static File arenaFile = new File(plugin.getDataFolder()+File.separator+"ArenaData","arenaData.yml");

    public static void createArenaConfigurations(){
        if(!arenaFolder.exists())
            arenaFolder.mkdirs();

        if(!arenaFile.exists()){
            plugin.getLogger().info("Arena data storage seems to be missing...Generating one");
            try {
                arenaFile.createNewFile();
                plugin.getLogger().info("Arena data storage has been successfully generated");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        arenaConfiguration = YamlConfiguration.loadConfiguration(arenaFile);
    }

    public static YamlConfiguration getArenaConfiguration() {
        return arenaConfiguration;
    }

    public static void saveArenaConfiguration(){
        try {
            arenaConfiguration.save(arenaFile);
            Bukkit.getLogger().info("Successfully saved the arena configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
