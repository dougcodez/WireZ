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
