package io.github.linoxgh.moretools.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.linoxgh.moretools.MoreTools;
import io.github.linoxgh.moretools.handlers.ItemInteractHandler;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.Slimefun;

public class PlayerListener implements Listener {

    public PlayerListener(MoreTools plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        SlimefunItem sfItem = SlimefunItem.getByItem(e.getItem());
        if (sfItem != null && Slimefun.isEnabled(e.getPlayer(), sfItem, true)) {
            
            if (!Slimefun.hasUnlocked(e.getPlayer(), sfItem, true)) {
                e.setCancelled(true);
            } else {
                sfItem.callItemHandler(ItemInteractHandler.class, handler -> handler.onInteract(e));
            }
        }
    }
}
