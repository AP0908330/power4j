package com.wusx.thinkinginnetty.ssl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.ClientAuth;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description .
 * @Author:ShangxiuWu
 * @Date: 9:45 2020/6/23.
 * @Modified By:
 */
@Slf4j
public class SslServer {

  private static int PORT = 9999;

  public static void main(String[] args) {
    new SslServer().start(PORT);
  }

  public void start(int port) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.DEBUG))
        .childOption(ChannelOption.SO_REUSEADDR, true)
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
          @Override
          protected void initChannel(NioSocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(MySslContextBuilder.build(ClientAuth.OPTIONAL).newHandler(ch.alloc()));
            pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
            pipeline.addLast(new LineBasedFrameDecoder(100));
            pipeline.addLast(new StringDecoder());
            pipeline.addLast(new StringEncoder());
            pipeline.addLast(new ServerHandler());
          }
        });

    bootstrap.bind(port).addListener((ChannelFutureListener) future -> {
      log.info("server bind port : {}", port);
    });
  }

}
