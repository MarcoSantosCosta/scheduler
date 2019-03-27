package persistence;

import Models.Robot;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class
DataBase {
    private static DataBase instance;
    private ArrayList<Robot> robots;
    private static String FILE_PATH = "scheduler.config";
    private String databse;

    private void loadDB() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                this.databse = (String) objectIn.readObject();
                objectIn.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.databse = "database.db";
        }
    }

    private DataBase() {
        this.robots = new ArrayList<>();
        this.loadDB();
        this.load();
    }

    public String getDatabse() {
        return databse;
    }

    public boolean setDatabse(String databse) {
        this.databse = databse + "\\databse.db";

        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_PATH, false);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.databse);
            objectOut.close();
            fileOut.close();
            this.load();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public ArrayList<Robot> getAllBots() {
        ArrayList<Robot> actives = new ArrayList<>();
        for (Robot aux : robots) {
            if (aux.isActive()) {
                actives.add(aux);
            }
        }
        return actives;
    }

    public Robot add(Robot robot) {
        this.robots.add(robot);
        this.save();
        return robot;
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.databse, false);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(robots);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nSALVANDO BASE DE DADOS \n");
    }

    public void load() {
        File file = new File(this.databse);
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                this.robots = (ArrayList<Robot>) objectIn.readObject();
                System.out.println("READ " + this.robots.size() + "FROM FILE");
                objectIn.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.robots = new ArrayList<>();
            System.out.println("READ " + this.robots.size() + "FROM FILE");
        }
    }

    public Robot find(int key) {
        for (Robot robot : robots) {
            if (robot.getKey() == key) {
                return robot;
            }
        }
        return null;
    }

    public Robot find(Robot robot) {
        return this.find(robot.getKey());
    }

    public void update(Robot robot) {
        Robot aux = this.find(robot);
        aux.setCode(robot.getCode());
        aux.setAbbreviation(robot.getAbbreviation());
        aux.setName(robot.getName());
        aux.setActive(robot.isActive());
        aux.setHour(robot.getHour());
        aux.setPathFile(robot.getPathFile());
        aux.setHour(robot.getHour());
        aux.setMinute(robot.getMinute());
        aux.setDaysOfWeek(robot.getDaysOfWeek());
        aux.setDaysOfMonth(robot.getDaysOfMonth());
        aux.setType(robot.getType());
        save();
    }
}
