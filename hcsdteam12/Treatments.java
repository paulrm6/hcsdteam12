package hcsdteam12;

import javax.swing.*;
import java.sql.*;
import java.awt.BorderLayout;

/**
 * Created by Adam on 14/12/2015.
 */
public class Treatments extends JFrame{

    private static final long serialVersionUID = 1L;
    // Creation of all variables needed for the form
    private JLabel totalCost;
    private JTable table;
    private JTextField forename, surname, phone,
            house, street, district, city, postcode;
    private JButton newPatient;

    public Treatments() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object rowData[][] = { { "Row1-Column1", "Row1-Column2"},
                { "Row2-Column1", "Row2-Column2" } };
        Object columnNames[] = {"Treatment", "Cost"};
        JTable table = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300,150);
        frame.setVisible(true);
    }

    public int getPatientId() {
        try {
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
            return patientId;
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
    }

}
