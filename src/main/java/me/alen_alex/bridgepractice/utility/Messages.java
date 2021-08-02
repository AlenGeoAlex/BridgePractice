package me.alen_alex.bridgepractice.utility;

import me.alen_alex.bridgepractice.configurations.Configuration;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {
    public static String parseColor(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void sendMessage(Player player, String message, boolean showPrefix) {
        if (showPrefix)
            player.sendMessage(Configuration.getPrefixMain() + parseColor(message));
        else
            player.sendMessage(parseColor(message));
    }

    public static void sendMessage(CommandSender sender, String message, boolean showPrefix) {
        if (showPrefix)
            sender.sendMessage(Configuration.getPrefixMain() + parseColor(message));
        else
            sender.sendMessage(parseColor(message));
    }

    public static void sendMessageNoPrefix(Player player, String message) {
        player.sendMessage(parseColor(message));
    }

    public static void sendMessageNoPrefix(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public static void broadcastMessageNoPrefix(String message) {
        Bukkit.getServer().broadcastMessage(parseColor(message));
    }


    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(Configuration.getPrefixMain() + parseColor(message));
    }

    public static void sendJSONSuggestMessage(Player player, String Message, String SuggestionCommand, String HoverText, boolean ShowPrefix) {
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, SuggestionCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage((BaseComponent) tc);
    }

    public static void sendJSONSuggestMessage(CommandSender sender, String Message, String SuggestionCommand, String HoverText, boolean ShowPrefix) {
        if(!(sender instanceof Player))
            return;
        Player player = (Player)sender;
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, SuggestionCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage((BaseComponent) tc);
    }

    public static void sendJSONExecuteCommand(Player player, String Message, String RunCommand, String HoverText, boolean ShowPrefix) {

        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, RunCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage(((BaseComponent) tc));
    }

    public static void sendJSONLink(Player player, String Message, String redirectTo, String HoverText) {

        TextComponent tc = new TextComponent();
        tc.setText(parseColor(Message));
        tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, redirectTo));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage(((BaseComponent) tc));
    }

    public static void sendJSONExecuteCommand(CommandSender sender, String Message, String RunCommand, String HoverText, boolean ShowPrefix) {
        if(!(sender instanceof Player))
            return;
        Player player = (Player)sender;
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, RunCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage(((BaseComponent) tc));
    }

    public static void sendJSONHoverMessage(Player player, String Message, String HoverText, boolean ShowPrefix) {
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setText(Configuration.getPrefixMain() + parseColor(Message));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage((BaseComponent) tc);
    }

    public static void sendBroadcastMessage(String message, boolean showPrefix){
        if(showPrefix)
            Bukkit.getServer().broadcastMessage(Configuration.getPrefixMain()+parseColor(message));
        else
            Bukkit.getServer().broadcastMessage(parseColor(message));
    }

    public static void sendIncorrectUsage(Player player){
        TextComponent tc = new TextComponent();
        tc.setText(Configuration.getPrefixMain() + parseColor("&cIncorrect usage."));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor("&fClick here to show help message")).create())));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/practiceadmin help"));
        player.spigot().sendMessage((BaseComponent) tc);
    }

}