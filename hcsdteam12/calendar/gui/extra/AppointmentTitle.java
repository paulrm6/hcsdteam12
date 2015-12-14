package hcsdteam12.calendar.gui.extra;
/**
 * This JLabel is created only xto show the title of a selected appointment
 *
 * @author Seng Kin(Terence), Kong
 **/

import hcsdteam12.calendar.data.Appointment;

import javax.swing.*;
import java.awt.*;

public class AppointmentTitle extends JLabel {
    private static final long serialVersionUID = 1L;
    private Appointment consult;

    public AppointmentTitle(Appointment consult) {
        super(consult.toString());
        this.consult = consult;
        setHorizontalAlignment(SwingConstants.LEFT);
        this.setBackground(Color.white);
    }

    public Appointment getAppointment() {
        return consult;
    }

    public String toString() {
        return this.getText();
    }
}