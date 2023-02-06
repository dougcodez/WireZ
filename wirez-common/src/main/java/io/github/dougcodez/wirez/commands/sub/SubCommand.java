package io.github.dougcodez.wirez.commands.sub;

import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

public abstract class SubCommand {

    public abstract String getSubCommandName();

    public abstract String getSubCommandDescription();

    public abstract String getSubCommandSyntax();

    public abstract String getSubCommandPermission();

    public abstract void perform(Object sender, String[] args, WireZSettingsFile wireZSettingsFile);

    public void checkPermission(CommandUser user) {
        if (!user.hasPermission(getSubCommandPermission())) {
            user.sendMessage(Lang.PREFIX.toConfigString() + Lang.NO_PERMISSION.toConfigString());
            return;
        }
    }
}

