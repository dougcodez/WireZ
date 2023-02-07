package io.github.dougcodez.wirezvelocity4;

import com.velocitypowered.api.command.SimpleCommand;
import io.github.dougcodez.wirez.commands.WireZCommand;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;

public class WireZCommandImpl implements SimpleCommand, WireZCommand {

    @Override
    public void execute(Invocation invocation) {
        CMDSenderImpl source = new CMDSenderImpl(invocation.source());
        onCommand(source, invocation.arguments());
    }

    @Override
    public WireZSettingsFile getSettingsFile() {
        return WireZPlugin.getInstance().getSettingsFile();
    }
}
