package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Adam on 14/12/2015.
 */
public class HealthcarePlans {

    public HealthcarePlans() {
        String postcode = JOptionPane.showInputDialog(null, "Enter the patients postcode:");
        String name;
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
                System.out.println(patientList);
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
            String query3 = "SELECT name FROM healthcare_plan;";
            ResultSet plans = stmt.executeQuery(query3);
            if (plans.next()) {
                plans.last();
                String[] planList = new String[plans.getRow()+1];
                plans.absolute(0);
                int i = 1;
                while (plans.next()) {
                    planList[i] = plans.getString("name");
                    i += 1;
                }
                planList[0] = "No plan";
                System.out.println(plans);
                String query4 = "SELECT plan_name FROM patients WHERE id="+patientId;
                ResultSet currentPlan = stmt.executeQuery(query4);
                currentPlan.next();
                String plan = (String)  JOptionPane.showInputDialog(null, "Patients current plan is: "+currentPlan.getString("plan_name")+"\n Set the new plan below", "Healthcare Plans", JOptionPane.QUESTION_MESSAGE, null,
                        planList, planList[0]);
                String query5;
                currentPlan.close();
                if (plan == "No plan") {
                    query5 = "UPDATE patients SET plan_name=NULL WHERE id="+patientId;
                } else {
                    query5 = "UPDATE patients SET plan_name='" + plan + "' WHERE id=" + patientId;
                }
                stmt.executeUpdate(query5);
            } else {
                JOptionPane.showMessageDialog(null, "No healthcare plans exist.");
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
    //Enter patients postcode
    //Dropdown of patients at that address
    //Displays an options menu with subscribe, update cancel
}
