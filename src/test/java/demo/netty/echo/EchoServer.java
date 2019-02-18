package demo.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * 服务端启动类
 * • 配置服务器功能，如线程、端口
 * • 实现服务器处理程序，它包含业务逻辑，决定当有一个请求连接或接收数据时该做什么
 * Created by tianjun on 2016/12/19 0019.
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception{

        EventLoopGroup eventLoopGroup = null;
        try {
            //创建ServerBootstrap实例来引导绑定和启动服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //创建NioEventLoopGroup对象来处理事件，如接受新连接、接收数据、写数据等等
            eventLoopGroup = new NioEventLoopGroup();
            //指定通道类型为NioServerSocketChannel，设置InetSocketAddress让服务器监听某个端口已等待客户端连接。
            final ServerBootstrap serverBootstrap1 = serverBootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class)
                    .localAddress("localhost", port).childHandler(new ChannelInitializer<Channel>() {
                        //设置childHandler执行所有的连接请求
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            // 最后绑定服务器等待直到绑定完成，调用sync()方法会阻塞直到服务器完成绑定,然后服务器等待通道关闭，因为使用sync()，所以关闭操作也会被阻塞。
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println("开始监听，端口为："+channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        new EchoServer(20000).start();
    }
}