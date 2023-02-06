package io.github.dougcodez.wirez.commands.sub.types.impl.database;

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.types.DatabaseCommands;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

public class DatabaseDisconnect extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "disconnect";
    }

    @Override
    public String getSubCommandDescription() {
        return Lang.DISCONNECT_FROM_DB_DESC.toConfigString();
    }

    @Override
    public String getSubCommandSyntax() {
        return Lang.DISCONNECT_FROM_DB_SYN.toConfigString();
    }

    @Override
    public String getSubCommandPermission() {
        return "wirez.database";
    }

    @Override
    public void perform(Object sender, String[] args, WireZSettingsFile wireZSettingsFile) {
        CommandUser user = (CommandUser) sender;
        String prefix = Lang.PREFIX.toConfigString();
        if(args.length < 2){
            user.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        checkPermission(user);
        DatabaseCommands.initDisconnection(user, args);
    }
}
