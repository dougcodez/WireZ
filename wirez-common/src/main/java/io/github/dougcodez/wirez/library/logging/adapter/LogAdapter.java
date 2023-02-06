package io.github.dougcodez.wirez.library.logging.adapter;

import io.github.dougcodez.wirez.library.logging.LogLevel;

public interface LogAdapter {

    /**
     * Logs a message with the provided level.
     *
     * @param level   message severity level
     * @param message the message to log
     */
    void log(LogLevel level, String message);

    /**
     * Logs a message and stack trace with the provided level.
     *
     * @param level     message severity level
     * @param message   the message to log
     * @param throwable the throwable to print
     */
    void log(LogLevel level, String message, Throwable throwable);
}
