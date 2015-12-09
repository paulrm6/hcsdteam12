package com2002.assignment.calendar.gui.extra;

/** 
 * A class that shows the details of for a specific set of appointments 
 * **/

import com2002.assignment.calendar.data.*;
import com2002.assignment.calendar.gui.*; 

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AppointmentInfoWindow extends JPanel implements MouseListener {
	private JList appointmentWindow;
	private JScrollPane scroll = new JScrollPane();
	private static final long serialVersionUID = 1L;
	private CalendarFrame frame;
	private LinkedList consult;
	private DefaultListModel duplicate;
	public void mousePressed(MouseEvent e) {} 
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {} 
	
	public AppointmentInfoWindow(Day day, CalendarFrame frame2) {
		super( ); 
		frame = frame2;
		if(day != null) {
			launchAppointmentElements(day);
		} 
	}
	
	//Launches the components and display the appointment window.
	private void launchAppointmentElements(Day day) {
		setLayout(new BorderLayout());
		duplicate = new DefaultListModel();
		consult = day.getAppointment();
		
		ListIterator iterlist = consult.listIterator();
		
		while(iterlist.hasNext()) {
			Appointment start = (Appointment)iterlist.next();
			duplicate.addElement(new AppointmentTitle(start) );    	 
		}
		
		appointmentWindow = new JList(duplicate);
		appointmentWindow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		appointmentWindow.setFixedCellWidth(725);
		appointmentWindow.addMouseListener(this); 
		scroll = new JScrollPane(appointmentWindow);     
		add(scroll, BorderLayout.CENTER);
		setVisible(true);      
	}
	
	//Rerun all the components in the appointment window.
	private void relaunchElements(Day day) {
		duplicate.clear();
		consult = day.getAppointment();
		ListIterator traverser = consult.listIterator();
		
		while(traverser.hasNext()) {
			Appointment cur = (Appointment)traverser.next();
			duplicate.addElement(new AppointmentTitle(cur) );    	 
		}    
	}      
	
	//Manages event when the mouse cursor clicks on "Alter Appointment".
	public void mouseClicked(MouseEvent object) {
		if (object.getClickCount() == 2) {
			Appointment pressed = ((AppointmentTitle)appointmentWindow.getSelectedValue()).getAppointment();
			new AppointmentForm(frame, "Alter Appointment", true, pressed).show();
		}
	}

	public void addPicked(Day day) {
		relaunchElements(day);
	}
	
	public Appointment obtainPicked() {
		  return ((AppointmentTitle)appointmentWindow.getSelectedValue() ).getAppointment();
	} 
}