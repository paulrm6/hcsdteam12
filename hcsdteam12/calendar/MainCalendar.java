package hcsdteam12.calendar;

/**
*	Main class for the calendar program. Run this file in the compiler to generate the calendar program. 
*
*	@author Seng Kin(Terence), Kong
**/

import hcsdteam12.calendar.gui.CalendarFrame;

public class MainCalendar {
	public MainCalendar() {} //Create new MainCalendar.

	public static void main (String args[]) {
		try {
			CalendarFrame structure = new CalendarFrame("Calendar");
			structure.pack();
			structure.run();
		}
		catch(Exception error) {
			error.printStackTrace();	
		}
	}
}
