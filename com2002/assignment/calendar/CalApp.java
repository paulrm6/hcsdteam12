/**
*	CalApp.java
*
*	Created on November 24, 2001, 2:30 PM
**/

package com2002.assignment.calendar;

import com2002.assignment.calendar.gui.CalFrame;

/**
*	This launches the calendar application by running the main object.
*	@author  Paul Miles
*	@version  1.0
**/
public class CalApp {

	/** Creates new CalApp */
	public CalApp() {
	}
	
	/**
	* @param args the command line arguments
	**/
	public static void main (String args[]) {
		//Construct a CalFrame object and start the calendar running.
		try {
			CalFrame f = new CalFrame("Calendar");
			f.pack();
			f.run();
		}
		catch(Exception e) {
			e.printStackTrace();	
		}
	}
}