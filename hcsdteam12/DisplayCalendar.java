package hcsdteam12;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paul on 14/12/2015.
 */
public class DisplayCalendar {
    String type, date;
    public DisplayCalendar(String type){
        this(type, getTodaysDate());
    }
    public DisplayCalendar(String type, String date) {
        this.type = type;
        this.date = date;
    }
    private static String getTodaysDate() {
        Date date = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static void main(String[] args) {
        getTodaysDate();
    }
}
