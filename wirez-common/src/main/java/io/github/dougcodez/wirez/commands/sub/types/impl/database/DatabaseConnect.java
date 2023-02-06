package io.github.dougcodez.wirez.commands.sub.types.impl.database;

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.types.DatabaseCommands;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

public class DatabaseConnect extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "connect";
    }

    @Override
    public String getSubCommandDescription() {
        return Lang.CONNECT_TO_DB_DESC.toConfigString();
    }

    @Override
    public String getSubCommandSyntax() {
        return Lang.CONNECT_TO_DB_SYN.toConfigString();
    }

    @Override
    public String getSubCommandPermission() {
        return "wirez.database";
    }

    @Override
    public void perform(Object sender, String[] args, WireZSettingsFile file) {
        CommandUser user = (CommandUser) sender;
        String prefix = Lang.PREFIX.toConfigString();
        if (args.length < 8) {
            user.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }
        checkPermission(user);
        DatabaseCommands.initConnection(user, file, args);
    }
}
