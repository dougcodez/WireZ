package io.github.dougcodez.wirezvelocity4;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@UtilityClass
public class StringUtils {

    public static Component format(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }
}
