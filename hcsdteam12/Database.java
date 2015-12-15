package hcsdteam12;

import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 14/12/2015.
 */
public class Database {
    public static int getPatientID() {
        String[] details = getPatient();
        if (details != null) {
            String forename = details[0];
            String surname = details[1];
            String addressid = details[2];
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                Statement stmt = con.createStatement();
                String query2 = "SELECT id FROM patients WHERE forename='" + forename + "' AND surname='" + surname + "' AND addressid='" + addressid + "';";
                ResultSet patient = stmt.executeQuery(query2);
                patient.next();
                int patientId = patient.getInt("id");
                patient.close();
                return patientId;
            } catch (NullPointerException e) {

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return 0;
        } else {
            return 0;
        }
    }

    public static String[] getPatient() {
        try {
            String postcode = JOptionPane.showInputDialog(null, "Enter the patients postcode:");
            postcode = postcode.replaceAll("\\s", "");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname, addressid FROM patients JOIN address ON patients.addressid = address.id WHERE postcode='" + postcode + "';";
            ResultSet patients = stmt.executeQuery(query);
            String name;
            if (patients.next()) {
                patients.last();
                String[] patientList = new String[patients.getRow()];
                patients.absolute(0);
                int i = 0;
                while (patients.next()) {
                    String fore = patients.getString("forename");
                    String sur = patients.getString("surname");
                    String addressid = patients.getString("addressid");
                    String fullDetails = sur + ", " + fore + " - " + addressid;
                    patientList[i] = fullDetails;
                    i += 1;
                }
                patients.close();
                stmt.close();
                con.close();
                name = (String) JOptionPane.showInputDialog(null, "Select the patient", "View Patient", JOptionPane.QUESTION_MESSAGE,
                        null, patientList, patientList[0]);
            } else {
                JOptionPane.showMessageDialog(null, "No patients live at this postcode");
                return null;
            }
            String surname = name.split(" - ")[0];
            String forename = surname.split(", ")[1];
            surname = surname.split(", ")[0];
            String addressid = name.split(" - ")[1];
            return new String[]{forename, surname, addressid};
        } catch (NullPointerException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Appointment getAppointment() {
        String date = getDate();
        int partnerID = getPartnerID();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT startTime, endTime, forename, surname FROM appointment JOIN patients ON id = patientid WHERE date='" + date + "' and partnerid=" + partnerID + ";";
            ResultSet appointments = stmt.executeQuery(query);
            if (appointments.next()) {
                appointments.last();
                String[] patientList = new String[appointments.getRow()];
                appointments.absolute(0);
                int i = 0;
                String appointment = null;
                while (appointments.next()) {
                    String start = appointments.getString("startTime");
                    String end = appointments.getString("endTime");
                    String fore = appointments.getString("forename");
                    String sur = appointments.getString("surname");
                    String fullDetails = start + " to " + end + ", " + fore + " " + sur;
                    patientList[i] = fullDetails;
                    i += 1;
                }
                appointments.close();
                stmt.close();
                con.close();
                appointment = (String) JOptionPane.showInputDialog(null, "Select the patient", "View Patient", JOptionPane.QUESTION_MESSAGE,
                        null, patientList, patientList[0]);
                String startTime = appointment.split(" to ")[0];
                return new Appointment(date,startTime,partnerID);
            } else {
                JOptionPane.showMessageDialog(null, "No appointments on that date for that parner");
                return null;
            }
        } catch (NullPointerException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDate() {
        String date;
        date = JOptionPane.showInputDialog(null, "Enter the date of the appointment (dd/mm/yyyy):");
        while (!date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            date = JOptionPane.showInputDialog(null, "Enter the date of the appointment (dd/mm/yyyy):");
        }
        return changeDateFromForm(date);
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
                Map<String, Integer> partnerLookup = new HashMap();
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

    static String changeDateFromForm(String date) {
        String splitDate[] = date.split("/");
        return splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
    }

    static String changeDateFromDatabase(String date) {
        String splitDate[] = date.split("-");
        return splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
    }
}
