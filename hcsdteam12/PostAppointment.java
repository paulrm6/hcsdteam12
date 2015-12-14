package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Paul on 14/12/2015.
 */
public class PostAppointment {

    public PostAppointment() {
        String[] details = Database.getAppointment();
        if (details != null) {
            String date = details[0];
            String startTime = details[1];
            String endTime = details[2];

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                Statement stmt = con.createStatement();
                String query = "SELECT patientid FROM appointment  WHERE date='" + date + "' AND startTime='" + startTime + "' AND endTime='" + endTime + "';";
                ResultSet appointment = stmt.executeQuery(query);
                appointment.next();
                int patientid = appointment.getInt("patientid");
                appointment.close();
                int reply;
                do {
                    reply = JOptionPane.showConfirmDialog(null, "Would you like to add a treatment to this appointment?", null, JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        new AddTreatment(patientid);
                    }
                } while (reply == JOptionPane.YES_OPTION);
                String query2 = "UPDATE appointment SET seen=1 WHERE date='" + date + "' AND startTime='" + startTime + "' AND endTime='" + endTime + "';";
                stmt.executeUpdate(query2);
                JOptionPane.showMessageDialog(null, "Patient has been marked as seen.");
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
