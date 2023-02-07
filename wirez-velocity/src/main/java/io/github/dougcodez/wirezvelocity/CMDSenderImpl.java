package io.github.dougcodez.wirezvelocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import io.github.dougcodez.wirez.commands.user.CommandUser;
import net.kyori.adventure.text.TextComponent;

import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.UUID;

public class CMDSenderImpl  implements CommandUser {

    private CommandSource source;

    public CMDSenderImpl(CommandSource source) {
        this.source = source;
    }

    @Override
    public Optional<UUID> getUUID() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getName() {
        if(source instanceof Player) {
            return Optional.of(((Player) source).getUsername());
        } else {
            return Optional.of(source.getClass().getName());
        }
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

