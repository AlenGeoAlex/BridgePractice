package me.alen_alex.bridgepractice.menu;

import me.Abhigya.core.menu.ItemMenu;
import me.Abhigya.core.menu.action.ItemClickAction;
import me.Abhigya.core.menu.item.action.ActionItem;
import me.Abhigya.core.menu.item.action.ItemAction;
import me.Abhigya.core.menu.item.action.ItemActionPriority;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Blocks;
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

}
