package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Adam on 14/12/2015.
 * Modified by Paul on 14/12/2015 to make minor SQL and display changes
 */
public class HealthcarePlans {

    public HealthcarePlans() {
        Patient patient = Prompt.getPatient();
        if (patient != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                Statement stmt = con.createStatement();
                String query3 = "SELECT name FROM healthcare_plan;";
                ResultSet plans = stmt.executeQuery(query3);
                if (plans.next()) {
                    plans.last();
                    String[] planList = new String[plans.getRow() + 1];
                    plans.absolute(0);
                    int i = 1;
                    while (plans.next()) {
                        planList[i] = plans.getString("name");
                        i += 1;
                    }
                    planList[0] = "No plan";
                    String plan = (String) JOptionPane.showInputDialog(null, "Patients current plan is: " +
                            patient.getPlanName() + "\n Set the new plan below", "Healthcare Plans",
                            JOptionPane.QUESTION_MESSAGE, null, planList, planList[0]);
                    if (plan == "No plan") {
                        patient.setPlanName(null);
                        patient.update();
                        JOptionPane.showMessageDialog(null, patient.getForename() + " " + patient.getSurname() + " now has no plan");
                    } else if (plan != null) {
                        patient.setPlanName(plan);
                        patient.update();
                        JOptionPane.showMessageDialog(null, patient.getForename() + " " + patient.getSurname() + " has been added to " + plan);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No healthcare plans exist.");
                }
                stmt.close();
                con.close();
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
        }
    }
    //Enter patients postcode
    //Dropdown of patients at that address
    //Displays an options menu with subscribe, update cancel
}