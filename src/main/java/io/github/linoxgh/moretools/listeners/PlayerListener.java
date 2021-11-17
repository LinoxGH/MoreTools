package io.github.linoxgh.moretools.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.linoxgh.moretools.MoreTools;
import io.github.linoxgh.moretools.handlers.ItemInteractHandler;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

public class PlayerListener implements Listener {

    public PlayerListener(MoreTools plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (SlimefunUtils.canPlayerUseItem(e.getPlayer(), e.getItem(), true)) {
            SlimefunItem sfItem = SlimefunItem.getByItem(e.getItem());
            if (sfItem == null) return;
            sfItem.callItemHandler(ItemInteractHandler.class, handler -> handler.onInteract(e, sfItem));
        }
    }
}
