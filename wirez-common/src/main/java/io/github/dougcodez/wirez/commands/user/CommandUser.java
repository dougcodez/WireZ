package io.github.dougcodez.wirez.commands.user;

import java.util.Optional;
import java.util.UUID;

public interface CommandUser {

    Optional<UUID> getUUID();

    Optional<String> getName();

    boolean hasPermission(String permission);

    boolean hasPermissions(String... permissions);

    void sendMessage(String message);

    default UUID getUUIDOrThrow() {
        return getUUID().orElseThrow(() -> new IllegalStateException("CommandSender has no UUID!"));
    }

    default String getNameOrThrow() {
        return getName().orElseThrow(() -> new IllegalStateException("CommandSender has no name!"));
    }
}
