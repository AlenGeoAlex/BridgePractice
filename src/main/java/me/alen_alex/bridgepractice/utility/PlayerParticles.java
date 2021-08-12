package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.particle.particlelib.ParticleEffect;
import me.alen_alex.bridgepractice.BridgePractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerParticles {

    private static HashMap<String, ParticleEffect> playerEffect = new HashMap<String, ParticleEffect>();

    public static void loadAllAvailableEffectToCache(){
        ParticleEffect.getAvailableEffects().stream().filter(particleEffect -> particleEffect != ParticleEffect.MOB_APPEARANCE).forEach(particleEffect -> playerEffect.put(particleEffect.name(), particleEffect));
    }

    public static List<ParticleEffect> getPlayersParticle(Player player){
        List<ParticleEffect> returnEffect = new ArrayList<>();
        for(Map.Entry<String, ParticleEffect> effectEntry : playerEffect.entrySet()){
            if(effectEntry.getValue() == ParticleEffect.SMOKE_NORMAL) {
                returnEffect.add(ParticleEffect.SMOKE_NORMAL);
                continue;
            }
            if(player.hasPermission("practice.particle."+effectEntry.getKey())){
                returnEffect.add(effectEntry.getValue());
            }
        }
        return returnEffect;
    }

    public static HashMap<String, ParticleEffect> getCachedParticles() {
        return playerEffect;
    }
}
