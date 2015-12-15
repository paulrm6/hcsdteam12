package hcsdteam12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Adam on 14/12/2015.
 * Modified by Terence Kong on 14/12/2015, to remove unused import.
 */
public class Receipts extends JFrame {

    private static final long serialVersionUID = 1L;
    // Creation of all variables needed for the form
    private JLabel totalCostLabel;
    private JButton paid = new JButton("Paid?");;
    private JFrame frame = new JFrame();
    private int id;

    public Receipts() {
        id = Prompt.getPatientID();
        if (id != 0) {
            Object rowData[][] = {{}};
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                Statement stmt = con.createStatement();
                String query = "SELECT treatment_name, cost FROM treatments_given JOIN treatments ON treatments_given.treatment_name = treatments.name WHERE patientid=" + id + " AND paid = 0 AND covered = 0;";
                ResultSet result = stmt.executeQuery(query);
                if (result.next()) {
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
                    Object columnNames[] = {"Treatment", "Cost"};
                    JTable table = new JTable(rowData, columnNames);
                    JScrollPane scrollPane = new JScrollPane(table);
                    double totalCost = 0;
                    for (int j = 0; j < rowData.length; j++) {
                        totalCost += Double.parseDouble(rowData[j][1].toString());
                    }
                    result.close();
                    stmt.close();
                    con.close();
                    totalCostLabel = new JLabel("Total cost: £" + totalCost);
                    setTitle("Receipt");
                    setLayout(new GridBagLayout());
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    add(scrollPane, 0, 0, 300, 300);
                    add(totalCostLabel, 0, 1, 1, 1);
                    add(paid, 1, 1, 1, 1);
                    setSize(300, 400);
                    setVisible(true);
                    paid.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                System.out.println("Read");
                                Class.forName("com.mysql.jdbc.Driver").newInstance();
                                Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                                Statement stmt = con.createStatement();
                                String query = "UPDATE treatments_given SET paid=1 WHERE patientid=" + id + " AND paid = 0;";
                                stmt.executeUpdate(query);
                                dispose();
                                stmt.close();
                                con.close();
                            } catch (InstantiationException e1) {
                                e1.printStackTrace();
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            } catch (ClassNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "This patient has no outstanding bills");
                }
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

    public int updateTotalCostForPlan() {
        //Updates the total cost of the bill to take away ones based on the plan a user is on
        return 0;
    }

    public void add(Component c, int x, int y, int w, int h) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 20;
        gbc.ipady = 5;
        add(c, gbc);
    }
}