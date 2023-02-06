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

