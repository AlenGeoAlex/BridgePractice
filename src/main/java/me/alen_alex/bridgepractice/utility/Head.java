package me.alen_alex.bridgepractice.utility;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class Head {

    public static ItemStack getHead(final String base64) {

        final ItemStack head = new ItemStack(Material.SKULL_ITEM);
        final SkullMeta meta = (SkullMeta) head.getItemMeta();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));
        final Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (final IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return new ItemStack(Material.SKULL_ITEM);
        }

        head.setItemMeta(meta);
        return head;
    }

    public static ItemStack getPlayerHead(final UUID uuid) {
        final ItemStack head = new ItemStack(Material.SKULL_ITEM);
        final SkullMeta skullMeta = (SkullMeta) (head.hasItemMeta() ? head.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM));
        skullMeta.setOwner(Bukkit.getOfflinePlayer(uuid).getName());
        head.setItemMeta(skullMeta);
        return head;
    }

    public static ItemStack getOnlinePlayer(String name) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwner(name);
        return item;
    }

}
