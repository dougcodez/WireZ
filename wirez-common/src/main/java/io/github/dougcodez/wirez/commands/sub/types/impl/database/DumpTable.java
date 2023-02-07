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
package io.github.dougcodez.wirez.commands.sub.types.impl.database;

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.types.DatabaseCommands;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

import java.io.File;

public class DumpTable extends SubCommand {

    private DataFolder dataFolder;

    @Override
    public String getSubCommandName() {
        return "dumptable";
    }

    @Override
    public String getSubCommandDescription() {
        return Lang.DUMP_TABLE_DESC.toConfigString();
    }

    @Override
    public String getSubCommandSyntax() {
        return Lang.DUMP_TABLE_SYN.toConfigString();
    }

    @Override
    public String getSubCommandPermission() {
        return "wirez.database";
    }

    @Override
    public void perform(Object sender, String[] args, WireZSettingsFile wireZSettingsFile) {
        CommandUser user = (CommandUser) sender;
        String prefix = Lang.PREFIX.toConfigString();
        if (args.length < 2) {
            user.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        checkPermission(user);
        DatabaseCommands.createDumpOfTable(user, args, dataFolder.getDataFolder());
    }

    /**
     * Set the data folder for the command on plugin start for any platform
     */
    public void setDataFolder(DataFolder dataFolder) {
        this.dataFolder = dataFolder;
    }

    public interface DataFolder {

        File getDataFolder();
    }
}

