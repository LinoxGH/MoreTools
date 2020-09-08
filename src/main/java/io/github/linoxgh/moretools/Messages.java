package io.github.linoxgh.moretools;

import javax.annotation.Nonnull;
import org.bukkit.ChatColor;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;

public enum Messages {

    CRESCENTHAMMER_BLOCKBREAKING("items.crescent-hammer.block-breaking"),
    CRESCENTHAMMER_RIGHTCLICKFAIL("items.crescent-hammer.right-click-fail");
    
    private final String message;
    
    Messages(@Nonnull String path) {
        message = ChatColor.color(MoreTools.getInstance().getCfg().getString("messages." + path));
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
    
}