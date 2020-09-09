package io.github.linoxgh.moretools.items;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.linoxgh.moretools.Messages;
import io.github.linoxgh.moretools.MoreTools;
import io.github.linoxgh.moretools.handlers.ItemInteractHandler;

import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.cargo.CargoManager;
import io.github.thebusybiscuit.slimefun4.implementation.items.cargo.ReactorAccessPort;
import io.github.thebusybiscuit.slimefun4.implementation.items.cargo.TrashCan;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.EnergyRegulator;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

/**
 * A {@link CrescentHammer} is a {@link SlimefunItem} which allows you to break placed machine blocks
 * with a single right click.
 *
 * @author Linox
 *
 * @see ItemInteractHandler
 * 
 */
public class CrescentHammer extends SimpleSlimefunItem<ItemInteractHandler> implements DamageableItem {

    private boolean damageable = true;
    //private List<String> blacklist = null;

    public CrescentHammer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        
        damageable = MoreTools.getInstance().getCfg().getBoolean("item-settings.crescent-hammer.damageable");
        //blacklist = MoreTools.getInstance().getCfg().getStringList("item-settings.crescent-hammer.rotation-blacklist");
    }
    
    @Override
    public ItemInteractHandler getItemHandler() {
        return e -> {
            Block b = e.getClickedBlock();
            if (b != null) {
                Player p = e.getPlayer();
                if (SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.BREAK_BLOCK)) {
                    
                    switch(e.getAction()) {
                        case RIGHT_CLICK_BLOCK:
                            if (p.isSneaking()) {
                                alterChannel(b, p, 1);
                            } else {
                                rotateBlock(b, p);
                            }
                            break;
                        
                        case LEFT_CLICK_BLOCK:
                            if (p.isSneaking()) {
                                alterChannel(b, p, -1);
                            } else {
                                dismantleBlock(b, p, e.getItem());
                            }
                            break;
                        
                        default:
                            break;
                    }
                }
            }
            e.setCancelled(true);
        };
    }
    
    private void alterChannel(Block b, Player p, int change) {
        SlimefunItem sfItem = BlockStorage.check(b);
        if (sfItem != null) {
            if (sfItem.getID().startsWith("CARGO_NODE")) {
            
                
            }
        }
        p.sendMessage(Messages.CRESCENTHAMMER_CHANNELCHANGEFAIL.getMessage());
    }
    
    private void dismantleBlock(Block b, Player p, ItemStack item) {
        SlimefunItem sfItem = BlockStorage.check(b);
        if (sfItem != null) {
            if (sfItem instanceof EnergyNetComponent || sfItem instanceof EnergyRegulator || sfItem.getID().startsWith("CARGO_NODE") || sfItem instanceof CargoManager || sfItem instanceof ReactorAccessPort || sfItem instanceof TrashCan) {
            
                BlockBreakEvent event = new BlockBreakEvent(b, p);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
                
                    if (isDamageable()) {
                        damageItem(p, item);
                    }
                    return;
                }
            }
        }
        p.sendMessage(Messages.CRESCENTHAMMER_DISMANTLEFAIL.getMessage());
    }
    
    private void rotateBlock(Block b, Player p) {
    
        //if (blacklist != null && !p.hasPermission("moretools.crescent-hammer.rotation-whitelist-bypass")) {
        //    if (blacklist.contains(b.getType().name())) {
        //        p.sendMessage(Messages.CRESCENTHAMMER_ROTATEFAIL.getMessage());
        //        return;
        //    }
        //}
        
        if (b.getBlockData() instanceof Directional) {
            Directional data = (Directional) b.getBlockData();
            BlockFace[] directions = data.getFaces().toArray(new BlockFace[0]);
            
            for (int i = 0; i < directions.length; i++) {
                if (data.getFacing() == directions[i]) {
                    i = (i == directions.length - 1) ? 0 : i + 1;
                    data.setFacing(directions[i]);
                    b.setBlockData(data, true);
                    
                    // Special management for cargo nodes.
                    if (b.getType() == Material.PLAYER_WALL_HEAD) {
                        SlimefunItem sfItem = BlockStorage.check(b);
                        if (sfItem != null) {
                            if (sfItem.getID().startsWith("CARGO_NODE")) {
                                SlimefunPlugin.getNetworkManager().updateAllNetworks(b.getLocation());
                            }
                        }
                    }
                    return;
                }
            }
        }
        p.sendMessage(Messages.CRESCENTHAMMER_ROTATEFAIL.getMessage());
    }
    
    private BlockBreakHandler getBlockBreakHandler() {
        return new BlockBreakHandler() {
        
            @Override
            public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
                if (isItem(item)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Messages.CRESCENTHAMMER_BLOCKBREAKING.getMessage());
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
        return damageable;
    }
    
}