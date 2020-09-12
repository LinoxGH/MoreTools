package io.github.linoxgh.moretools.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemHandler;

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
     *            The {@link PlayerInteractEvent} that was triggered
     */
    void onInteract(PlayerInteractEvent e);

    @Override
    default Class<? extends ItemHandler> getIdentifier() {
        return ItemInteractHandler.class;
    }

}
