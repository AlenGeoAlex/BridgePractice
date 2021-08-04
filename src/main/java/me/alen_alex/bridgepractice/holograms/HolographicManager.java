package me.alen_alex.bridgepractice.holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.island.IslandManager;

import java.util.HashMap;
import java.util.Map;

public class HolographicManager {

private static HashMap<String, Holograms> holoData = new HashMap<String, Holograms>();

    public static void fetchHologramsFromIslands(){
        for(Map.Entry<String, Island> islandEntry : IslandManager.getIslandData().entrySet()){
            HolographicManager.getHoloData().put(islandEntry.getKey(),new Holograms(islandEntry.getValue().getName(),islandEntry.getValue().getSpawnLocation(), islandEntry.getValue().getEndLocation()));
            holoData.get(islandEntry.getKey()).createHolograms();
        }
    }

    public static HashMap<String, Holograms> getHoloData() {
        return holoData;
    }
}
