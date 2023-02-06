package io.github.dougcodez.wirezbukkit;

import io.github.dougcodez.wirez.commands.WireZCommand;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WireZCommandImpl implements CommandExecutor, WireZCommand {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CMDSenderImpl source = new CMDSenderImpl(sender);
        return onCommand(source, args);
    }

    @Override
    public WireZSettingsFile getSettingsFile() {
        return WireZPlugin.getInstance().getSettingsFile();
    }
}
