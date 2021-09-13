package me.alen_alex.bridgepractice.citizens;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.utility.Messages;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CitizenNPC {

    private int npcID;
    private List<String> holoContent;
    private NPC npc;
    private Hologram hologram;
    private Location hologramLocation;
    private int position,holoYOffset;
    private String playerName;
    private boolean holoEnabled;

    public CitizenNPC(@NotNull int npcID, List<String> holoContent, @NotNull int position,int holoYOffset,@NotNull boolean holoEnabled) {
        this.npcID = npcID;
        this.holoContent = holoContent;
        this.position = position;
        this.holoEnabled = holoEnabled;
        if(holoYOffset != 0)
            this.holoYOffset = holoYOffset;
        else
            this.holoYOffset = 3;
        this.hologramLocation = null;
        this.hologram = null;
        this.npc = null;
        playerName = null;
    }

    public List<String> getHoloContent() {
        return holoContent;
    }

    public void setHoloContent(List<String> holoContent) {
        this.holoContent = holoContent;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getNpcID() {
        return npcID;
    }

    public NPC getNpc() {
        return npc;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public Location getHologramLocation() {
        return hologramLocation;
    }

    public int getPosition() {
        return position;
    }

    public boolean isHoloEnabled() {
        return holoEnabled;
    }

    public void connectNPC(){
        this.npc = BridgePractice.getCitizensRegistry().getById(this.npcID);
        if(npc == null){
            BridgePractice.getPlugin().getLogger().warning("Failed to load NPC with the id "+npcID+". May be you have deleted the NPC");
            return;
        }
        npc.setProtected(false);
        npc.setAlwaysUseNameHologram(false);
        npc.setFlyable(false);
        npc.setUseMinecraftAI(false);
        if(holoEnabled) {
            if(BridgePractice.isHologramsEnabled()) {
                hologramLocation = npc.getStoredLocation();
                hologramLocation.setY(hologramLocation.getY() + this.holoYOffset);
                hologram = HologramsAPI.createHologram(BridgePractice.getPlugin(), hologramLocation);
                if (this.playerName == null || this.playerName.equalsIgnoreCase(Messages.parseColor(MessageConfiguration.getPlaceholderNA()))) {
                    this.hologram.appendTextLine(Messages.parseColor(MessageConfiguration.getHologramUnclaimed()));
                } else {
                    if(!holoContent.isEmpty()){
                        holoContent.forEach((string) -> {
                            if(string.startsWith("[ITEM]")){
                                String dataReq[] = string.split(";");
                                if(Material.matchMaterial(dataReq[1]) != null){
                                    hologram.appendItemLine(new ItemStack(Material.getMaterial(dataReq[1])));
                                }else
                                    Bukkit.getLogger().severe("Unknown MATERIAL on item "+dataReq[1]+". Failed to register Holograms for NPC.");
                            }else
                                hologram.appendTextLine(Messages.parseColor(string));
                        });
                    }
                }
            }
        }
        //UP ABOVE HOLOGRAMS

        if (this.playerName == null || this.playerName.equalsIgnoreCase(Messages.parseColor(MessageConfiguration.getPlaceholderNA()))) {
            this.npc.setBukkitEntityType(EntityType.VILLAGER);
        }else{
            this.npc.setBukkitEntityType(EntityType.PLAYER);
            SkinTrait skin = this.npc.getTrait(SkinTrait.class);
            skin.setSkinName(this.playerName, true);
        }
        Bukkit.getLogger().info("Successfully connected NPC with id"+this.npcID+".");
    }

    public void updateNPC(){
        Bukkit.getScheduler().runTask(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(holoEnabled) {
                    if(BridgePractice.isHologramsEnabled()) {
                        hologram.clearLines();
                        if (playerName == null || playerName.equalsIgnoreCase(Messages.parseColor(MessageConfiguration.getPlaceholderNA()))) {
                            hologram.appendTextLine(Messages.parseColor(MessageConfiguration.getHologramUnclaimed()));
                        } else {
                            if(!holoContent.isEmpty()){
                                holoContent.forEach((string) -> {
                                    if(string.startsWith("[ITEM]")){
                                        String dataReq[] = string.split(";");
                                        if(Material.matchMaterial(dataReq[1]) != null){
                                            hologram.appendItemLine(new ItemStack(Material.getMaterial(dataReq[1])));
                                        }else
                                            Bukkit.getLogger().severe("Unknown MATERIAL on item "+dataReq[1]+". Failed to register Holograms for NPC.");
                                    }else
                                        hologram.appendTextLine(Messages.parseColor(string));
                                });
                            }
                        }
                    }
                }
                if (playerName == null || playerName.equalsIgnoreCase(Messages.parseColor(MessageConfiguration.getPlaceholderNA()))) {
                    npc.setBukkitEntityType(EntityType.VILLAGER);
                    npc.setFlyable(false);
                    npc.setUseMinecraftAI(false);
                }else{
                    npc.setBukkitEntityType(EntityType.PLAYER);
                    SkinTrait skin = npc.getTrait(SkinTrait.class);
                    skin.setSkinName(playerName, true);
                    npc.setFlyable(false);
                    npc.setUseMinecraftAI(false);
                }
            }
        });
    }
}
