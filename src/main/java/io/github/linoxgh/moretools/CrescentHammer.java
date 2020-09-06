package io.github.linoxgh.moretools.items;

import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.cscorelib2.protection.ProtectableAction;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack

/**
 * A {@link CrescentHammer} is a {@link SlimefunItem} which allows you to break placed machine blocks
 * with a single right click.
 *
 * @author Linox
 * 
 */
public class CrescentHammer extends SimpleSlimefunItem<ItemUseHandler> {

    public CrescentHammer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }
    
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            Optional<Block> block = e.getClickedBlock();
            if (block.isPresent()) {
                Block b = block.get();
                
                if (SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), b.getLocation(), ProtectableAction.BREAK_BLOCK)) {
                    
                   SlimefunItem sfItem = BlockStorage.check(b);
                   if (sfItem != null) {
                       if (sfItem instanceof EnergyNetComponent || sfItem.getID().startsWith("CARGO_NODE")) {
                       
                           BlockBreakEvent event = new BlockBreakEvent(b, p);
                           Bukkit.getPluginManager().callEvent(event);
                           b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType()); //TODO Make this configurable
                           damageItem(p, e.getItem()); //TODO Make this configurable
                       }
                   }
                   //TODO Send fail message.
                }
            }
            e.cancel();
        };
    }
    
    private BlockBreakHandler getBlockBreakHandler() {
        return new BlockBreakHandler() {
        
            @Override
            public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
                if (isItem(item)) {
                    e.setCancelled(true);
                    return true;
                }
                return false;
            }
            
            @Override
            public boolean isPrivate() {
                return false;
            }
        };
    }
    
    @Override
    public void preRegister() {
        super.preRegister();
        addItemHandler(getBlockBreakHandler());
    }
    
    @Override
    public boolean isDamageable() {
        return true;
    }
    
}