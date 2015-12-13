/**
 * CalendarFrame is the main skeleton that contains mouse events handlers and components that determine the positioning of GUI components
 * within the calendar window. The GUI components include CalendarOptionsBar, AppointmentInfoWindow and CalendarPresentation.
 * It also uses DetailsManager to supervise the flow of logic and control throughout the calendar.
 *
 * @author Seng Kin (Terence), Kong 
 **/

package hcsdteam12.calendar.gui;

import hcsdteam12.calendar.data.*;
import hcsdteam12.calendar.gui.extra.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;
import java.io.*;
import java.util.GregorianCalendar;

public class CalendarFrame extends javax.swing.JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	protected CalendarPresentation display;
	protected AppointmentInfoWindow window;
	protected CalendarOptionsBar calendarOptionsBar;
	public boolean TIME;
	protected Calendar present;
	protected int presentDay, presentMonth, presentYear;
	private int stopYear = 0;
	private int startYear = 0;
	protected DetailsManager organiser;
	
	//When a new appointment is booked.
		public void makeAppointment(Appointment consult) {
			organiser.saveAppointment(consult);
		}

		//When an appointment is cancelled.
		public void deleteAppointment(Appointment consult) {
			organiser.deleteAppointment(consult);
		}
		
		//When an appointment is updated.
		public void updateAppointment(Appointment consult) {
			deleteAppointment(consult);
			makeAppointment(consult);
		}

	//Launches calendar frame and retrive information about current date from DetailsManager. 
	//In the process, it launches the GUI components into the frame. 
	public CalendarFrame(String caption) {
		super(caption);
		try{
			TIME = false; 
		
			//Launch the calendar's parts
			organiser = new DetailsManager();
			display = new CalendarPresentation(this);
			calendarOptionsBar = new CalendarOptionsBar(this);
			
			//Get information about current date
			present = Calendar.getInstance();
			present = Calendar.getInstance();
			presentDay = present.get(Calendar.DAY_OF_MONTH);
			presentMonth = present.get(Calendar.MONTH);
			presentYear = present.get(Calendar.YEAR);

			reviseCalendar(presentMonth, presentYear); //Display current month and year
			
			//Return the list of appointment for a given day.
			GregorianCalendar today = new GregorianCalendar(presentYear,presentMonth,presentDay);
			GregorianCalendar tomorrow = (GregorianCalendar)today.clone();
			tomorrow.add(Calendar.DAY_OF_MONTH,1);
			java.util.List L = organiser.obtainAppointmentBetweenDates(today,tomorrow);
			
			window = new AppointmentInfoWindow((Day)L.get(0),this); //Launches the appointment panel
			startComponents(); //Initialize all the frame's components
		}
		catch(IOException exclude) {
			System.err.println("IO Exception: " + exclude);
		}
		catch(Exception pass) { 
			System.err.println("Unknown Exception: " + pass);
			pass.printStackTrace();
		}
	}

	//Utilized for any input activity that runs in the calendar
	public void actionPerformed(java.awt.event.ActionEvent action) {	
		Object toggle = action.getSource();
		if(toggle == CalendarOptionsBar.makeAppointmentButton) {
			new AppointmentForm(this,"Add New Appointment",true).show();
		}

		if(toggle == CalendarOptionsBar.nextMonthButton) {
			if(presentMonth == 11) {
				presentMonth = 0;
				presentYear++;
				
			}
			else {
				presentMonth++;
				
			}
		}

		if(toggle == CalendarOptionsBar.previousMonthButton) {
			if(presentMonth == 0) {
					presentMonth = 11;
					presentYear--;
			}
			else {
				presentMonth--;
			}
		}

		if(toggle == CalendarOptionsBar.nextYearButton) {
			presentYear++;
		}

		if(toggle == CalendarOptionsBar.previousYearButton) {
			presentYear--;
		}

		if(toggle == CalendarOptionsBar.closeCalendar) {
			setVisible(false);
			dispose();
		}
		
		reviseCalendar(presentMonth,presentYear); //Retrieve a new set of calendar data after requesting for a specific month and year

		//Repaint in order for the CalendarPresentation to present the new calendar details.
		repaint();
		display.repaint();
	}
	
	//Utilized when there is an input coming from the mouse being clicked on.
	public void mouseClicked(MouseEvent object) {
		Object action = object.getSource();
		
		if (action instanceof JTable ) {
			Day illuminated = display.bringAppointmentsOnDay();
			window.addPicked(illuminated); //When a day is selected, it highlights the box in which the day sits in.   	 
		}
	}

	//These are the methods which are built into the class.
	public void mouseEntered(MouseEvent object) { }
	public void mouseExited(MouseEvent object) { }
	public void mousePressed(MouseEvent object) { }
	public void mouseReleased(MouseEvent object) { }
	
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
		enclose.add(window,BorderLayout.SOUTH);
		
		setLocation(350, 150); //Pinpoints where the calendar should appear on the monitor.
		pack();
	}

	private void closeWindow(java.awt.event.WindowEvent occur) {
		System.exit(0);
	}

	//Project the calendar onto the display. 
	public void run() {
		show();
	}

	//Retrieves a set of calendar data from the organiser for a specific date.
	public void reviseCalendar(int month, int year) {
		int firstDayOfMonth = Utilities.DayOneOfMonth(year, month); //Identifies the day where the 1st of a month sits on
		int indexDayOne = 1; //The first day in the calendar is indexed to 1
		int lastDayOfWeek = firstDayOfMonth; //Set the last day of the week to become the first day of the month
		
		//From firstDayOfMonth, calculate the number of extra days needed prior to that day to complete a week.
		int extraDaysPrior = firstDayOfMonth - Calendar.SUNDAY;
		int extraDaysAfter = Calendar.SATURDAY - lastDayOfWeek; //Similar to the one above, but opposite
		
		//starting at 0, loop a number of times = to the days in the month
		for(int i = 0; i < Utilities.RowsInMonth(year,month); i++) {
	
			//if the last day is on a saturday, "wrap around" to sunday
			if(lastDayOfWeek == Calendar.SATURDAY)
				lastDayOfWeek = Calendar.SUNDAY;
			else
				lastDayOfWeek++;  //otherwise, go to the next day (i.e. MONDAY -> TUESDAY)
		}
		
		//Used to determine the start and the last day in the calendar.
		int before, after;
		before = after = 0;
	
		//In the event that the last day of the week does not fall on a Sunday add extra days to have one-complete week
		if(extraDaysPrior > 0) {
			if(month == 0)
				before = 11;
			else
				before = -1;
			
			indexDayOne = Utilities.RowsInMonth(year, month+before) - extraDaysPrior;
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
			
			//Display the days before the 1st day of a month and the days after the last day of a month.
			display.establishPresentDay(organiser.obtainAppointmentBetweenDates(new GregorianCalendar(year-startYear, month+before, indexDayOne),
					new GregorianCalendar(year+stopYear, month+after, extraDaysAfter+1)));
			
			//Reset the variables to zero
			stopYear = startYear = 0;
		}
		
		//Ignore occurrence when an invalid date is generated
		catch(OutOfDateHandler pass) {
			System.out.print("Date exception: "); 
			System.err.print(pass);
		}
	}
}