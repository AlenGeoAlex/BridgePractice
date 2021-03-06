package me.alen_alex.bridgepractice.utility;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.PlayerChunkMap;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            if(player.hasPermission("practice.block."+entry.getKey())) {
                availableMatrix.add(entry.getValue());
            }
        }

        return availableMatrix;
    }

    public static void setBlockToAir(World world, int x, int y, int z, int blockId, byte data) {
        net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_8_R3.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(blockId + (data << 12));
        nmsChunk.a(bp, ibd);
    }

    public void refreshPlayerChunk(Player player) {
        PlayerChunkMap playerChunkMap = ((CraftPlayer) player).getHandle().u().getPlayerChunkMap();
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        playerChunkMap.removePlayer(entityPlayer);
        playerChunkMap.addPlayer(entityPlayer);
    }

    public static void getColoredGlassPane(int data){
    }

    public static ItemStack getColoredWools(int data){
        ItemStack wool = new ItemStack(Material.WOOL,1, (short) 0,(byte) data);
        return wool;
    }

}