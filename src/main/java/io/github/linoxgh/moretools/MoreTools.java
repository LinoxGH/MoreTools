package io.github.linoxgh.moretools;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.linoxgh.moretools.items.CrescentHammer;
import io.github.linoxgh.moretools.listeners.PlayerListener;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;

public class MoreTools extends JavaPlugin implements SlimefunAddon {

    private static MoreTools instance;
    
    private final File configFile = new File(getDataFolder().getAbsolutePath() + File.separator + "config.yml");
    
    private Category moreToolsCategory;
    private boolean debug = true;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        FileConfiguration cfg = getConfig();
        debug = cfg.getBoolean("options.debugging", true);
        
        String version = getDescription().getVersion();
        if (version.startsWith("DEV")) {
            String cfgVersion = cfg.getString("version");
            
            Configuration defaultCfg = getConfig().getDefaults();
            if (cfgVersion == null || !cfgVersion.equals(version)) {
            
                getLogger().log(Level.WARNING, "Your config.yml file is outdated. Updating...");
                
                for (String key : defaultCfg.getKeys(true)) {
                    if (!cfg.contains(key, true)) {
                        if (debug) getLogger().log(Level.INFO, "Setting \"" + key + "\" to \"" + defaultCfg.get(key) + "\".");
                        cfg.set(key, defaultCfg.get(key));
                    }
                }
                cfg.set("version", version);
                
                getLogger().log(Level.INFO, "Finished updating config.yml file. Now saving...");
                
                try {
                    cfg.save(configFile);
                    getLogger().log(Level.INFO, "Saved config.yml file.");
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Failed saving config.yml file.", e);
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
            }
            
            if (cfg.getBoolean("options.auto-update")) {
                Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "LinoxGH/MoreTools/build");
                updater.start();
            }
        }
      
        if (debug) getLogger().log(Level.INFO, "Setting up metrics...");
        Metrics metrics = new Metrics(this, 8780);
        
        if (debug) getLogger().log(Level.INFO, "Setting up event listeners...");
        new PlayerListener(this);
        
        setupCategories();
        setupItems();
        setupResearches();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
    
    private void setupCategories() {
        if (debug) getLogger().log(Level.INFO, "Setting up categories...");
        
        moreToolsCategory = new Category(new NamespacedKey(this, "more_tools_category"), new CustomItem(Items.CRESCENT_HAMMER, "&3More Tools"), 4);
    }
    
    private void setupItems() {
        if (debug) getLogger().log(Level.INFO, "Setting up items...");
        
        new CrescentHammer(moreToolsCategory, Items.CRESCENT_HAMMER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.TIN_INGOT, null, SlimefunItems.TIN_INGOT,
            null, SlimefunItems.COPPER_INGOT, null,
            null, SlimefunItems.TIN_INGOT, null
        }).register(this);
        
    }
    
    private void setupResearches() {
        if (debug) getLogger().log(Level.INFO, "Setting up researches...");
        
        registerResearch("crescent_hammer", 7501, "Not A Hammer", 15, Items.CRESCENT_HAMMER);
    }
    
    private void registerResearch(String key, int id, String name, int defaultCost, ItemStack... items) {
        Research research = new Research(new NamespacedKey(this, key), id, name, defaultCost);
        
        for (ItemStack item : items) {
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null) {
                research.addItems(sfItem);
            }
        }
        research.register();
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/LinoxGH/MoreTools/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
    
    public static MoreTools getInstance() {
        return instance;
    }
    
    public boolean debug() {
        return debug;
    }
    
}
