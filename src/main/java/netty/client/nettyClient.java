package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import netty.server.nettyServerHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class nettyClient {
    static String ip="localhost";
    static int port=8080;

    public nettyClient(){
        init();
    }

    public void init(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("encoder", new StringEncoder());
                        ch.pipeline().addLast("decoder", new StringDecoder());
                        ch.pipeline().addLast(new nettyClientHandler());
                    }
                });
        try {
            Channel channel=bootstrap.connect(ip,port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                String info=in.readLine();
                channel.writeAndFlush(info+"\r\n");
                System.out.println("已输入信息"+info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }
    public static void main(String args[]){
        new nettyClient();
    }
}