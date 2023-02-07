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
package io.github.dougcodez.wirez.commands;

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.SubCommandRegistry;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

import java.util.List;
import java.util.Map;

public interface WireZCommand {

    default boolean onCommand(CommandUser user, String[] args) {
        if (!user.hasPermission("wirez.help")) {
            user.sendMessage(Lang.PREFIX.toConfigString() + Lang.NO_PERMISSION.toConfigString());
            return true;
        }

        Map<String,SubCommand> subCommandMap = SubCommandRegistry.getSubCommandMap();
        if (args.length > 0) {
            for (SubCommand subCommand : subCommandMap.values()) {
                if (args[0].equalsIgnoreCase(subCommand.getSubCommandName())) {
                    subCommand.perform(user, args, getSettingsFile());
                }
            }
        } else {
            for (SubCommand subCommand : subCommandMap.values()) {
                user.sendMessage(Lang.PREFIX.toConfigString() + subCommand.getSubCommandSyntax() + " - " + subCommand.getSubCommandDescription());
            }
        }

        return true;
    }

    WireZSettingsFile getSettingsFile();
}

