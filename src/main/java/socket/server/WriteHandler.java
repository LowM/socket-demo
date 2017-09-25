package socket.server;

import java.io.*;
import java.net.Socket;

public class WriteHandler implements Runnable {
    private Socket socket;

    public WriteHandler(Socket socket){
        this.socket=socket;
    }

    public void run() {
        try{
            System.out.print("请输入消息：");
            OutputStream os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String info=null;
            while(!(info=br.readLine()).equals("end")){
                pw.println(socket.getLocalPort()+":"+info);
                pw.flush();
                System.out.print("请输入消息：");
            }
            System.out.println("运行到这里");

            pw.close();
            os.close();
            br.close();

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