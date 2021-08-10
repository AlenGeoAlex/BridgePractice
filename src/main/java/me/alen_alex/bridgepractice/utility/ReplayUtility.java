package me.alen_alex.bridgepractice.utility;

import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.jumper251.replay.filesystem.saving.ReplaySaver;
import me.jumper251.replay.replaysystem.Replay;
import me.jumper251.replay.utils.fetcher.Consumer;
import org.bukkit.entity.Player;

public class ReplayUtility {

    public static void playReplay(Player player, String replayName){
        if(!ReplaySaver.exists(replayName)){
            Messages.sendMessage(player,"&cError in fetching replay",false);
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() != null){
            Messages.sendMessage(player,"&cYou can't play replay while in a session!", false);
            return;
        }

        ReplaySaver.load(replayName, new Consumer<Replay>() {
            @Override
            public void accept(Replay replay) {
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setWatchingReplay(true);
                replay.play(player);
            }
        });



    }

    public static void deleteReplay(Player player, String replayName){
        if(!ReplaySaver.exists(replayName)){
            Messages.sendMessage(player,"&cError in fetching replay",false);
            return;
        }

        ReplaySaver.delete(replayName);
        Messages.sendMessage(player,"&cDeleted replay "+replayName,false);
    }

}
