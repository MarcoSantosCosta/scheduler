package server;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class ThreadReceiver {


    public static void main(String[] args) {
        System.out.println("Servidor");
        try {

            ServerSocket servidor = new ServerSocket(12345);
            System.out.println(servidor.getInetAddress());
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
            ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());
            while (true) {
                String content = (String) input.readObject();
                System.out.println(content);

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}
