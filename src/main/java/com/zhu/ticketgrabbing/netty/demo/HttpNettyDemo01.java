package com.zhu.ticketgrabbing.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.SneakyThrows;

/**
 * 基于netty实现http测试类
 */
public class HttpNettyDemo01 {

    @SneakyThrows
    public void run() {

        // 接收线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(10);
        // 工作线程池
        EventLoopGroup workerGroup = new NioEventLoopGroup(20);

        try {
            // 服务端的程序进行NIO请求，设置Channel
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 设置线程池，以及当前的Channel类型
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);

            // 接收信息后需要进行处理，定义子处理器
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpResponseEncoder()); // 响应编码
                    ch.pipeline().addLast(new HttpRequestDecoder()); // 请求解码
                    ch.pipeline().addLast(new HttpServerHandler());
                }
            });

            // tcp协议相关配置
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 异步回调处理操作
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } finally {

            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

        }


    }


}
