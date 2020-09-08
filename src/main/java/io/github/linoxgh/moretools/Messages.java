package io.github.limoxgh.moretools;

import org.bukkit.ChatColor;

import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;

public enum Messages {

    ITEMS$CRESCENT_HAMMER$BLOCK_BREAKING,
    ITEMS$CRESCENT_HAMMER$RIGHT_CLICK_FAIL;
    
    private final String message;
    
    Messages() {
        Config cfg = MoreTools.getInstance().getCfg();
        message = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages" + name().toLowerCase().replace('_', '-').replace('$', '.')));
    }
    
    public String getMessage() {
        return message;
    }
    
}