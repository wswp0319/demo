package demo.netty.nettyupload;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;

public class FileUploadClient {
    public void connect(int port, String host, final FileUploadFile fileUploadFile) throws Exception {
        System.out.println("---------------Clent connect-------------------");
        ///创建请求线程
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            //线程装配到bootstarp中
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                    //处理
                    ch.pipeline().addLast(new FileUploadClientHandler(fileUploadFile));
                }
            });
            //连接到服务
            ChannelFuture f = b.connect(host, port).sync();
            //同步释放
            f.channel().closeFuture().sync();
        } finally {
            //释放资源
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        System.out.println("Client main method is start");
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        try {
            //文件
            FileUploadFile uploadFile = new FileUploadFile();
            File file = new File("c:/1.txt");
            String fileMd5 = file.getName();// 文件名
            //配置domain对象
            uploadFile.setFile(file);
            uploadFile.setFile_md5(fileMd5);
            uploadFile.setStarPos(0);// 文件开始位置
            //启动
            new FileUploadClient().connect(port, "127.0.0.1", uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Client main method is end");
    }

}