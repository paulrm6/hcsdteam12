package com2002.assignment.calendar.gui;

import java.util.*;
import java.awt.event.*;	
import javax.swing.*;

public class CalendarOptionsBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	public static JButton makeAppointmentButton;
	public static JButton cancelAppointmentButton;
	public static JButton previousMonthButton;
	public static JButton nextMonthButton;
	public static JButton previousYearButton;
	public static JButton nextYearButton;
	private Vector toggles;

	// Launches every JButton objects and inserts them into the calendar' options bar 
	public CalendarOptionsBar(ActionListener component ) {
		super();
		toggles = new Vector(6);
		
		makeAppointmentButton = new JButton("Insert New Appointment");
		toggles.addElement(makeAppointmentButton);
		add(makeAppointmentButton);
		
		cancelAppointmentButton = new JButton("Delete An Appointment");
		add(cancelAppointmentButton);
		toggles.addElement(cancelAppointmentButton);
		
		previousMonthButton = new JButton("Previous Month");
		add(previousMonthButton);
		toggles.addElement(previousMonthButton);
		
		nextMonthButton = new JButton("Next Month");
		add(nextMonthButton);
		toggles.addElement(nextMonthButton);
		
		previousYearButton = new JButton("Previous Year");
		add(previousYearButton);
		toggles.addElement(previousYearButton);
		
		nextYearButton = new JButton("Next Year");
		add(nextYearButton);
		toggles.addElement(nextYearButton);
		
		setListener(component);
		}
 
	//Every ActionListener objects is linked with every JButton in the calendar's options bar
	public void setListener(ActionListener listener) {
		for(int i = 0; i < toggles.size(); i++) {
			((JButton)toggles.elementAt(i)).addActionListener(listener);
		}
	}
}