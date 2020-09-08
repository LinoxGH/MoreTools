package io.github.limoxgh.moretools;

import org.bukkit.ChatColor;

import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;

public enum Messages {

    ITEMS$CRESCENT_HAMMER$BLOCK_BREAKING,
    ITEMS$CRESCENT_HAMMER$RIGHT_CLICK_FAIL;
    
    private static Config cfg = null;
    
    private final String message;
    
    Messages() {
        message = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages" + name().toLowerCase().replace('_', '-').replace('$', '.')));
    }
    
    public String getMessage() {
        return message;
    }
    
    public static void setup(Config cfg) {
        Messages.cfg = cfg;
    }
}