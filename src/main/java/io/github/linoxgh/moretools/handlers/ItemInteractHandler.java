package io.github.linoxgh.moretools.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

/**
 * This {@link ItemHandler} is triggered when the {@link SlimefunItem} it was assigned to
 * is interacted with.
 * 
 * @author Linox
 *
 * @see ItemHandler
 * @see SimpleSlimefunItem
 * 
 */
@FunctionalInterface
public interface ItemInteractHandler extends ItemHandler {

    /**
     * This function is triggered when a {@link Player} interacts with the assigned {@link SlimefunItem}
     * in his hand.
     * 
     * @param e
     *                The {@link PlayerInteractEvent} that was triggered
     * @param sfItem
     *                The {@link SlimefunItem} the item that was used
     */
    void onInteract(PlayerInteractEvent e, SlimefunItem sfItem);

    @Override
    default Class<? extends ItemHandler> getIdentifier() {
        return ItemInteractHandler.class;
    }

}
