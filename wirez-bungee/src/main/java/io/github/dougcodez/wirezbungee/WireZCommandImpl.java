package io.github.dougcodez.wirezbungee;

import io.github.dougcodez.wirez.commands.WireZCommand;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class WireZCommandImpl extends Command implements WireZCommand {


    public WireZCommandImpl() {
        super("wirez");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        CMDSenderImpl source = new CMDSenderImpl(commandSender);
        onCommand(source, args);
    }


    @Override
    public WireZSettingsFile getSettingsFile() {
        return WireZPlugin.getInstance().getSettingsFile();
    }
}
