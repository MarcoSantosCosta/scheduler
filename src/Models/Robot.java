package Models;

import persistence.DataBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Robot implements Serializable {


    public void setDaysOfMonth(ArrayList<Integer> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {

        DAILY(1, "Daily"), WEEKLY(2, "Weekly"), MONTHLY(3, "Monthly"), ONE_TIME(4, "One Time Only");
        private int code;
        private String name;

        Type(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private int key;
    private String code;
    private String abbreviation;
    private String name;
    private boolean active;


    private String pathFile;
    private Calendar lastRun;
    private int hour;
    private int minute;
    private ArrayList<Integer> daysOfWeek;
    private ArrayList<Integer> daysOfMonth;
    private Type type;



    public Type getType() {
        return type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public void setDaysOfWeek(ArrayList<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    



    public Robot(int key, String code, String abbreviation, String name, boolean active, String pathFile, int hour, int minute, ArrayList<Integer> daysOfWeek, ArrayList<Integer> daysOfMonth, Type type) {
        this.key = key;
        this.code = code;
        this.abbreviation = abbreviation;
        this.name = name;
        this.active = active;
        this.pathFile = pathFile;
        this.hour = hour;
        this.minute = minute;
        this.daysOfWeek = daysOfWeek;
        this.daysOfMonth = daysOfMonth;
        this.lastRun = Calendar.getInstance();
        this.lastRun.setTime(new Date());
        this.type = type;
    }


    public String getPathFile() {
        return pathFile;
    }

    public Calendar getLastRun() {
        return lastRun;
    }

    public ArrayList<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public ArrayList<Integer> getDaysOfMonth() {
        return daysOfMonth;
    }

    public int getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }


    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void remove() {
        this.active = false;
    }

    public void update() {
        DataBase.getInstance().update(this);
    }


    //TO TABLE
    public String getHourString() {
        int aux = this.hour;
        String h = ((aux < 10) ? "0" : "") + aux;
        aux = this.minute;
        String m = ((aux < 10) ? "0" : "") + aux;
        return h + ":" + m;
    }

    public String getTypeString() {
        return this.type.toString();
    }

    public String getLastRunString() {
        if (this.lastRun != null) {
            int day = this.lastRun.get(Calendar.DAY_OF_MONTH);
            int month = this.lastRun.get(Calendar.MONDAY);
            int year = this.lastRun.get(Calendar.YEAR);
            int hour = this.lastRun.get(Calendar.HOUR_OF_DAY);
            int min = this.lastRun.get(Calendar.MINUTE);
            return ((day < 10) ? "0" + day : day) + "/" + ((month < 10) ? "0" + month : month) + "/" + year + " - " + ((hour < 10) ? "0" + hour : hour) + ":" + ((min < 10) ? "0" + min : min);
        } else {
            return "Nothing to Show";
        }
    }

    public String getNameString() {
        return this.code + " - " + this.abbreviation + " - " + this.name;
    }

    public String getPathFileString() {
        return this.pathFile;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return minute;
    }

    /////RUNN

    private boolean hourTest(Calendar now, Calendar schedule) {
        return schedule.before(now);
    }

    private boolean dayOfMonthTest(Calendar now) {
        if (this.daysOfMonth.size() == 0) {
            return true;
        } else {
            return this.daysOfMonth.contains(now.get(Calendar.DAY_OF_MONTH));
        }
    }

    private boolean dayOfWeekTest(Calendar now) {
        return this.daysOfWeek.contains(now.get(Calendar.DAY_OF_WEEK));
    }

    private boolean lastRunTest(Calendar schedule) {
        return this.lastRun.before(schedule);
    }

    public boolean needRun() {
        Calendar now = Calendar.getInstance();

        Calendar schedule = Calendar.getInstance();
        schedule.setTime(new Date());
        schedule.set(Calendar.HOUR_OF_DAY, this.hour);
        schedule.set(Calendar.MINUTE, this.minute);
        schedule.set(Calendar.SECOND, 0);

        now.setTime(new Date());
        return (hourTest(now, schedule) && dayOfMonthTest(now) && dayOfWeekTest(now) && lastRunTest(schedule));
    }

    public boolean run() {
        this.lastRun.setTime(new Date());
        Process p;
        try {
            p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + this.pathFile);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
