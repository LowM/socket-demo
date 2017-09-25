package socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class myServerSocket {
    public static void main(String[] args){

        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println(e);
        }
        while (true){
            Socket socket=null;
            try{
                socket= serverSocket.accept();
                Thread writerThread=new Thread(new WriteHandler(socket));
                Thread readerThread=new Thread(new ReaderHandler(socket));
                writerThread.start();
                readerThread.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}