package me.alen_alex.bridgepractice.configurations;

import de.leonhard.storage.Yaml;
import me.alen_alex.bridgepractice.BridgePractice;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BlockConfiguration {


    private static BridgePractice plugin = BridgePractice.getPlugin();
    private static YamlConfiguration blocksConfigurations;
    private static File blocksFolder = new File(plugin.getDataFolder()+File.separator+"Blocks");
    private static File blocksFile = new File(plugin.getDataFolder()+File.separator+"Blocks","blockData.yml");

    public static void createBlockConfigurations(){
        if(!blocksFolder.exists())
            blocksFolder.mkdirs();

        if(!blocksFile.exists()){
            plugin.getLogger().info("Block config seems to be missing...Generating one");
            try {
                blocksFile.createNewFile();
            new Yaml("blockData.yml","plugins/BridgePractice/Blocks",BridgePractice.getPlugin().getResource("blockData.yml"));
            plugin.getLogger().info("Block config has been successfully generated");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        blocksConfigurations = YamlConfiguration.loadConfiguration(blocksFile);
    }

    public static YamlConfiguration getBlocksConfigurations() {
        return blocksConfigurations;
    }
}
