package demo.netty.heartbeats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient extends Thread{
	private String host;
	private int port;
	
	public TestClient(String host,int port) {
		this.host = host;
		this.port = port;
	}
	
	public void run() {
		Socket client=null;  
        PrintWriter writer=null;  
        BufferedReader in=null;  
        try {  
            client=new Socket();  
            client.connect(new InetSocketAddress(host,port));  
            writer=new PrintWriter(client.getOutputStream(),true);  
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer.println("{\"msg\": \"hello\"}");

            //writer.println();  
            //writer.flush();  
            String resp = in.readLine();
            System.out.println(resp);
        }catch (UnknownHostException ex){  
            ex.printStackTrace();  
        }catch (IOException e){  
            e.printStackTrace();  
        } finally {  
            if(writer!=null){  
                writer.close();  
            }  
            if(in!=null){  
                try {  
                    in.close();  
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

	public static void main(String[] args) {
		
		for(int i=0;i<10;i++) {
			TestClient tc = new TestClient("",8888);
			tc.start();
		}

	}

}
