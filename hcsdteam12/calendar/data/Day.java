package hcsdteam12.calendar.data;

/**
 * Day.java is a class that houses all the details of every apppointment for a given day.
 * 
 * @author Seng Kin (Terence), Kong
 */

import java.util.*;

//Stores all appointments for a given day in the calendar.
public class Day {
	//To maintain ordering of appointments
	LinkedList consultations;
	
	//Determine the validity of the Day object to hold appointment data.
	boolean valid;
	
	//Stores the calendar day, month and year
	int day;
	int month;
	int year;
	
	//Generates an invalid Day object.
	public Day() {
		valid = false;
	}

	//Creates a new Day object that can accepts and store appointments in the calendar.
	public Day(GregorianCalendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
	
		setCalendarDay(day);
		setCalendarMonth(month);
		setCalendarYear(year);
		setAppointments(new LinkedList());
		valid = true;
	}
	
	//Adds a new appointment to the Day object. The appointments are sorted by their start times. To allow recurring appointments to be 
	//added, it does not inspect to see if the appointment is actually scheduled to take place on that day itself.
	public void addAppointment(Appointment consult) {
		if (valid) {
			for (int i = 0; i < getAppointment().size(); i++) {
				if (((Appointment)getAppointment().get(i)).retrieveStartHour() > consult.retrieveStartHour()) {
					getAppointment().add(i, consult);
					return;
				}
			}
			getAppointment().add(consult);
		}
	}
	
	public boolean isDayLegit() {
	    return valid;
	}
	
	//The appointments are added after the CalDay is constructed. The appointments with no starting time are situated at the beginning.
	//Only then, appointments with specific starting times are sorted. 
	public Iterator iterator() {
	    if (isDayLegit()) {
	        return getAppointment().iterator();
	    }
	    else {
	        return null;
	    }
	}
	
	//Sets appointments
	private void setAppointments(LinkedList consultations) {
	    this.consultations = consultations;
	}
	
	//Sets day
	private void setCalendarDay(int day) {
	    this.day = day;
	}
	
	//Sets month
	private void setCalendarMonth(int month) {
	    this.month = month;
	}
	
	//Sets year
	private void setCalendarYear(int year) {
	    this.year = year;
	}
	
	//Gets appointments
	public LinkedList getAppointment() {
	    return consultations;
	}
	
	//Gets day
	public int getDay() {
	    return day;
	}
	
	//Gets month
	public int getMonth() {
	    return month;
	}
	
	//Get year
	public int getYear() {
	    return year;
	}
	
	//Prints out a string representation of the date in a day/month/year format
	public String toString() {
	    return getDay() + "/" + (getMonth()+1) + "/" + getYear();
	}
}