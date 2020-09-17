package io.github.linoxgh.moretools;

import org.bukkit.ChatColor;

public enum Messages {

    CRESCENTHAMMER_BLOCKBREAKING("items.crescent-hammer.block-breaking"),
    CRESCENTHAMMER_COOLDOWN("items.crescent-hammer.cooldown"),
    CRESCENTHAMMER_DISMANTLEFAIL("items.crescent-hammer.dismantle-fail"),
    CRESCENTHAMMER_ROTATEFAIL("items.crescent-hammer.rotate-fail"),
    CRESCENTHAMMER_CHANNELCHANGEFAIL("items.crescent-hammer.channel-change-fail"),
    CRESCENTHAMMER_CHANNELCHANGESUCCESS("items.crescent-hammer.channel-change-success");
    
    private final String message;
    
    Messages(String path) {
        message = color(MoreTools.getInstance().getConfig().getString("messages." + path));
    }
    
    private String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    
    public String getMessage() {
        return message;
    }
    
}