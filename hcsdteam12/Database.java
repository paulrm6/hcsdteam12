package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Paul on 14/12/2015.
 */
public class Database {
    public static int getPatientID() {
        String[] details = getPatient();
        String forename = details[0];
        String surname = details[1];
        String addressid = details[2];
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query2 = "SELECT id FROM patients WHERE forename='"+forename+"' AND surname='"+surname+"' AND addressid='"+addressid+"';";
            ResultSet patient = stmt.executeQuery(query2);
            patient.next();
            int patientId = patient.getInt("id");
            patient.close();
            return patientId;
        } catch(NullPointerException e) {

        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String[] getPatient() {
        try {
            String postcode = JOptionPane.showInputDialog(null, "Enter the patients postcode:");
            postcode = postcode.replaceAll("\\s","");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname, addressid FROM patients JOIN address ON patients.addressid = address.id WHERE postcode='"+postcode+"';";
            ResultSet patients = stmt.executeQuery(query);
            String name = "";
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
                JOptionPane.showMessageDialog(null, "No patients live at this postcode");
                return new String[] {"","",""};
            }
            patients.close();
            String forename = name.split(",")[0];
            String surname = name.split(",")[1];
            String addressid = name.split(",")[2];
            return new String[] {forename, surname, addressid};
        } catch(NullPointerException e) {

        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new String[] {"","",""};

    }
}
