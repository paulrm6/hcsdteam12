package hcsdteam12;

import javax.swing.*;
import java.sql.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 14/12/2015.
 */
public class Treatments extends JFrame{

    private static final long serialVersionUID = 1L;
    // Creation of all variables needed for the form
    private JLabel totalCostLabel;

    public Treatments() {
        int id = getPatientId();
        if (id != 0) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Object rowData[][] = {{}};
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                Statement stmt = con.createStatement();
                String query = "SELECT treatment_name, cost FROM treatments_given JOIN treatments ON treatments_given.treatment_name = treatments.name WHERE patientid=" + id + " AND paid = 0;";
                ResultSet result = stmt.executeQuery(query);
                result.last();
                Object[][] resultSet = new Object[result.getRow()][2];
                result.absolute(0);
                int row = 0;
                while (result.next()) {
                    for (int i = 0; i < 2; i++) {
                        resultSet[row][i] = result.getObject(i + 1);
                    }
                    row++;
                }
                rowData = resultSet;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Object columnNames[] = {"Treatment", "Cost"};
            JTable table = new JTable(rowData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            double totalCost = 0;
            for (int j = 0; j <= rowData[1].length; j++){
                totalCost += Double.parseDouble(rowData[j][1].toString());
                System.out.println(totalCost);
            }
            totalCostLabel = new JLabel("Total cost: £"+totalCost);
            add(scrollPane, BorderLayout.CENTER);
            add(totalCostLabel, BorderLayout.SOUTH);
            setSize(300, 400);
            setVisible(true);
        }
    }

    public int getPatientId() {
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
                return 0;
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

    public int updateTotalCostForPlan() {
        //Updates the total cost of the bill to take away ones based on the plan a user is on
        return 0;
    }

}
