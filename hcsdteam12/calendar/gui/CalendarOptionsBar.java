package hcsdteam12.calendar.gui;

/**
 * This class houses JToolBar of option tabs that enables the actionListeners to listen
 * for mouse input.
 * 
 * @author Seng Kin(Terence), Kong
 **/

import java.util.*;
import java.awt.event.*;	
import javax.swing.*;

public class CalendarOptionsBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	public static JButton makeAppointmentButton, cancelAppointmentButton, searchAppointmentButton, editAppointmentButton;
	public static JButton nextMonthButton, previousMonthButton, previousYearButton, nextYearButton;
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
		
		searchAppointmentButton = new JButton("Search An Appointment");
		add(searchAppointmentButton);
		toggles.addElement(searchAppointmentButton);
		
		editAppointmentButton = new JButton("Edit An Appointment");
		add(editAppointmentButton);
		toggles.addElement(editAppointmentButton);
		
		setListener(component);
		}
 
	//Every ActionListener objects is linked with every JButton in the calendar's options bar
	public void setListener(ActionListener listener) {
		for(int i = 0; i < toggles.size(); i++) {
			((JButton)toggles.elementAt(i)).addActionListener(listener);
		}
	}
}