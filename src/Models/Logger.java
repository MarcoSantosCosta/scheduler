package Models;

import java.io.*;
import java.util.Date;

public class Logger{

    public  static void log(String string, boolean timestamp){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("logfile.txt", true));
            writer.append("\n");
            if(timestamp){
                writer.append(new Date().toString()+": ");
            }
            writer.append(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
