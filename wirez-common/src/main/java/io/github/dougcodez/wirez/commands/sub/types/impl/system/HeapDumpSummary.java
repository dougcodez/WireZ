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
package io.github.dougcodez.wirez.commands.sub.types.impl.system;

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.types.SystemMonitorCommands;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

public class HeapDumpSummary extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "heapdump";
    }

    @Override
    public String getSubCommandDescription() {
        return Lang.HEAP_DUMP_DESC.toConfigString();
    }

    @Override
    public String getSubCommandSyntax() {
        return Lang.HEAP_DUMP_SYN.toConfigString();
    }

    @Override
    public String getSubCommandPermission() {
        return "wirez.systems";
    }

    @Override
    public void perform(Object sender, String[] args, WireZSettingsFile file) {
        CommandUser user = (CommandUser) sender;
        checkPermission(user);
        String prefix = Lang.PREFIX.toConfigString();
        user.sendMessage(prefix + Lang.PASTE_LOADING.toConfigString());
        SystemMonitorCommands.printHeapSummary(user);
    }
}
