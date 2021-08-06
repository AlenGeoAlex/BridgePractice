package me.alen_alex.bridgepractice.utility;

import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworkUtilities {

    private final static Random random = new Random();
    private static List<org.bukkit.Color> colorList = new ArrayList<org.bukkit.Color>(){{
        add(org.bukkit.Color.BLUE);
        add(org.bukkit.Color.LIME);
        add(org.bukkit.Color.OLIVE);
        add(org.bukkit.Color.WHITE);
        add(org.bukkit.Color.ORANGE);
        add(org.bukkit.Color.PURPLE);
        add(org.bukkit.Color.AQUA);
        add(org.bukkit.Color.BLACK);
        add(org.bukkit.Color.FUCHSIA);
        add(org.bukkit.Color.GRAY);
        add(org.bukkit.Color.GREEN);
        add(org.bukkit.Color.MAROON);
        add(org.bukkit.Color.NAVY);
        add(org.bukkit.Color.RED);
        add(org.bukkit.Color.SILVER);
        add(org.bukkit.Color.TEAL);
        add(org.bukkit.Color.YELLOW);
    }};

    private static List<FireworkEffect.Type> fireworkType = new ArrayList<FireworkEffect.Type>(){{
        add(FireworkEffect.Type.BALL);
        add(FireworkEffect.Type.BURST);
        add(FireworkEffect.Type.STAR);
        add(FireworkEffect.Type.CREEPER);
        add(FireworkEffect.Type.BALL_LARGE);
    }};

    public static org.bukkit.Color getRandomColor(){
        return colorList.get(random.nextInt(colorList.size()));
    }

    /*public static FireworkEffect.Type getRandomFireworkType(){
        return fireworkType.get(fireworkType.size());
    }*/

    public static List<FireworkEffect.Type> getFireworkType() {
        return fireworkType;
    }

    public static List<FireworkEffect.Type> getPlayerAvailableFirework(Player player){
        List<FireworkEffect.Type> availFirework = new ArrayList<FireworkEffect.Type>();
        availFirework.add(FireworkEffect.Type.BALL);
        fireworkType.forEach((firework) ->{
            if(!(firework == FireworkEffect.Type.BALL)) {
                if(player.hasPermission("practice.firework."+firework.name()))
                    availFirework.add(firework);
            }
        });
        return availFirework;
    }
}
