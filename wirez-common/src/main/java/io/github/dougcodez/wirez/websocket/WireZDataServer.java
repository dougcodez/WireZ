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
package io.github.dougcodez.wirez.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.logging.Logger;


public class WireZDataServer extends WebSocketServer {

    private final Logger logger = Logger.getLogger(WireZDataServer.class.getName());

    public WireZDataServer(String host, int port) {
        super(new InetSocketAddress(host, port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("Connection opened to " + conn.getRemoteSocketAddress().getHostName());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        logger.info("Connection closed to " + conn.getRemoteSocketAddress().getHostName());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        logger.info("Received message: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.info(ex.getMessage());
    }

    @Override
    public void onStart() {
        logger.info("Webserver started on port: " + getPort());
    }
}