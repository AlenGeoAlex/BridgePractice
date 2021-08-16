package me.alen_alex.bridgepractice.utility;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.citizens.CitizenNPC;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class CitizensUtility {

    public static int spawnCitizenNPC(Location location, String name){
        if(BridgePractice.getCitizensRegistry() == null)
            return 0;
        NPC npc = BridgePractice.getCitizensRegistry().createNPC(EntityType.VILLAGER,"");
        npc.setName("");
        npc.setAlwaysUseNameHologram(false);
        npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false);
        npc.setProtected(true);
        npc.setUseMinecraftAI(false);
        npc.spawn(location);
        npc.faceLocation(location);
        return npc.getId();
    }

    public static List<CitizenNPC> loadNPCFromGroup(String name){
        List<CitizenNPC> cacheNpc = new ArrayList<CitizenNPC>();
        if(GroupConfiguration.getGroupConfigurations().contains(name+".npc")){
            for(int i =1; i<= 10; i++){
                if(GroupConfiguration.getGroupConfigurations().contains(name+".npc."+String.valueOf(i))){
                    cacheNpc.add(new CitizenNPC(GroupConfiguration.getGroupConfigurations().getInt(name+".npc."+String.valueOf(i)+".npcID"),GroupConfiguration.getGroupConfigurations().getStringList(name+".npc."+String.valueOf(i)+".hologram"),i,GroupConfiguration.getGroupConfigurations().getInt(name+".npc."+String.valueOf(i)+".hologramOffset-Y"),GroupConfiguration.getGroupConfigurations().getBoolean(name+".npc."+String.valueOf(i)+".hologram-enabled")));
                }
            }
        }
        cacheNpc.forEach((npc) -> {
            System.out.println(npc.getNpcID());
        });
        return cacheNpc;
    }


}
