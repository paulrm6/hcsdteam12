package hcsdteam12.calendar.gui;

/**
 * This class houses JToolBar of option tabs that enables the actionListeners to listen
 * for mouse input.
 *
 * @author Seng Kin(Terence), Kong
 * Extra button added by Paul MacDonald
 **/

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CalendarOptionsBar extends JToolBar {
    private static final long serialVersionUID = 1L;
    public static JButton makeAppointmentButton, cancelAppointmentButton, dentistButton,
                            hygienistButton, secretaryButton;
    private Vector toggles;

    // Launches every JButton objects and inserts them into the calendar' options bar
    public CalendarOptionsBar(ActionListener component) {
        super();
        toggles = new Vector(6);

        makeAppointmentButton = new JButton("Insert New Appointment");
        toggles.addElement(makeAppointmentButton);
        add(makeAppointmentButton);

        cancelAppointmentButton = new JButton("Delete An Appointment");
        add(cancelAppointmentButton);
        toggles.addElement(cancelAppointmentButton);

        dentistButton = new JButton("Dentist");
        toggles.addElement(dentistButton);
        add(dentistButton);

        hygienistButton = new JButton("Hygienist");
        toggles.addElement(hygienistButton);
        add(hygienistButton);

        secretaryButton = new JButton("Secretary");
        toggles.addElement(secretaryButton);
        add(secretaryButton);

        setListener(component);
    }

    //Every ActionListener objects is linked with every JButton in the calendar's options bar
    public void setListener(ActionListener listener) {
        for (int i = 0; i < toggles.size(); i++) {
            ((JButton) toggles.elementAt(i)).addActionListener(listener);
        }
    }
}