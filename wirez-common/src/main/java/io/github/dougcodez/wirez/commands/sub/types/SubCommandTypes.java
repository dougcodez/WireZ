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

import io.github.dougcodez.wirez.commands.sub.SubCommand;
import io.github.dougcodez.wirez.commands.sub.types.impl.database.*;
import io.github.dougcodez.wirez.commands.sub.types.impl.system.*;

public enum SubCommandTypes {

    CPU(new CPUInfo()),
    MEMORY_INFO(new MemoryInfo()),
    THREAD_INFO(new ServerThreadInfo()),
    THREAD_DUMP(new ServerThreadDump()),
    HEAP_DUMP(new HeapDumpSummary()),

    CONNECT( new DatabaseConnect()),
    DISCONNECT(new DatabaseDisconnect()),
    DB_LIST(new ListConnectedDatabases()),
    DUMP_TABLE( new DumpTable()),
    LIST_TABLES(new ListTables()),

    ;

    private final SubCommand subCommand;
    public static final SubCommandTypes[] CACHE = values();

    SubCommandTypes(SubCommand subCommand) {
        this.subCommand = subCommand;
    }

    public SubCommand getSubCommand() {
        return subCommand;
    }
}
