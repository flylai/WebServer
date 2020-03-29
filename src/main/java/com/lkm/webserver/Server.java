package com.lkm.webserver;

import com.lkm.webserver.connection.Connection;
import com.lkm.webserver.connection.ConnectionPool;
import com.lkm.webserver.connection.Processor;
import com.lkm.webserver.constant.Misc;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public
class Server {
    ServerSocketChannel serverSocketChannel;
    Selector selector;

    public Server() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(Misc.PORT), 1024);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                int n = selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                        if (key.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            int socketChannelHash = socketChannel.hashCode();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int messageSize = socketChannel.read(readBuffer);
                            readBuffer.flip();
                            if (messageSize > 0) {
                                ConnectionPool.getConnectionPool().putIfAbsent(socketChannelHash,
                                        new Connection(socketChannel));
                                ConnectionPool.getConnectionPool().get(socketChannelHash).updateLastConnectTime();

                                byte[] tmp = new byte[messageSize];
                                readBuffer.get(tmp);
                                ConnectionPool.getConnectionPool().get(socketChannelHash)
                                        .getHttpMessage().write(tmp);
                            }
                            if (messageSize == -1) {
                                ConnectionPool.removeSocket(socketChannel);
                            }
                            if (messageSize < Misc.BUFFER_SIZE) {
                                Processor.requestExecutor.execute(() -> {
                                    Processor.processRequest(ConnectionPool.getConnectionPool().get(socketChannelHash));
                                });
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}