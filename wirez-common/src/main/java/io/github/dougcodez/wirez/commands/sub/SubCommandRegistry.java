package io.github.dougcodez.wirez.commands.sub;

import io.github.dougcodez.wirez.commands.sub.types.SubCommandTypes;
import io.github.dougcodez.wirez.commands.sub.types.impl.database.*;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class SubCommandRegistry {

    private final Map<String, SubCommand> subCommandMap = new LinkedHashMap<>();

    public void registerCommands(WireZSettingsFile settingsFile) {
        for (SubCommandTypes subCommandTypes : SubCommandTypes.CACHE) {
            subCommandMap.putIfAbsent(subCommandTypes.getSubCommand().getClass().getSimpleName(), subCommandTypes.getSubCommand());
        }

        //Checking to see if the database modules meet the criteria to be registered
        if (!settingsFile.getConfigFile().getBoolean("database-module.enable")) {
            subCommandMap.remove(DatabaseConnect.class.getSimpleName());
            subCommandMap.remove(DatabaseDisconnect.class.getSimpleName());
            subCommandMap.remove(DumpTable.class.getSimpleName());
            subCommandMap.remove(ListConnectedDatabases.class.getSimpleName());
            subCommandMap.remove(ListTables.class.getSimpleName());
        }
    }

    public Map<String, SubCommand> getSubCommandMap() {
        return subCommandMap;
    }
}
