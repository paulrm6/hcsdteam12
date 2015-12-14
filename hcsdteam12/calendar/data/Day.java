package hcsdteam12.calendar.data;

/**
 * Day.java is a class that houses all the details of every apppointment for a given day.
 *
 * @author Seng Kin (Terence), Kong
 **/

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

//Stores all appointments for a given day in the calendar.
public class Day {
    LinkedList consultations; //To maintain ordering of appointments
    boolean valid;
    int day, month, year;

    //Generates an invalid Day object.
    public Day() {
        valid = false;
    }

    //Creates a new Day object that can accepts and store appointments in the calendar.
    public Day(GregorianCalendar cal) {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        establishCalendarDay(day);
        establishCalendarMonth(month);
        establishCalendarYear(year);
        establishAppointments(new LinkedList());
        valid = true;
    }

    //Adds a new appointment to the Day object. The appointments are sorted by their start times. To allow recurring appointments to be
    //added, it does not inspect to see if the appointment is actually scheduled to take place on that day itself.
    public void addAppointment(Appointment consult) {
        if (valid) {
            for (int i = 0; i < retrieveAppointment().size(); i++) {
                if (((Appointment) retrieveAppointment().get(i)).retrieveStartHour() > consult.retrieveStartHour()) {
                    retrieveAppointment().add(i, consult);
                    return;
                }
            }
            retrieveAppointment().add(consult);
        }
    }

    public boolean isDayLegit() {
        return valid;
    }

    //The appointments are added after the CalDay is constructed. The appointments with no starting time are situated at the beginning.
    //Only then, appointments with specific starting times are sorted.
    public Iterator iterator() {
        if (isDayLegit()) {
            return retrieveAppointment().iterator();
        } else {
            return null;
        }
    }

    private void establishAppointments(LinkedList consultations) {
        this.consultations = consultations;
    }

    private void establishCalendarDay(int day) {
        this.day = day;
    }

    private void establishCalendarMonth(int month) {
        this.month = month;
    }

    private void establishCalendarYear(int year) {
        this.year = year;
    }

    public LinkedList retrieveAppointment() {
        return consultations;
    }

    public int retrieveDay() {
        return day;
    }

    public int retrieveMonth() {
        return month;
    }

    public int retrieveYear() {
        return year;
    }

    //Prints out a string representation of the date in a day/month/year format
    public String toString() {
        return retrieveDay() + "/" + (retrieveMonth() + 1) + "/" + retrieveYear();
    }
}