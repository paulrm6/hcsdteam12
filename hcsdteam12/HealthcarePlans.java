package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Adam on 14/12/2015.
 * Modified by Paul on 14/12/2015 to make minor SQL and display changes
 */
public class HealthcarePlans {

    public HealthcarePlans() {
        String[] details = Database.getPatient();
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
                    String query4 = "SELECT plan_name FROM patients WHERE id=" + patientId;
                    ResultSet currentPlan = stmt.executeQuery(query4);
                    currentPlan.next();
                    String currentPlanName = currentPlan.getString("plan_name");
                    if (currentPlanName == null) {
                        currentPlanName = "No Plan";
                    }
                    String plan = (String) JOptionPane.showInputDialog(null, "Patients current plan is: " + currentPlanName + "\n Set the new plan below", "Healthcare Plans", JOptionPane.QUESTION_MESSAGE, null,
                            planList, planList[0]);
                    String query5;
                    currentPlan.close();
                    if (plan == "No plan" && plan != null) {
                        query5 = "UPDATE patients SET plan_name=NULL WHERE id=" + patientId;
                        stmt.executeUpdate(query5);
                        JOptionPane.showMessageDialog(null, forename + " " + surname + " now has no plan");
                    } else if (plan != null) {
                        query5 = "UPDATE patients SET plan_name='" + plan + "' WHERE id=" + patientId;
                        stmt.executeUpdate(query5);
                        JOptionPane.showMessageDialog(null, forename + " " + surname + " has been added to " + plan);
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
