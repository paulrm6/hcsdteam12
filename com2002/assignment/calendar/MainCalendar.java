package com2002.assignment.calendar;

import com2002.assignment.calendar.gui.CalendarFrame;

public class MainCalendar {
	public MainCalendar() {}
	
	public static void main (String args[]) {
		//Construct a CalFrame object and start the calendar running.
		try {
			CalendarFrame structure = new CalendarFrame("Calendar");
			structure.pack();
			structure.run();
		}
		catch(Exception e) {
			e.printStackTrace();	
		}
	}
}