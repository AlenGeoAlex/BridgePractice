package me.alen_alex.bridgepractice.utility;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    }};

    public static List<Material> getBlock() {
        return block;
    }

    public static Material getRandomBlock(){
        return block.get(random.nextInt(block.size()));
    }



}
