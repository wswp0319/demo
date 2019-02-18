package demo.netty.nettyupload;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUploadClientHandler extends ChannelInboundHandlerAdapter {
    private int byteRead;
    private volatile int start = 0;
    private volatile int lastLength = 0;
    public RandomAccessFile randomAccessFile;
    private FileUploadFile fileUploadFile;

    public FileUploadClientHandler(FileUploadFile ef) {
        System.out.println("FileUploadClientHandler 构造方法调用");
        if (ef.getFile().exists()) {
            if (!ef.getFile().isFile()) {
                System.out.println("Not a file :" + ef.getFile());
                return;
            }
        }
        this.fileUploadFile = ef;
    }
    //文件未读完，通道一直处于活动状态
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("--------------------Client channelActive---------------------");
        try {
            //把文件装配到randomAccessFile
            randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
            //seek：设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作。
            randomAccessFile.seek(fileUploadFile.getStarPos());
            //分10次读写
            lastLength = (int) randomAccessFile.length() / 10;
            byte[] bytes = new byte[lastLength];
            if ((byteRead = randomAccessFile.read(bytes)) != -1) {
                fileUploadFile.setEndPos(byteRead);
                fileUploadFile.setBytes(bytes);
                //写出并刷新
                ctx.writeAndFlush(fileUploadFile);
            } else {
                System.out.println("文件已经读完");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //一直读取，直到文件读完，会进行多次调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("--------------------Client channelRead---------------------");
        if (msg instanceof Integer) {

            start = (Integer) msg;
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
                randomAccessFile.seek(start);
                System.out.println("块儿长度：" + (randomAccessFile.length() / 10));
                System.out.println("长度：" + (randomAccessFile.length() - start));
                int a = (int) (randomAccessFile.length() - start);
                int b = (int) (randomAccessFile.length() / 10);
                if (a < b) {
                    lastLength = a;
                }
                byte[] bytes = new byte[lastLength];
                System.out.println("-----------------------------" + bytes.length);
                if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
                    System.out.println("byte 长度：" + bytes.length);
                    fileUploadFile.setEndPos(byteRead);
                    fileUploadFile.setBytes(bytes);
                    try {
                        ctx.writeAndFlush(fileUploadFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    randomAccessFile.close();
                    ctx.close();
                    System.out.println("文件已经读完--------" + byteRead);
                }
            }
        }
    }

    // @Override
    // public void channelRead(ChannelHandlerContext ctx, Object msg) throws
    // Exception {
    // System.out.println("Server is speek ："+msg.toString());
    // FileRegion filer = (FileRegion) msg;
    // String path = "E://Apk//APKMD5.txt";
    // File fl = new File(path);
    // fl.createNewFile();
    // RandomAccessFile rdafile = new RandomAccessFile(path, "rw");
    // FileRegion f = new DefaultFileRegion(rdafile.getChannel(), 0,
    // rdafile.length());
    //
    // System.out.println("This is" + ++counter + "times receive server:["
    // + msg + "]");
    // }

    // @Override
    // public void channelReadComplete(ChannelHandlerContext ctx) throws
    // Exception {
    // ctx.flush();
    // }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("--------------------Client exceptionCaught---------------------");
        cause.printStackTrace();
        ctx.close();
    }
    // @Override
    // protected void channelRead0(ChannelHandlerContext ctx, String msg)
    // throws Exception {
    // String a = msg;
    // System.out.println("This is"+
    // ++counter+"times receive server:["+msg+"]");
    // }
}