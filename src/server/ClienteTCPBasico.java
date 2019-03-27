package server;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCPBasico {
    public static void main(String[] args) {

        System.out.print("IP: ");
        Scanner sc = new Scanner(System.in);
        String IP = sc.nextLine();
        try {
            Socket server = new Socket(IP, 12345);
            ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
            String a = "1";
            while (!a.equals("0")) {
                System.out.println("\nMSg: ");
                a = sc.nextLine();
                output.writeObject(a);
                output.flush();
                output.reset();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

