package hcsdteam12;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Created by Adam on 14/12/2015.
 * Modified by Terence Kong on 14/12/2015, to remove unused import.
 */
public class Treatments extends JFrame {

    private static final long serialVersionUID = 1L;
    // Creation of all variables needed for the form
    private JLabel totalCostLabel;

    public Treatments() {
        int id = Prompt.getPatientID();
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
            for (int j = 0; j < rowData.length; j++) {
                totalCost += Double.parseDouble(rowData[j][1].toString());
                System.out.println(totalCost);
            }
            totalCostLabel = new JLabel("Total cost: £" + totalCost);
            add(scrollPane, BorderLayout.CENTER);
            add(totalCostLabel, BorderLayout.SOUTH);
            setSize(300, 400);
            setVisible(true);
        }
    }

    public int updateTotalCostForPlan() {
        //Updates the total cost of the bill to take away ones based on the plan a user is on
        return 0;
    }

}