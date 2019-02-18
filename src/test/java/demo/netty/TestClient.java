package demo.netty;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class TestClient {
	private TestHandler handler;

	public TestHandler getHandler() {
		return handler;
	}

	public void setHandler(TestHandler handler) {
		this.handler = handler;
	}

	public TestClient() {
		handler = new TestHandler();
	}

	public void connect(int port, String host) throws Exception {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new LoggingHandler(LogLevel.INFO)).handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast("ping", new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
							p.addLast("decoder", new StringDecoder());
							p.addLast("encoder", new StringEncoder());
							p.addLast(handler);
						}
					});

			ChannelFuture future = b.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	private void createAndShowGUI() {
		// 确保一个漂亮的外观风格
		JFrame.setDefaultLookAndFeelDecorated(true);

		// 创建及设置窗口
		JFrame frame = new JFrame("HelloWorldSwing");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setSize(400, 300);
		// frame.setLocation(400, 400);

		// 添加 "Hello World" 标签
		JLabel label = new JLabel("请输入要发送消息：");
		frame.getContentPane().add(label, BorderLayout.NORTH);
		
		final JTextArea jf = new JTextArea("{\"command\":0,\"mobile\":\"13548484848\"}", 20, 43);
		jf.setLineWrap(true);
		frame.getContentPane().add(jf, BorderLayout.CENTER);
		
		
		JButton submit = new JButton("submit");
		frame.getContentPane().add(submit, BorderLayout.SOUTH);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.send(jf.getText());
			}
		});
		// 显示窗口
		// frame.pack();
		// frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				handler.close();
				System.exit(0);
			}
		});

		frame.setTitle("Netty客户端");
		frame.setSize(400, 300);
		frame.setLocation(200, 200);
		frame.setVisible(true);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 8888;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}
		new TestClient().connect(port, "127.0.0.1");
		// 显示应用 GUI

	}
}
