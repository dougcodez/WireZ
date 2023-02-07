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
package io.github.dougcodez.wirez.library.logging.adapter;

import io.github.dougcodez.wirez.library.logging.LogLevel;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class JDKLogAdapter implements LogAdapter {
    /**
     * JDK logger
     */
    private final Logger logger;

    /**
     * Creates a new JDK log adapter that logs to a {@link Logger}.
     *
     * @param logger the JDK logger to wrap
     */
    public JDKLogAdapter(Logger logger) {
        this.logger = requireNonNull(logger, "logger");
    }

    /**
     * Logs a message with the provided level to the JDK logger.
     *
     * @param level   message severity level
     * @param message the message to log
     */
    @Override
    public void log(LogLevel level, String message) {
        switch (requireNonNull(level, "level")) {
            case DEBUG:
                logger.log(Level.FINE, message);
                break;
            case INFO:
                logger.log(Level.INFO, message);
                break;
            case WARN:
                logger.log(Level.WARNING, message);
                break;
            case ERROR:
                logger.log(Level.SEVERE, message);
                break;
        }
    }

    /**
     * Logs a message and stack trace with the provided level to the JDK
     * logger.
     *
     * @param level     message severity level
     * @param message   the message to log
     * @param throwable the throwable to print
     */
    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        switch (requireNonNull(level, "level")) {
            case DEBUG:
                logger.log(Level.FINE, message, throwable);
                break;
            case INFO:
                logger.log(Level.INFO, message, throwable);
                break;
            case WARN:
                logger.log(Level.WARNING, message, throwable);
                break;
            case ERROR:
                logger.log(Level.SEVERE, message, throwable);
                break;
        }
    }
}

