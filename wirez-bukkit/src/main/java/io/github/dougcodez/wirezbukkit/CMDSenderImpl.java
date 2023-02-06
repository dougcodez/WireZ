package io.github.dougcodez.wirezbukkit;

import io.github.dougcodez.wirez.commands.user.CommandUser;
import org.bukkit.command.CommandSender;

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
        source.sendMessage(StringUtils.format(message));
    }
}
