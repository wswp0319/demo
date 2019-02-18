package com.jmhqmc.demo.net;

public class ServerManager
{
    private AbstractNettyServer tcpServer;

    public ServerManager() {
        tcpServer = (AbstractNettyServer)AppContext.getBean(AppContext.TCP_SERVER);
    }

    public void startServer(int port) throws Exception {
        tcpServer.startServer(port);
    }

    public void startServer() throws Exception {
        tcpServer.startServer();
    }
    public void stopServer() throws Exception {
        tcpServer.stopServer();
    }
}