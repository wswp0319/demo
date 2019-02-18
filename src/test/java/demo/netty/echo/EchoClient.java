package demo.netty.echo;

import java.io.BufferedReader; 
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.InetSocketAddress;  
import java.net.Socket;  
import java.net.UnknownHostException;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.locks.LockSupport; 

/**
 * 客户端启动类
 *  • 连接服务器
 *  • 写数据到服务器
 *  • 等待接受服务器返回相同的数据
 *  • 关闭连接
 * Created by tianjun on 2016/12/19 0019.
 */
public class EchoClient extends Thread{
    private final String host;
    private final int port;
    private final Long sleep_time=1000L; 

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /*public void start() throws InterruptedException {
        EventLoopGroup nioEventLoopGroup = null;
        try {

            //创建Bootstrap对象用来引导启动客户端
            Bootstrap bootstrap = new Bootstrap();
            //创建EventLoopGroup对象并设置到Bootstrap中，EventLoopGroup可以理解为是一个线程池，这个线程池用来处理连接、接受数据、发送数据
            nioEventLoopGroup = new NioEventLoopGroup();
            //创建InetSocketAddress并设置到Bootstrap中，InetSocketAddress是指定连接的服务器地址
            bootstrap.group(nioEventLoopGroup).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //添加一个ChannelHandler，客户端成功连接服务器后就会被执行
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // • 调用Bootstrap.connect()来连接服务器
            ChannelFuture f = bootstrap.connect().sync();
            // • 最后关闭EventLoopGroup来释放资源
            f.channel().closeFuture().isCancellable();
        }finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }
    }*/
    
    public void run() {  
    	  
        Socket client=null;  
        PrintWriter writer=null;  
        BufferedReader reader=null;  
        try {  
            client=new Socket();  
            client.connect(new InetSocketAddress(host,port));  
            writer=new PrintWriter(client.getOutputStream(),true);  
//            writer.print("h");  
//            LockSupport.parkNanos((long) (sleep_time*Math.random()));  
//            writer.print("e");  
//            LockSupport.parkNanos((long) (sleep_time*Math.random()));  
//
//            writer.print("l");  
//            LockSupport.parkNanos((long) (sleep_time*Math.random()));  
//
//            writer.print("l");  
//            LockSupport.parkNanos((long) (sleep_time*Math.random()));  
//
//            writer.print("o");  
//            LockSupport.parkNanos((long) (sleep_time*Math.random()));  
//
//            writer.print("!");  
//            LockSupport.parkNanos((long) (sleep_time*Math.random()));  
            writer.println("{\"msg\": \"hello\"}");

            writer.println();  
            writer.flush();  
            //reader=new BufferedReader(new InputStreamReader(client.getInputStream()));  
            //System.out.println("from server:"+reader.readLine());  
        }catch (UnknownHostException ex){  
            ex.printStackTrace();  
        }catch (IOException e){  
            e.printStackTrace();  
        } finally {  
            if(writer!=null){  
                writer.close();  
            }  
            if(reader!=null){  
                try {  
                    reader.close();  
                }catch (IOException ex){  
                    ex.printStackTrace();  
                }  

            }  
            if(client!=null){  
                try {  
                    client.close();  
                }catch (IOException ex){  
                    ex.printStackTrace();  
                }  
            }  
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //new EchoClient("localhost", 20000).start();
    	
    	//ExecutorService es= Executors.newCachedThreadPool(); 
    	
        for(int i=0;i<1;i++){  
            //es.execute(ec);  
        	EchoClient ec=new EchoClient("localhost", 8888);  
        	ec.start();
        }  
    }

	

}