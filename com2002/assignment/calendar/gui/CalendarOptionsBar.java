package com2002.assignment.calendar.gui;

import java.util.*;
import java.awt.event.*;	
import javax.swing.*;

public class CalendarOptionsBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	/** A Jbutton for placing a new appointment into the calendar**/
	public static JButton newAppointmentButton;
	
	/** A Jbutton for removing an appointment from the calendar **/
	public static JButton deleteAppointmentButton;
	
	/** A Jbutton for viewing the previous month **/
	public static JButton previousMonthButton;
	
	/** A Jbutton for viewing the next month **/
	public static JButton nextMonthButton;
	
	/** A Jbutton for causing the previous year to viewed **/
	public static JButton previousYearButton;
	
	/** A Jbutton for causing the next year to viewed **/
	public static JButton nextYearButton;
	
	/** stores the JButtons for iterator access **/
	private Vector buttons;

	/** Initializes every member JButton and adds it to the JToolBar **/
	public CalendarOptionsBar(ActionListener component ) {

		super();
		buttons = new Vector(6);
		
		newAppointmentButton = new JButton("New Appointment");
		buttons.addElement(newAppointmentButton);
		add(newAppointmentButton);
		
		deleteAppointmentButton = new JButton("Delete Appointment");
		add(deleteAppointmentButton);
		buttons.addElement(deleteAppointmentButton);
		
		previousMonthButton = new JButton("Previous Month");
		add(previousMonthButton);
		buttons.addElement(previousMonthButton);
		
		nextMonthButton = new JButton("Next Month");
		add(nextMonthButton);
		buttons.addElement(nextMonthButton);
		
		previousYearButton = new JButton("Previous Year");
		add(previousYearButton);
		buttons.addElement(previousYearButton);
		
		nextYearButton = new JButton("Next Year");
		add(nextYearButton);
		buttons.addElement(nextYearButton);
		
		setListener(component);
		}

	/** Enables ActionListener objects to be associated as a listener with every JButton in the toolbar**/ 
	public void setListener(ActionListener listener) {
		for(int i = 0; i < buttons.size(); i++) {
			((JButton)buttons.elementAt(i)).addActionListener(listener);
		}
	}
}