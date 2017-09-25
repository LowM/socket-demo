package socket.client;

import socket.server.ReaderHandler;
import socket.server.WriteHandler;

import java.io.*;
import java.net.Socket;

public class myClientSocket {
    public static void main(String []args) throws IOException {
        Socket socket=new Socket("localhost",4444);

        Thread writerThread=new Thread(new WriteHandler(socket));
        Thread readerThread=new Thread(new ReaderHandler(socket));
        writerThread.start();
        readerThread.start();

    }
}