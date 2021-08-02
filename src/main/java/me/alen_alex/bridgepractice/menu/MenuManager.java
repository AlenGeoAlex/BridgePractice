package me.alen_alex.bridgepractice.menu;

import me.Abhigya.core.menu.ItemMenu;
import me.Abhigya.core.menu.action.ItemClickAction;
import me.Abhigya.core.menu.item.action.ActionItem;
import me.Abhigya.core.menu.item.action.ItemAction;
import me.Abhigya.core.menu.item.action.ItemActionPriority;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Blocks;
import me.alen_alex.bridgepractice.utility.Head;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuManager {

    public static void openMaterialMenu(Player player) {
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            Messages.sendMessage(player,"&cYou cannot set this with the timer on!", true);
            return;
        }
        ItemMenu menu = BridgePractice.getMaterialMenu();
        menu.clear();
        List<Material> availableBlocks = Blocks.getAvailableBlocks(player);
        ActionItem item[] = new ActionItem[availableBlocks.size()];
        for(int i = 0; i < availableBlocks.size();i++){
            ItemStack itemStack = new ItemStack(availableBlocks.get(i));
            item[i] = new ActionItem(itemStack);
            item[i].setName(Messages.parseColor("&6&lClick to select"));
            int finalI = i;
            item[i].addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.HIGH;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerMaterial(availableBlocks.get(finalI));
                    Messages.sendMessage(player,"&b&lYour material choice has been set", true);
                    if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.IDLE_ISLAND) {
                        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).fillPlayerBlocks();
                    }
                    menu.close(player);
                    return;
                }
            });
        }
        menu.setContents(item);
        menu.open(player);
    }

    public static void openSpectatorMenu(Player player){
        if(!(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).canSpectate())){
            Messages.sendMessage(player,"&cYou can't spectate players!", true);
            return;
        }
        List<Player> currentPlayers = Gameplay.getCurrentPlayes();
        if(currentPlayers == null){
            Messages.sendMessage(player,"&cThere are currently no players practicing the islands!!", true);
            return;
        }
        ItemMenu specMenu = BridgePractice.getSpectatorMenu();
        specMenu.clear();
        ActionItem item[] = new ActionItem[54];
        for(int i = 0;i<currentPlayers.size();i++){
            if(PlayerDataManager.getCachedPlayerData().get(currentPlayers.get(i)).CanOthersSpectate()) {
                item[i] = new ActionItem(Head.getOnlinePlayer(currentPlayers.get(i).getName()));
                item[i].setName(currentPlayers.get(0).getName());
                int finalI = i;
                item[i].addAction(new ItemAction() {
                    @Override
                    public ItemActionPriority getPriority() {
                        return ItemActionPriority.NORMAL;
                    }

                    @Override
                    public void onClick(ItemClickAction itemClickAction) {
                        System.out.println(1);
                        specMenu.close(player);
                    }
                });
            }
        }
        specMenu.setContents(item);
        specMenu.open(player);
    }

}
