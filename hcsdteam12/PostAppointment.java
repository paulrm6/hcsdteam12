package hcsdteam12;

import javax.swing.*;

/**
 * Created by Paul on 14/12/2015.
 */
public class PostAppointment {

    public PostAppointment() {
        Appointment appointment = Database.getAppointment();
        if (appointment != null) {
            int reply;
            do {
                reply = JOptionPane.showConfirmDialog(null, "Would you like to add a treatment to this appointment?", null, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    new AddTreatment(appointment.getPatientid());
                }
            } while (reply == JOptionPane.YES_OPTION);
            appointment.setSeen(1);
            if (appointment.update()) {
                JOptionPane.showMessageDialog(null, "Patient has been marked as seen.");
            }
        }

    }
}
