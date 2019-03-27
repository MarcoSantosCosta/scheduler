package Teste;


import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;


public class Main {

    public static void main(String args[]) throws AWTException {

        Robot robot = new Robot();
        Point p = MouseInfo.getPointerInfo().getLocation();
        int movement = 1;
        robot.mouseMove(p.x + 1, p.y);

    }
}
