package socket.server;

import java.io.*;
import java.net.Socket;

public class ReaderHandler implements Runnable{

    private Socket socket;

    public ReaderHandler(Socket socket){
        this.socket=socket;
    }

    public void run() {
        try{
            InputStream is=socket.getInputStream();
            InputStreamReader sr=new InputStreamReader(is);
            BufferedReader in=new BufferedReader(sr);

            PrintWriter pw=new PrintWriter(socket.getOutputStream());

            String info=null;
            while((info=in.readLine())!=null){
                System.out.println();
                System.out.println(info);
                System.out.print("请输入消息：");

                if(info.split(":")[1].equals("hello you")){

                    pw.println(socket.getLocalPort()+": hello you too");
                    pw.flush();
                }

            }
            System.out.println("运行到这里");


            //Thread.sleep(10000);
            in.close();
            sr.close();
            is.close();

        }catch(Exception e){e.printStackTrace();}finally{
            try{
                System.out.println("关闭连接:"+socket.getInetAddress()+":"+socket.getPort());
                if(socket!=null)socket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}