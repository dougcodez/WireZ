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
package io.github.dougcodez.wirez.commands.sub.types;

import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.database.MultiPoolSetup;
import io.github.dougcodez.wirez.database.api.DatabaseAuthentication;
import io.github.dougcodez.wirez.database.api.StatementAPI;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import io.github.dougcodez.wirez.promise.Promise;
import io.github.dougcodez.wirez.promise.PromiseGlobalExecutor;
import io.github.dougcodez.wirez.util.ByteBinClient;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class DatabaseCommands {

    private final MultiPoolSetup multiPoolSetup = MultiPoolSetup.grabInstance();

    public void initConnection(CommandUser source, WireZSettingsFile wireZSettingsFile, String[] args) {

        final String host = args[1];
        final AtomicInteger port = new AtomicInteger();
        try {
            port.set(Integer.parseInt(args[2]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        final String database = args[3];
        final String username = args[4];
        final String password = args[5];
        final AtomicInteger timeout = new AtomicInteger();
        final AtomicInteger poolSize = new AtomicInteger();
        try {
            timeout.set(Integer.parseInt(args[6]));
            poolSize.set(Integer.parseInt(args[7]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        multiPoolSetup.init(source, wireZSettingsFile, new DatabaseAuthentication(host, port.get(), database, username, password), timeout.get(), poolSize.get());
    }

    public void initDisconnection(CommandUser source, String[] args) {
        final String name = source.getNameOrThrow();
        final String database = args[1];
        if (multiPoolSetup.isNotEstablished(name, database)) {
            source.sendMessage(Lang.PREFIX.toConfigString() + Lang.DATABASE_NOT_CONNECTED.toConfigString());
            return;
        }

        multiPoolSetup.close(name, database);
        source.sendMessage(Lang.PREFIX.toConfigString() + Lang.DISCONNECTED_FROM_DB_SUCCESSFULLY.toConfigString());
    }

    public void grabListOfDatabases(CommandUser source) {
        if (multiPoolSetup.getPlayersCurrentDbs().isEmpty() || multiPoolSetup.getPlayersCurrentDbs().get(source.getNameOrThrow()).isEmpty()) {
            source.sendMessage(Lang.PREFIX.toConfigString() + Lang.LISTED_DATABASES_EMPTY.toConfigString());
            return;
        }

        source.sendMessage(Lang.PREFIX.toConfigString() + Lang.LISTED_DATABASES_INTRO.toConfigString());
        for (String databases : multiPoolSetup.getPlayersCurrentDbs().get(source.getNameOrThrow()).keySet()) {
            source.sendMessage(Lang.LISTED_DATABASES_SET.toConfigString().replace("%database%", databases));
        }
    }

    public void grabListOfTargetsDatabase(CommandUser source, String[] args) {
        String target = args[1];
        if (multiPoolSetup.getPlayersCurrentDbs().isEmpty() || multiPoolSetup.getPlayersCurrentDbs().get(target).isEmpty()) {
            source.sendMessage(Lang.PREFIX.toConfigString() + Lang.LISTED_DATABASES_TARGET_EMPTY.toConfigString());
            return;
        }

        source.sendMessage(Lang.PREFIX.toConfigString() + Lang.LISTED_DATABASES_TARGET_INTRO.toConfigString().replace("%player%", target));
        for (String databases : multiPoolSetup.getPlayersCurrentDbs().get(target).keySet()) {
            source.sendMessage(Lang.LISTED_DATABASES_SET.toConfigString().replace("%database%", databases));
        }
    }

    public synchronized void createDumpOfTable(CommandUser source, String[] args, File dataFolder) {
        final String name = source.getNameOrThrow();
        final String database = args[1];
        final String table = args[2];
        final String fileName = args[3];

            if (multiPoolSetup.isNotEstablished(source.getNameOrThrow(), database)) {
                source.sendMessage(Lang.PREFIX.toConfigString() + Lang.DATABASE_NOT_CONNECTED.toConfigString());
                return;
            }

            File file = new File(dataFolder + File.separator + "dblogs", fileName + ".csv");
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Could not create CSV");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            StatementAPI.getStatementAPI().executeQuery(multiPoolSetup.getConnections(name, database), "SELECT * FROM " + table, rs -> {
                try (FileWriter fw = new FileWriter(file)) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int cols = rs.getMetaData().getColumnCount();

                    for (int i = 1; i <= cols; i++) {
                        fw.append(rsmd.getColumnLabel(i));
                        if (i < cols) fw.append(',');
                        else fw.append('\n');
                    }

                    while (rs.next()) {

                        for (int i = 1; i <= cols; i++) {
                            fw.append(rs.getString(i));
                            if (i < cols) fw.append(',');
                        }
                        fw.append('\n');
                    }
                    fw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                source.sendMessage(Lang.PREFIX.toConfigString() + Lang.DUMP_TABLE_SUCCESS.toConfigString());
                return rs;
            });
    }

    public void showTables(CommandUser source, String[] args) {
        final String name = source.getNameOrThrow();
        final String database = args[1];
        final List<String> formatList = Collections.synchronizedList(new LinkedList<>());
        CompletableFuture.runAsync(() -> {
            StatementAPI.getStatementAPI().executeQuery(multiPoolSetup.getConnections(name, database), "SHOW TABLES", rs -> {
                while (rs.next()) {
                    formatList.add(rs.getRow() + ":" + rs.getString(1) + "\n");

                }
                return rs;

            });
        }).whenComplete((unused, throwable) -> Promise.createNew().fulfillInAsync(() -> {
            ByteBinClient.postRequest(formatList, () -> {
                source.sendMessage(Lang.PREFIX.toConfigString() + Lang.LIST_TABLES_COMPLETE.toConfigString().replace("%key%", ByteBinClient.getKey()));
            });
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace));
    }
}
