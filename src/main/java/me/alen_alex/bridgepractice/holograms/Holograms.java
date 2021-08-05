package me.alen_alex.bridgepractice.holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.placeholderapi.PlaceholderDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class Holograms {

    private String islandName;
    private Hologram startingHolo,endingHolo;
    private Location startingLocation,endLocation;

    public Holograms(@NotNull String islandName, @NotNull Location startingLocation,@NotNull Location endLocation) {
        this.islandName = islandName;
        this.startingLocation = startingLocation;
        this.endLocation = endLocation;
    }

    public void createHolograms(){
        if(!Configuration.getHologramsEndingLines().isEmpty() && Configuration.getHologramsStartingLines().isEmpty()){
            Bukkit.getLogger().severe("Aborting holo creations as those lines are empty");
            return;
        }
        startingLocation.setY(startingLocation.getBlockY() + Configuration.getHologramsOffsetY());
        endLocation.setY(endLocation.getBlockY() + Configuration.getHologramsOffsetY());
        startingHolo = HologramsAPI.createHologram(BridgePractice.getPlugin(),startingLocation);
        if(!Configuration.getHologramsStartingLines().isEmpty()){
            Configuration.getHologramsStartingLines().forEach(string ->{
                if(string.startsWith("[ITEM]")){
                    String dataReq[] = string.split(";");
                    if(Material.matchMaterial(dataReq[1]) != null){
                        startingHolo.appendItemLine(new ItemStack(Material.getMaterial(dataReq[1])));
                    }else
                        Bukkit.getLogger().severe("Unknown MATERIAL on item "+dataReq[1]+". Failed to register Holograms for starting position.");
                }else
                startingHolo.appendTextLine(Messages.parseColor(string));
            });
        }
        endingHolo = HologramsAPI.createHologram(BridgePractice.getPlugin(),endLocation);
        if(!Configuration.getHologramsEndingLines().isEmpty()){
            Configuration.getHologramsEndingLines().forEach(s -> {
                if(s.startsWith("[ITEM]")){
                    String dataReq[] = s.split(";");
                    if(Material.matchMaterial(dataReq[1]) != null){
                        startingHolo.appendItemLine(new ItemStack(Material.getMaterial(dataReq[1])));
                    }else
                        Bukkit.getLogger().severe("Unknown MATERIAL on item "+dataReq[1]+". Failed to register Holograms for starting position.");
                }else
                endingHolo.appendTextLine(Messages.parseColor(s));
            });
        }
        startingLocation.setY(startingLocation.getBlockY() - Configuration.getHologramsOffsetY());
        endLocation.setY(endLocation.getBlockY() - Configuration.getHologramsOffsetY());
    }

    public void deleteHolo(){
        if(startingHolo != null)
            startingHolo.delete();
        if(endingHolo != null)
            endingHolo.delete();
    }






}
