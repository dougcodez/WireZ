/**
 * MIT License
 *
 * Copyright (c) 2022-2023 Douglas (dougcodez)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
