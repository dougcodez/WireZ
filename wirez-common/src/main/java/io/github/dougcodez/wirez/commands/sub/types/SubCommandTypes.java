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
