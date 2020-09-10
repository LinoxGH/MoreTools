package io.github.linoxgh.moretools;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class Items {
    
    // TOOLS
    public static final SlimefunItemStack CRESCENT_HAMMER = new SlimefunItemStack("CRESCENT_HAMMER", Material.IRON_HOE, "&bCrescent Hammer", "&7&oActually this is a wrench, really.", "", "&eLeft Click &7> Dismantles the machine.");
    static {
        Config cfg = MoreTools.getInstance().getCfg();
        ItemMeta meta = CRESCENT_HAMMER.getItemMeta();
        List<String> lore = meta.getLore();
        
        if (cfg.getBoolean("item-settings.crescent-hammer.features.enable-rotation")) {
            lore.set(3, "&eRight Click &7> Rotates the block, if it's rotatable.");
        }
        
        if (cfg.getBoolean("item-settings.crescent-hammer.features.enable-channel-change")) {
            lore.set(4, "&eShift + Left Click &7> Increases the channel of a cargo node.");
            lore.set(5, "&eShift + Right Click &7> Decreases the channel of a cargo node.");
        }
        
        meta.setLore(lore);
        CRESCENT_HAMMER.setItemMeta(meta);
    }
}