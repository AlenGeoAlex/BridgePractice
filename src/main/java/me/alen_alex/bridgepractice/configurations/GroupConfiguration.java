package me.alen_alex.bridgepractice.configurations;

import me.alen_alex.bridgepractice.BridgePractice;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GroupConfiguration {

    private static BridgePractice plugin = BridgePractice.getPlugin();
    private static YamlConfiguration groupConfigurations;
    private static File groupFolder = new File(plugin.getDataFolder()+File.separator+"Groups");
    private static File groupFile = new File(plugin.getDataFolder()+File.separator+"Groups","groupData.yml");

    public static void createGroupConfigurations(){
        if(!groupFolder.exists())
            groupFolder.mkdirs();

        if(!groupFile.exists()){
            plugin.getLogger().info("Group config seems to be missing...Generating one");
            try {
                groupFile.createNewFile();
                plugin.getLogger().info("Group config has been successfully generated");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        groupConfigurations = YamlConfiguration.loadConfiguration(groupFile);
    }

    public static YamlConfiguration getGroupConfigurations() {
        return groupConfigurations;
    }

    public static void saveGroupConfigurations(){
        try {
            groupConfigurations.save(groupFile);
            Bukkit.getLogger().info("Successfully saved the arena configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
