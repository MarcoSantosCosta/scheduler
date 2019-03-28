package Models;

import controllers.WatcherListener;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class Watcher {

    private static Watcher instance;
    private Scheduler scheduler;
    private static int status = -1;

    private Watcher() {
        this.scheduler = Scheduler.getInstance();
    }

    private static ArrayList<WatcherListener> listeners;

    public static void addListener(WatcherListener e) {
        listeners.add(e);
    }

    public void update() {
        for (WatcherListener e : listeners) {
            e.onUpdate();
        }
    }

    public static synchronized Watcher getInstance() {
        if (instance == null) {
            listeners = new ArrayList<>();
            instance = new Watcher();
        }
        return instance;
    }

    public void pause() {
        status = 0;
        update();
    }

    public void stop() {
        status = -1;
        update();
    }

    public static String getStatus() {
        switch (Watcher.status) {
            case -1:
                return "Stopped";
            case 0:
                return "Paused";
            case 1:
                return "Running";
            default:
                return "Unknow";
        }
    }


    private int movement = 1;
    private int countMove = 0;

    private void moveMouse() {
        try {
            countMove++;
            if (countMove > 12) {
                java.awt.Robot robot = null;
                try {
                    robot = new java.awt.Robot();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                Point p;
                p = MouseInfo.getPointerInfo().getLocation();
                System.out.println(new Date().toString() + " Moving mouse from X: " + p.x + " Y: " + p.y);
                Logger.log(" Moving mouse X: " + p.x + " Y: " + p.y, true);
                assert robot != null;
                robot.mouseMove(p.x + movement, p.y);
                p = MouseInfo.getPointerInfo().getLocation();
                movement = -movement;
                countMove = 0;
            }
        } catch (Exception e) {
            Logger.log(e.getCause().toString(), true);
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println(new Date().toString() + " Watcher says: Hello ;) ...");
        Logger.log(" Watcher says: Hello ;) ...", true);
        if (Watcher.status != 1) {
            Watcher.status = 1;
            new Thread() {
                @Override
                public void run() {

                    while (Watcher.status != -1) {
                        if (Watcher.status == 1) {
                            Robot run = scheduler.getRobotToRun();
                            if (run != null) {
                                scheduler.run(run);
                                Logger.log(" Running: " + run.getName(), true);
                                System.out.println(new Date().toString() + " Running: " + run.getName());
                                update();
                            } else {
                                System.out.println(new Date().toString() + " NOTHING TO DO");
                                Logger.log(" NOTHING TO DO", true);
                            }

                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                Logger.log(e.getMessage(), true);
                                System.out.println(new Date().toString() + e.getMessage());
                            }
                        }
                        moveMouse();
                    }
                    System.out.println(new Date().toString() + " Watcher say: God Bye o/");
                    Logger.log(" Watcher say: God Bye o/", true);
                }
            }.start();
        } else {
            System.out.println("Watcher already runing...");
        }
    }

    public void clearListners() {
        listeners.clear();
    }
}
