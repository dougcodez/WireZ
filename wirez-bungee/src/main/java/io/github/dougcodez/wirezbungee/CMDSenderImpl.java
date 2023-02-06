package io.github.dougcodez.wirezbungee;

import io.github.dougcodez.wirez.commands.user.CommandUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Optional;
import java.util.UUID;

public class CMDSenderImpl implements CommandUser {

    private CommandSender source;

    public CMDSenderImpl(CommandSender source) {
        this.source = source;
    }

    @Override
    public Optional<UUID> getUUID() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getName() {
        return Optional.of(source.getName());
    }

    @Override
    public boolean hasPermission(String permission) {
        return source.hasPermission(permission);
    }

    @Override
    public boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            return source.hasPermission(permission);
        }

        return false;
    }

    @Override
    public void sendMessage(String message) {
        source.sendMessage(TextComponent.fromLegacyText(StringUtils.format(message)));
    }
}

