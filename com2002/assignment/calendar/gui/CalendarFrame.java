package com2002.assignment.calendar.gui;

/**
 * CalendarFrame is the main skeleton that contains mouse events handlers and components that determine the positioning of GUI components
 * within the calendar window. The GUI components include CalendarOptionsBar, AppointmentInfoWindow and CalendarPresentation.
 * It also uses DetailsManager to supervise the flow of logic and control throughout the calendar.
 * */

import com2002.assignment.calendar.data.*;
import com2002.assignment.calendar.gui.extra.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CalendarFrame extends javax.swing.JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	protected CalendarPresentation display;
	protected AppointmentInfoWindow appointmentBoard;	
	protected CalendarOptionsBar calendarOptionsBar;
	protected Calendar present;
	public boolean TIME;
	protected int presentDay;
	protected int presentMonth;
	protected int presentYear;
	private int stopYear = 0;
	private int startYear = 0;
	protected DetailsManager organiser;
	
	//When a new appointment is booked.
	public void makeAppointment(Appointment consult) {
		organiser.saveAppointment(consult);
	}
	
	//When an appointment is cancelled
	public void deleteAppointment(Appointment consult) {
		organiser.deleteAppointment(consult);
	}
	
	//When an appointment is updated
	public void updateAppointment(Appointment consult) {
		deleteAppointment(consult);
		makeAppointment(consult);
	}
	
	public CalendarFrame(String title) {
		super(title);
		try {
			TIME = false;
			//Launch the calendar's parts
			organiser = new DetailsManager();
			display = new CalendarPresentation(this);
			calendarOptionsBar = new CalendarOptionsBar(this);
			
			//Get information about current date
			present = Calendar.getInstance();
			presentDay = present.get(Calendar.DAY_OF_MONTH);
			presentMonth = present.get(Calendar.MONTH);
			presentYear = present.get(Calendar.YEAR);
	
			//Display current month and year
			reviseCalendar(presentMonth, presentYear);
			
			//Return a list of appointments for a given day
			GregorianCalendar today = new GregorianCalendar(presentYear,presentMonth,presentDay);
			GregorianCalendar tomorrow = (GregorianCalendar)today.clone();
			tomorrow.add(Calendar.DAY_OF_MONTH,1);
			java.util.List L = organiser.getApptRange(today,tomorrow);
			
			//Launches the appointment panel
			appointmentBoard = new AppointmentInfoWindow((Day)L.get(0),this);
			
			//Initialize all the functions
			startComponents();
		}
		catch(IOException exclude) {
			System.err.println("IO Exception: " + exclude);
		}
		catch(Exception pass) { 
			System.err.println("Unknown Exception: " + pass);
			pass.printStackTrace();
		}
	}	

    /** Utilized for any input activity that runs in the calendar **/
	public void actionPerformed(java.awt.event.ActionEvent object) {	
		Object action = object.getSource();
//		if(action == CalendarOptionsBar.makeAppointmentButton) {
//			new AppointmentForm(this,"Add New Appointment",true).show();
//		}

		if(action == CalendarOptionsBar.nextMonthButton) {
			if(presentMonth == 11) {
				presentMonth = 0;
				presentYear++;
			}
			else {
				presentMonth++;	
			}
		}

		if(action == CalendarOptionsBar.previousMonthButton) {
			if(presentMonth == 0) {
				presentMonth = 11;
					presentYear--;
			}
			else {
				presentMonth--;
			}
		}

		if(action == CalendarOptionsBar.nextYearButton) {
			presentYear++;
		}

		if(action == CalendarOptionsBar.previousYearButton) {
			presentYear--;
		}

		//Retrieve a new set of calendar data after requesting for a specific month and year
		reviseCalendar(presentMonth,presentYear);

		//Repaint in order for the CalendarPresentation to present the new calendar details.
		repaint();
		display.repaint();
	}
   
	//Utilized when there is an input coming from the mouse being clicked on
	public void mouseClicked(MouseEvent object) {
		Object action = object.getSource();
		
		//When a day is selected, it highlights the box in which the day sits in.
		if (action instanceof JTable ) {
			Day illuminate = display.getSelectedDay();
			appointmentBoard.addPicked(illuminate);   	 
		}
	}

	//These are the methods which are built into the class.
	public void mouseEntered(MouseEvent object) {}
	public void mouseExited(MouseEvent object) {}
	public void mousePressed(MouseEvent object) {}
	public void mouseReleased(MouseEvent object) {}
	
	//Private method that launches all the GUI components to display the design of the calendar window.
	private void startComponents() {		
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent occur) {
				closeWindow(occur);
			}
		});

		Container enclose = getContentPane();		
		enclose.add(calendarOptionsBar, BorderLayout.NORTH);
		enclose.add(display, BorderLayout.CENTER);
		enclose.add(appointmentBoard,BorderLayout.SOUTH);
		
		//Pinpoints where the calendar should appear on the monitor.
		setLocation(350, 100);
		pack();
	}
		
	//When the window is closed.
	private void closeWindow(java.awt.event.WindowEvent occur) {
		System.exit(0);
	}

	// Project the calendar onto the display 
	public void run() {
		show();
	}
	
	public void reviseCalendar(int month, int year) {		
		int firstDayOfMonth = CalendarUtil.DayOneOfMonth(year, month);
		int indexDayOne = 1; //The first day in the calendar is indexed to 1.
		int lastDayOfWeek = firstDayOfMonth; //Set the last day of the week to become the first day of the month
		//From firstDayOfMonth, calculate the number of extra days needed prior to that day to complete a week.
		int extraDaysPrior = firstDayOfMonth - Calendar.SUNDAY;
		//Similar to the one above, but opposite
		int extraDaysAfter = Calendar.SATURDAY - lastDayOfWeek;
		
		//Beginning from zero, the number of days in a month matches the number of loops 
		for(int i = 0; i < CalendarUtil.TotalDaysInMonth(year, month); i++) {
			if(lastDayOfWeek == Calendar.SATURDAY) 
				lastDayOfWeek = Calendar.SUNDAY; //Configure so that the last week of the day is actually a Sunday, not Saturday.
			else
				lastDayOfWeek++;
		}

		//Used to determine the start and the last day in the calendar
		int before, after;
		before = after = 0;

		//In the event that the last day of the week does not fall on a Sunday add extra days to have one-complete week
		if(extraDaysPrior > 0) {
			if(month == 0)
				before = 11;
			else
				before = -1;
			indexDayOne = CalendarUtil.TotalDaysInMonth(year, month+before) - extraDaysPrior;
		}
		
		//Similar to the if statement previously but if it does not fall on a Saturday.
		if(extraDaysAfter > 0) {
			if(month == 11) {
				after = -11;
			}
			else {
				after = 1;
			}
		}
	
		//Retrieve the dates before passing onto the DisplayBoard.
		try {
			if(presentMonth == 11)
				stopYear = 1;
			if(presentMonth == 0)
				startYear = 1;
	
			//display the days before the 1st day of the month and the days after the last day of a month.
			display.setCurrentDays(organiser.getApptRange(new GregorianCalendar(year-startYear, month+before, indexDayOne),
					new GregorianCalendar(year+stopYear, month+after, extraDaysAfter+1)));
			stopYear = startYear = 0;
		}
		
		//Ignore occurrences when an invalid date is generated
		catch(DateOutOfRangeException pass) {
			System.out.print("Date exception"); 
			System.err.print(pass);
		}
	}
}