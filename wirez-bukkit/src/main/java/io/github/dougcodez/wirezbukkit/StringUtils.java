package io.github.dougcodez.wirezbukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class StringUtils {

    public String format(String string) {
      return ChatColor.translateAlternateColorCodes('&', string);
    }
}
