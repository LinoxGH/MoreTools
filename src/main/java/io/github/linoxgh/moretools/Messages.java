package io.github.linoxgh.moretools;

import org.bukkit.ChatColor;

import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;

public enum Messages {

    CRESCENTHAMMER_BLOCKBREAKING("crescent-hammer.block-breaking"),
    CRESCENTHAMMER_RIGHTCLICKFAIL("crescent-hammer.right-click-fail");
    
    private final String message;
    
    Messages(String path) {
        Config cfg = MoreTools.getInstance().getCfg();
        message = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages." + path));
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
    
}