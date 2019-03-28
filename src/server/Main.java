package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Main {
    public static void main(String[] args) throws IOException {

//        System.out.print("IP: ");
//        Scanner sc = new Scanner(System.in);
//        String IP = sc.nextLine();
//        try {
//            String a = "1";
//            while (!a.equals("0")) {
//                System.out.println("\nMSg: ");
//                a = sc.nextLine();
//                Socket server = new Socket(IP, 12345);
//                PrintWriter output = new PrintWriter(server.getOutputStream(), true);
//                output.write(a);
//                output.flush();
//                server.close();
//            }
//        } catch (Exception e) {
//            System.out.println("Erro: " + e.getMessage());
//        }


        String IP = args[0];
        int port = new Integer(args[1]);
        String message = args[2];

        Socket server = new Socket(IP, port);
        PrintWriter output = new PrintWriter(server.getOutputStream(), true);
        output.write(message);
        output.flush();
        server.close();
    }
}

