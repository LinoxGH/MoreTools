package io.github.linoxgh.moretools;

import javax.annotation.Nonnull;
import org.bukkit.ChatColor;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;

public enum Messages {

    CRESCENTHAMMER_BLOCKBREAKING("items.crescent-hammer.block-breaking"),
    CRESCENTHAMMER_RIGHTCLICKFAIL("items.crescent-hammer.right-click-fail");
    
    private final String message;
    
    Messages(@Nonnull String path) {
        message = color(MoreTools.getInstance().getCfg().getString("messages." + path));
    }
    
    private String color(@Nonnull String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    
    public String getMessage() {
        return message;
    }
    
}