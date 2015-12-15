package hcsdteam12;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Paul on 14/12/2015.
 */
public class AddTreatment {

    public AddTreatment(int patientID, String date) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String queryName = "SELECT forename, surname FROM patients WHERE id='" + patientID + "'";
            ResultSet patient = stmt.executeQuery(queryName);
            patient.next();
            String forename = patient.getString("forename");
            String surname = patient.getString("surname");
            String query3 = "SELECT name FROM treatments;";
            ResultSet treatments = stmt.executeQuery(query3);
            if (treatments.next()) {
                treatments.last();
                String[] treatmentList = new String[treatments.getRow()];
                treatments.absolute(0);
                int i = 0;
                while (treatments.next()) {
                    treatmentList[i] = treatments.getString("name");
                    i += 1;
                }
                String treatment = (String) JOptionPane.showInputDialog(null, "Add a treatment for " + forename + " " + surname + " below:", "Treatments", JOptionPane.QUESTION_MESSAGE, null,
                        treatmentList, treatmentList[0]);
                String query5;
                if (treatment != null) {
                    String query7 = "SELECT type FROM treatments WHERE name='"+treatment+"';";
                    ResultSet type = stmt.executeQuery(query7);
                    type.next();
                    String treatmentType = type.getString("type");
                    type.close();
                    String query8 = "SELECT COUNT(*) AS used FROM treatments_given INNER JOIN treatments ON treatments_given.treatment_name=treatments.name WHERE patientid="+patientID+" AND covered=1 AND DATEDIFF(NOW(), date_given) < 365 AND type='"+treatmentType+"'";
                    ResultSet current = stmt.executeQuery(query8);
                    current.next();
                    int currentUsed = current.getInt("used");
                    current.close();
                    treatmentType = treatmentType.replaceAll("\\s+","");
                    String query9 = "SELECT serviceLvl"+treatmentType+" FROM healthcare_plan INNER JOIN patients ON healthcare_plan.name=patients.plan_name WHERE id="+patientID+";";
                    ResultSet total = stmt.executeQuery(query9);
                    total.next();
                    int totalAmount = total.getInt("serviceLvl"+treatmentType);
                    total.close();
                    int coveredLeft = totalAmount - currentUsed;
                    if (coveredLeft > 0) {
                        query5 = "INSERT into treatments_given (patientid, treatment_name, paid, covered, date_given) VALUES ('" + patientID + "','" + treatment + "', 1, 1, '"+date+"')";
                        System.out.println(date);
                    } else {
                        query5 = "INSERT into treatments_given (patientid, treatment_name, covered, date_given) VALUES ('" + patientID + "','" + treatment + "', 0, '"+date+"')";
                        System.out.println(date);
                    }
                    stmt.executeUpdate(query5);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No treatment plans exist.");
            }
            treatments.close();
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
}
