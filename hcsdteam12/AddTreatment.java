package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Paul on 14/12/2015.
 */
public class AddTreatment {

    public AddTreatment() {
        String postcode = JOptionPane.showInputDialog(null, "Enter the patients postcode:");
        String name;
        postcode = postcode.replaceAll("\\s","");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname, addressid FROM patients JOIN address ON patients.addressid = address.id WHERE postcode='"+postcode+"';";
            ResultSet patients = stmt.executeQuery(query);
            if (patients.next()) {
                patients.last();
                String[] patientList = new String[patients.getRow()];
                patients.absolute(0);
                int i = 0;
                while (patients.next()) {
                    String fore = patients.getString("forename");
                    String sur = patients.getString("surname");
                    String addressid = patients.getString("addressid");
                    String fullDetails = fore+","+sur+","+addressid;
                    patientList[i] = fullDetails;
                    i += 1;
                }
                name = (String) JOptionPane.showInputDialog(null, "Select the patient", "View Patient", JOptionPane.QUESTION_MESSAGE,
                        null, patientList, patientList[0]);
            } else {
                JOptionPane.showMessageDialog(null, "No patients live at this address");
                return;
            }
            patients.close();
            String forename = name.split(",")[0];
            String surname = name.split(",")[1];
            String addressid = name.split(",")[2];
            String query2 = "SELECT id FROM patients WHERE forename='"+forename+"' AND surname='"+surname+"' AND addressid='"+addressid+"';";
            ResultSet patient = stmt.executeQuery(query2);
            patient.next();
            int patientId = patient.getInt("id");
            patient.close();
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
                    query5 = "UPDATE treatments_given SET patientid='"+patientId+"', treatment_name='"+treatment+"'";
                    stmt.executeUpdate(query5);
                } else {
                    JOptionPane.showMessageDialog(null, "You must select a treatment");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No treatment plans exist.");
            }
            patients.close();
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
