package com2002.assignment.calendar.data;

import java.util.*;

/** Collection of all of the appointments for the calendar day */
public class Day {
	//LinkedList is used to preserve order
	LinkedList appts;
	
	/** Determine the validity of a given calendar day */
	boolean valid;
	
	/** Stores the calendar day, month and year */
	int day;
	int month;
	int year;
	
	/** Build an invalid Day object */
	public Day() {
		valid = false;
	}
	
	/** Creates a new Day which can accepts appointments */
	public Day(GregorianCalendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		setDay(day);
		setMonth(month);
		setYear(year);
		setAppointments(new LinkedList());
	
		valid = true;
	}
	
	/**
	 * Adds a new appointment to the Day object. The appointments are sorted by their start times. To allow recurring appointments to be 
	 * added, it does not inspect to see if the appointment is actually scheduled to take place on that day itself.
	 */
	public void addAppointment(Appointment appt) {
		if (valid) {
			for (int i = 0; i < getAppointment().size(); i++) {
				if (((Appointment)getAppointment().get(i)).getStartHour() > appt.getStartHour()) {
					getAppointment().add(i, appt);
					return;
				}
			}
		    //Add the appointment if it has not been added yet
			getAppointment().add(appt);
		}
	}
	
	public boolean isValid() {
	    return valid;
	}
	
	/**
	 * The appointments are added after the CalDay is constructed. The appointments with no starting time are situated at the beginning.
	 * Only then, appointments with specific starting times are sorted. 
	 */
	public Iterator iterator() {
	    if (isValid()) {
	        return getAppointment().iterator();
	    }
	    else {
	        return null;
	    }
	}
	
	/** Sets appointments */
	private void setAppointments(LinkedList appts) {
	    this.appts = appts;
	}
	
	/** Sets day of the appointment */
	private void setDay(int day) {
	    this.day = day;
	}
	
	/** Sets month of the appointment */
	private void setMonth(int month) {
	    this.month = month;
	}
	
	/** Sets year of the appointment */
	private void setYear(int year) {
	    this.year = year;
	}
	
	/** Obtain appointments */
	public LinkedList getAppointment() {
	    return appts;
	}
	
	/** Gets the day of the appointment */
	public int getDay() {
	    return day;
	}
	
	/** Get the month of the appointment. January-December is indexed from 0 to 11 **/
	public int getMonth() {
	    return month;
	}
	
	/** Gets year of the appointment */
	public int getYear() {
	    return year;
	}
	
	/** Prints out a string representation of the date in a day/month/year format*/
	public String toString() {
	    return  getDay() + "/" + (getMonth()+1) + "/" + getYear();
	}
}