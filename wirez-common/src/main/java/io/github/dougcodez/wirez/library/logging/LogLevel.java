package io.github.dougcodez.wirez.library.logging;

public enum LogLevel {

    /**
     * Stuff that isn't useful to end-users
     */
    DEBUG,

    /**
     * Stuff that might be useful to know
     */
    INFO,

    /**
     * Non-fatal, often recoverable errors or notices
     */
    WARN,

    /**
     * Probably an unrecoverable error
     */
    ERROR
}
