package io.github.linoxgh.moretools;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class Items {

    private static final FileConfiguration cfg = MoreTools.getInstance().getConfig();
    
    // TOOLS
    public static final SlimefunItemStack CRESCENT_HAMMER = new SlimefunItemStack("CRESCENT_HAMMER", Material.IRON_HOE, "&bCrescent Hammer", "&7&oActually this is a wrench, really.", "", "&eLeft Click &7> Dismantles the machine.");
    
    static {
        ItemMeta meta = CRESCENT_HAMMER.getItemMeta();
        List<String> lore = meta.getLore();
        
        if (cfg.getBoolean("item-settings.crescent-hammer.features.enable-rotation")) {
            lore.add(3, ChatColor.YELLOW + "Right Click" + ChatColor.GRAY + " > Rotates the block, if it's rotatable.");
        }
        
        if (cfg.getBoolean("item-settings.crescent-hammer.features.enable-channel-change")) {
            lore.add(4, ChatColor.YELLOW + "Shift + Left Click" + ChatColor.GRAY + " > Increases the channel of a cargo node.");
            lore.add(5, ChatColor.YELLOW + "Shift + Right Click" + ChatColor.GRAY + " > Decreases the channel of a cargo node.");
        }
        
        meta.setLore(lore);
        CRESCENT_HAMMER.setItemMeta(meta);
    }
}
