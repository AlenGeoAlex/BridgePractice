package me.alen_alex.bridgepractice.utility;

import me.alen_alex.bridgepractice.BridgePractice;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.*;

public class Blocks {

    final static Random random = new Random();

    final static List<Material> block = new ArrayList<Material>(){{
        add(Material.WOOL);
        add(Material.PRISMARINE);
        add(Material.SEA_LANTERN);
        add(Material.EMERALD_BLOCK);
        add(Material.DIAMOND_BLOCK);
        add(Material.GOLD_BLOCK);
        add(Material.REDSTONE_BLOCK);
        add(Material.NETHERRACK);
        add(Material.BRICK);
    }};

    final static HashMap<String, Material> materialName = new HashMap<String,Material>(){{
        put("wool",Material.WOOL);
        put("prismarine",Material.PRISMARINE);
        put("sealantern",Material.SEA_LANTERN);
        put("emeraldblock",Material.EMERALD_BLOCK);
        put("diamondblock",Material.DIAMOND_BLOCK);
        put("goldblock",Material.GOLD_BLOCK);
        put("redstoneblock",Material.REDSTONE_BLOCK);
        put("netherrack",Material.NETHERRACK);
        put("brick",Material.BRICK);
    }};

    public static List<Material> getBlock() {
        return block;
    }

    public static Material getRandomBlock(){
        return block.get(random.nextInt(block.size()));
    }

    public static boolean doPlayerHavePreferencePermission(Player player){
        return player.hasPermission("practice.preferenceblock");
    }

    public static List<Material> getAvailableBlocks(Player player){
        List<Material> availableMatrix = new ArrayList<>();
        availableMatrix.add(Material.WOOD);
        for(Map.Entry<String, Material> entry : materialName.entrySet()){
            if(player.hasPermission("practice.block"+entry.getKey())) {
                availableMatrix.add(entry.getValue());
            }
        }

        return availableMatrix;
    }

    public static void setResetBlocks(World world, int x, int y, int z, int blockId, byte data) {
        net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_8_R3.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(blockId + (data << 12));
        nmsChunk.a(bp, ibd);
    }

}