package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Paul on 14/12/2015.
 */
public class AddTreatment {

    public AddTreatment(int patientID) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String queryName = "SELECT forename, surname FROM patients WHERE id='"+patientID+"'";
            ResultSet patient = stmt.executeQuery(queryName);
            patient.next();
            String forename = patient.getString("forename");
            String surname = patient.getString("surname");
            String query3 = "SELECT name FROM treatments;";
            ResultSet treatments = stmt.executeQuery(query3);
            if (treatments.next()) {
                treatments.last();
                String[] treatmentList = new String[treatments.getRow()+1];
                treatments.absolute(0);
                int i = 0;
                while (treatments.next()) {
                    treatmentList[i] = treatments.getString("name");
                    i += 1;
                }
                String treatment = (String)  JOptionPane.showInputDialog(null, "Add a treatment for "+forename+" "+surname+"below:", "Treatments", JOptionPane.QUESTION_MESSAGE, null,
                        treatmentList, "Select a treatment");
                String query5;
                if (treatment != "Select a treatment") {
                    query5 = "INSERT into treatments_given (patientid, treatment_name) VALUES ('"+patientID+"','"+treatment+"')";
                    stmt.executeUpdate(query5);
                } else {
                    JOptionPane.showMessageDialog(null, "You must select a treatment");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No treatment plans exist.");
            }
            stmt.close();
            con.close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
