package com2002.assignment.calendar.gui.extra;

import com2002.assignment.calendar.data.*;
import javax.swing.*;
import java.awt.*;

public class AppointmentTitle extends JLabel {
	private static final long serialVersionUID = 1L;
	private Appointment consult;

	public AppointmentTitle(Appointment consult)
	{
		super(consult.toString());
		this.consult = consult;
		setHorizontalAlignment(SwingConstants.LEFT);
		this.setBackground(Color.white);
	}
	
	public Appointment getAppointment() {
		return consult;
	}
	
	public String toString()
	{
		return this.getText();
	}
}