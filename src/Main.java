
import Models.Watcher;


import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        System.out.println("Watcher says: HI o/");
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        Watcher watcher = Watcher.getInstance();watcher.start();

    }

}
