package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class nettyServer {

    static  int port=8080;

    public nettyServer(){
        init();
    };

    public void init() {
        //实例化服务端boostrap
        ServerBootstrap bootstrap = new ServerBootstrap();

        //创建两个线程
        NioEventLoopGroup dady = new NioEventLoopGroup();
        NioEventLoopGroup baby = new NioEventLoopGroup();
        bootstrap.group(dady,baby)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("encoder", new StringEncoder());
                        ch.pipeline().addLast("decoder", new StringDecoder());
                        ch.pipeline().addLast(new nettyServerHandler());
                        System.out.println(ch.remoteAddress()+"连接上");
                    }
                })
        .option(ChannelOption.SO_BACKLOG,128)
        .childOption(ChannelOption.SO_KEEPALIVE,true);

        try {
            System.out.println("已绑定端口");
            //绑定端口
            ChannelFuture future=bootstrap.bind(port).sync();



        } catch (InterruptedException e) {
            dady.shutdownGracefully();
            baby.shutdownGracefully();
        }
    }
    public static void main(String [] args){
        new nettyServer();
    }

}