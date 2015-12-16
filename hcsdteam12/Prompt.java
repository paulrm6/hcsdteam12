package hcsdteam12;

import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 14/12/2015.
 * Modified by Terence Kong on 14/12/2015 to parameterized the references to generic type HashMap<K,V>
 */
public class Prompt {
    public static int getPatientID() {
        Patient patient = getPatient();
        if(patient != null) {
            return patient.getId();
        }
        return 0;
    }

    public static Patient getPatient() {
        try {
            String postcode = JOptionPane.showInputDialog(null, "Enter the patients postcode:");
            postcode = postcode.replaceAll("\\s", "");
            Patient[] patientList = Patient.getPatientsAtPostcode(postcode);
            if (patientList != null) {
                int i = 0;
                String patientListString[] = new String[patientList.length];
                for (Patient patient : patientList) {
                    String fore = patient.getForename();
                    String sur = patient.getSurname();
                    String addressid = patient.getAddressid();
                    String fullDetails = sur + ", " + fore + " - " + addressid;
                    patientListString[i] = fullDetails;
                    i += 1;
                }
                String name = (String) JOptionPane.showInputDialog(null, "Select the patient", "View Patient", JOptionPane.QUESTION_MESSAGE,
                        null, patientListString, patientListString[0]);
                String surname = name.split(" - ")[0];
                String forename = surname.split(", ")[1];
                surname = surname.split(", ")[0];
                String addressid = name.split(" - ")[1];
                return new Patient(forename,surname,addressid);
            } else {
                JOptionPane.showMessageDialog(null, "No patients live at this postcode");
                return null;
            }
        } catch (NullPointerException e) {
        }
        return null;
    }

    public static Appointment getAppointment() {
        try {
            String date = getDate();
            int partnerID = getPartnerID();
            Appointment[] appointmentList = Appointment.getAppointments(date, partnerID);
            if (appointmentList != null) {
                int i = 0;
                String patientList[] = new String[appointmentList.length];
                for (Appointment appointment : appointmentList) {
                    String start = appointment.getStartTime();
                    String end = appointment.getEndTime();
                    String fore = appointment.getPatientForename();
                    String sur = appointment.getPatientSurname();
                    String fullDetails = start + " to " + end + ", " + fore + " " + sur;
                    patientList[i] = fullDetails;
                    i += 1;
                }
                String appointment = (String) JOptionPane.showInputDialog(null, "Select the patient", "View Patient", JOptionPane.QUESTION_MESSAGE,
                        null, patientList, patientList[0]);
                String startTime = appointment.split(" to ")[0];
                return new Appointment(date, startTime, partnerID);
            } else {
                JOptionPane.showMessageDialog(null, "No appointments on that date for that parner");
                return null;
            }
        } catch (NullPointerException e) {}
        return null;
    }

    public static String getDate() {
        String date;
        date = JOptionPane.showInputDialog(null, "Enter the date of the appointment (dd/mm/yyyy):");
        while (!date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            date = JOptionPane.showInputDialog(null, "Enter the date of the appointment (dd/mm/yyyy):");
        }
        return date;
    }

    public static int getPartnerID() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT id, forename, surname, role FROM partners;";
            ResultSet partners = stmt.executeQuery(query);
            String name;
            if (partners.next()) {
                partners.last();
                String[] partnerList = new String[partners.getRow()];
                Map<String, Integer> partnerLookup = new HashMap<String, Integer>();
                partners.absolute(0);
                int i = 0;
                while (partners.next()) {
                    String fore = partners.getString("forename");
                    String sur = partners.getString("surname");
                    String role = partners.getString("role");
                    String fullDetails = sur + ", " + fore + " - " + role;
                    partnerList[i] = fullDetails;
                    partnerLookup.put(fullDetails, partners.getInt("id"));
                    i += 1;
                }
                partners.close();
                stmt.close();
                con.close();
                name = (String) JOptionPane.showInputDialog(null, "Select partner name", "View Patient", JOptionPane.QUESTION_MESSAGE,
                        null, partnerList, partnerList[0]);
                return partnerLookup.get(name);
            } else {
                JOptionPane.showMessageDialog(null, "No partners exist");
                return -1;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
        return -1;
    }
}