package Models;

import persistence.DataBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Scheduler {




    private static Scheduler instance;
    private DataBase db;

    private Scheduler() {
        this.db = DataBase.getInstance();
    }


    public static synchronized Scheduler getInstance() {
        if (instance == null) {
            instance = new Scheduler();

        }
        return instance;
    }


    public void run(Robot robot) {
        robot.run();
        if(robot.getType() == Robot.Type.ONE_TIME){
            robot.remove();
        }
        db.save();
    }

    public void delete(Robot robot) {
        robot.remove();
        db.save();
    }


    public Robot getRobotToRun() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        for (Robot robot : db.getAllBots()) {
            if (robot.isActive() && robot.needRun()) {
                return robot;
            }
        }
        return null;
    }

    private int generateKey() {
        return db.getAllBots().size();
    }


    public Robot addSchedule(String code, String abbreviation, String personalName, String pathFile, int hour, int minute, ArrayList<Integer> daysOfWeek, ArrayList<Integer> daysOfMonth, Robot.Type type) {
        Robot robot = new Robot(this.generateKey(), code, abbreviation,personalName,true,pathFile,hour,minute,daysOfWeek,daysOfMonth,type);
        return this.db.add(robot);

    }
}

