package io.github.dougcodez.wirez.commands.sub.types.impl.database;

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.types.DatabaseCommands;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

public class ListConnectedDatabases extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "dblist";
    }

    @Override
    public String getSubCommandDescription() {
        return Lang.LISTED_DATABASES_DESC.toConfigString();
    }

    @Override
    public String getSubCommandSyntax() {
        return Lang.LISTED_DATABASES_SYN.toConfigString();
    }

    @Override
    public String getSubCommandPermission() {
        return "wirez.database";
    }

    @Override
    public void perform(Object sender, String[] args, WireZSettingsFile wireZSettingsFile) {
        CommandUser user = (CommandUser) sender;
        if (args.length == 1) {
            checkPermission(user);
            DatabaseCommands.grabListOfDatabases(user);
        } else if (args.length == 2) {
            checkPermission(user);
            DatabaseCommands.grabListOfTargetsDatabase(user, args);
        }
    }
}
