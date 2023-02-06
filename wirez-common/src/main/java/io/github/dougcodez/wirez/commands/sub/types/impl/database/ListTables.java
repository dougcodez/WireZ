package io.github.dougcodez.wirez.commands.sub.types.impl.database;

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.types.DatabaseCommands;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.database.MultiPoolSetup;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

public class ListTables extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "listtables";
    }

    @Override
    public String getSubCommandDescription() {
        return Lang.LIST_TABLES_DESC.toConfigString();
    }

    @Override
    public String getSubCommandSyntax() {
        return Lang.LIST_TABLES_SYN.toConfigString();
    }

    @Override
    public String getSubCommandPermission() {
        return "wirez.database";
    }

    @Override
    public void perform(Object sender, String[] args, WireZSettingsFile wireZSettingsFile) {
        CommandUser user = (CommandUser) sender;
        String prefix = Lang.PREFIX.toConfigString();
        if(args.length != 2){
            user.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        final MultiPoolSetup multiDataPoolSetup = MultiPoolSetup.grabInstance();
        if(multiDataPoolSetup.isNotEstablished(user.getNameOrThrow(), args[1])){
            user.sendMessage(prefix + Lang.DATABASE_NOT_CONNECTED.toConfigString());
            return;
        }

        DatabaseCommands.showTables(user, args);
    }
}
