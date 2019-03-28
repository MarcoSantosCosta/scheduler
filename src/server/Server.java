package server;

import server.ThreadReceiver;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {


    private int port;
    private ServerSocket server;

    public Server(int port) {
        this.port = port;

    }

    public void start() {
        try {
            this.server = new ServerSocket(this.port);
            System.out.println("server started");
            while (true) {
                Socket cliente = this.server.accept();
                System.out.println("NEW CONECTION: "+ cliente.getInetAddress());
                new ThreadReceiver(cliente).run();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }
}