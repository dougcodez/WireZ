/**
 * MIT License
 *
 * Copyright (c) 2022-2023 Douglas (dougcodez)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.dougcodez.wirezvelocity4;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.connection.Player;
import io.github.dougcodez.wirez.commands.user.CommandUser;

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
            return Optional.of(((Player) source).username());
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

