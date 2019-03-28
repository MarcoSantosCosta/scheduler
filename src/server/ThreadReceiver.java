package server;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.Socket;


public class ThreadReceiver implements Runnable {

    private Socket cliente;

    public ThreadReceiver(Socket cliente) {
        this.cliente = cliente;
    }

    public void run() {
        try {
            System.out.println("Nova conexão com o cliente " +
                    cliente.getInetAddress().getHostAddress()
            );
            BufferedReader input = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            System.out.println(input.readLine());
            cliente.close();
        } catch (Exception e) {
            System.out.println("Excecao ocorrida na thread: " + e);
            try {
                cliente.close();
            } catch (Exception ec) {
            }
        }
    }
}
