package demo;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZKTest {

    public static void main(String[] args) {
        String connectString = "127.0.0.1:2181";
        int sessionTimeout = 4000;
        Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                //System.out.println(event.getPath());
            }
        };

        try {
            ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
            List<String> list = zooKeeper.getChildren("/dubbo/com.jmhqmc.demo.service.CallService/providers", false);
            for (String path : list) {
                System.out.println(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}