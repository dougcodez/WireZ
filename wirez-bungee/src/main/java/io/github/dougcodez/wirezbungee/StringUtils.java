package io.github.dougcodez.wirezbungee;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class StringUtils {

    public String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
