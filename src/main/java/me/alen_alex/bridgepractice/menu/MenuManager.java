package me.alen_alex.bridgepractice.menu;

import me.Abhigya.core.menu.ItemMenu;
import me.Abhigya.core.menu.action.ItemClickAction;
import me.Abhigya.core.menu.item.action.ActionItem;
import me.Abhigya.core.menu.item.action.ItemAction;
import me.Abhigya.core.menu.item.action.ItemActionPriority;
import me.Abhigya.core.particle.ParticleEffect;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Blocks;
import me.alen_alex.bridgepractice.utility.Head;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.PlayerParticles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private static final ItemStack BARRIER = new ItemStack(Material.BARRIER);
    private static final ItemStack CLAYBALL = new ItemStack(Material.CLAY_BALL);
    private static final ItemStack CHEST = new ItemStack(Material.CHEST);
    private static final ItemStack BOOK = new ItemStack(Material.BOOK);
    private static final ItemStack NAMETAG = new ItemStack(Material.NAME_TAG);
    private static final ArrayList<String> CLAYBALLLORE = new ArrayList<String>(){{
        add("");
        add(Messages.parseColor("&aEnabling setbacks &7will allow players"));
        add(Messages.parseColor("&7to teleport back when the timer runs up!"));
        add(Messages.parseColor("&7This will help in hardcore practice sessions"));
        add("");
        add(Messages.parseColor("&cDisabling setbacks &7will allow players"));
        add(Messages.parseColor("&fto continue bridging even if the timer runs up!"));
    }};

    private static final ArrayList<String> CHESTLORE = new ArrayList<String>(){{
        add("");
        add(Messages.parseColor("&eClear currently set timer"));
    }};


    public static void openMaterialMenu(Player player) {
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            Messages.sendMessage(player,"&cYou cannot set this with the timer on!", true);
            return;
        }
        ItemMenu menu = BridgePractice.getMaterialMenu();
        menu.clear();
        Bukkit.getScheduler().runTaskAsynchronously(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
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
        });
    }

    public static void openParticleMenu(Player player){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            Messages.sendMessage(player,"&cYou cannot set this with the timer on!", true);
            return;
        }
        ItemMenu menu = BridgePractice.getParticleMenu();
        menu.clear();
        Bukkit.getScheduler().runTaskAsynchronously(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
                List<ParticleEffect> playerParticle = PlayerParticles.getPlayersParticle(player);
                ActionItem[] items = new ActionItem[playerParticle.size()];
                ParticleEffect currentEffect = PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerParticle();
                for(int i = 0; i<playerParticle.size();i++){
                    ParticleEffect currentParticle = playerParticle.get(i);
                    if(currentEffect == currentParticle){
                        items[i] = new ActionItem(NAMETAG);
                        items[i].setName(Messages.parseColor("&a&l"+currentParticle.name()));
                    }else{
                        items[i] = new ActionItem(BOOK);
                        items[i].setName(Messages.parseColor("&e"+currentParticle.name()));
                        items[i].addAction(new ItemAction() {
                            @Override
                            public ItemActionPriority getPriority() {
                                return ItemActionPriority.NORMAL;
                            }

                            @Override
                            public void onClick(ItemClickAction itemClickAction) {
                                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerParticle(currentParticle);
                                Messages.sendMessage(player,"&aYour particle has been set to &b&l"+currentParticle.name(),false);
                                menu.close(player);
                            }
                        });
                    }
                }
                menu.setContents(items);
                menu.open(player);
            }
        });
    }

    public static void openSpectatorMenu(Player player){
        //TODO DON"T SPECTATE IF PLAYERSTATE IS NOT NULL
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
            if(PlayerDataManager.getCachedPlayerData().get(currentPlayers.get(i).getUniqueId()).CanOthersSpectate()) {
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



    public static void openTimerMenu(Player player){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            Messages.sendMessage(player,"&cYou can't use this command while timer is on!", false);
            return;
        }
        ItemMenu timerMenu = BridgePractice.getTimerMenu();
        timerMenu.clear();
        ActionItem[] items = new ActionItem[9];
        items[0] = new ActionItem(Blocks.getColoredWools(0));
        items[0].setName(Messages.parseColor("&eSet timer to &c&l25 secs"));
        items[0].addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {
                return  ItemActionPriority.NORMAL;
            }

            @Override
            public void onClick(ItemClickAction itemClickAction) {
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(25);
                Messages.sendMessage(player,"&aYour player timer has been set to 25", false);
                timerMenu.close(player);
            }
        });

        items[1] = new ActionItem(Blocks.getColoredWools(4));
        items[1].setName(Messages.parseColor("&eSet timer to &c&l30 secs"));
        items[1].addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {
                return  ItemActionPriority.NORMAL;
            }

            @Override
            public void onClick(ItemClickAction itemClickAction) {
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(30);
                Messages.sendMessage(player,"&aYour player timer has been set to 30", false);
                timerMenu.close(player);
            }
        });

        items[2] = new ActionItem(Blocks.getColoredWools(1));
        items[2].setName(Messages.parseColor("&eSet timer to &c&l35 secs"));
        items[2].addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {
                return  ItemActionPriority.NORMAL;
            }

            @Override
            public void onClick(ItemClickAction itemClickAction) {
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(35);
                Messages.sendMessage(player,"&aYour player timer has been set to 35", false);
                timerMenu.close(player);
            }
        });

        items[3] = new ActionItem(Blocks.getColoredWools(6));
        items[3].setName(Messages.parseColor("&eSet timer to &c&l40 secs"));
        items[3].addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {
                return  ItemActionPriority.NORMAL;
            }

            @Override
            public void onClick(ItemClickAction itemClickAction) {
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(40);
                Messages.sendMessage(player,"&aYour player timer has been set to 40", false);
                timerMenu.close(player);
            }
        });

        items[4] = new ActionItem(Blocks.getColoredWools(5));
        items[4].setName(Messages.parseColor("&eSet timer to &c&l45 secs"));
        items[4].addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {
                return  ItemActionPriority.NORMAL;
            }

            @Override
            public void onClick(ItemClickAction itemClickAction) {
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(45);
                Messages.sendMessage(player,"&aYour player timer has been set to 45", false);
                timerMenu.close(player);
            }
        });

        items[5] = new ActionItem(Blocks.getColoredWools(14));
        items[5].setName(Messages.parseColor("&eSet timer to &c&l50 secs"));
        items[5].addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {
                return  ItemActionPriority.NORMAL;
            }

            @Override
            public void onClick(ItemClickAction itemClickAction) {
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(50);
                Messages.sendMessage(player,"&aYour player timer has been set to 50", false);
                timerMenu.close(player);
            }
        });

        items[6] = new ActionItem(BARRIER);
        items[6].setName("");
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerTimer() > 1) {
            items[7] = new ActionItem(CHEST);
            items[7].setName(Messages.parseColor("&cClear timer"));
            items[7].setLore(CHESTLORE);
            items[7].addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.HIGH;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(-1);
                    Messages.sendMessage(player,"&cYour timer has been cleared",false);
                    timerMenu.close(player);
                }
            });
        }else{
            items[7] = new ActionItem(BARRIER);
            items[7].setName("");
        }
        items[8] = new ActionItem(CLAYBALL);
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isSetbackEnabled())
            items[8].setName(Messages.parseColor("&cDisable Setback"));
        else
            items[8].setName(Messages.parseColor("&aEnable Setback"));
        items[8].setLore(CLAYBALLLORE);
        items[8].addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {
                return ItemActionPriority.NORMAL;
            }

            @Override
            public void onClick(ItemClickAction itemClickAction) {
                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isSetbackEnabled()) {
                    PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setSetbackEnabled(false);
                    Messages.sendMessage(player, "&eYour setback has been disabled", false);
                }else{
                    PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setSetbackEnabled(true);
                    Messages.sendMessage(player, "&aYour setback has been enabled", false);
                }
                timerMenu.close(player);
            }
        });
        timerMenu.setContents(items);
        timerMenu.open(player);
    }



}
