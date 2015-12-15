package hcsdteam12;

import java.sql.*;

/**
 * Created by Paul on 15/12/2015.
 */
public class Patient {
    private int id, outstandingBill;
    private String title, forename, surname, dob, addressid, planName, phoneNumber;
    private String houseNumber, streetName, districtName, cityName, postcode;
    private int healthcareCheckUp = 0, healthcareHygiene = 0, healthcareRepair = 0;
    private boolean exists = true;

    public Patient(String forename, String surname, String addressid) {
        this.forename = forename;
        this.surname = surname;
        this.addressid = addressid;
        getPatientData();
        if (exists = true) {
            getPlanData();
        }
    }

    private void getPatientData() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT patients.id, title, dob, patients.number, plan_name, outstanding_bill, address.number, " +
                    "address.streetname, address.districtname, address.cityname, address.postcode FROM patients " +
                    "JOIN address ON patients.addressid = address.id WHERE forename='" + forename + "' AND surname='" +
                    surname + "' AND addressid='" + addressid + "';";
            ResultSet patient = stmt.executeQuery(query);
            if (patient.next()) {
                id = patient.getInt("patients.id");
                title = patient.getString("title");
                dob = patient.getString("dob");
                phoneNumber = patient.getString("patients.number");
                planName = patient.getString("plan_name");
                outstandingBill = patient.getInt("outstanding_bill");
                houseNumber = patient.getString("address.number");
                streetName = patient.getString("address.streetname");
                districtName = patient.getString("address.districtname");
                cityName = patient.getString("address.cityname");
                postcode = patient.getString("address.postcode");
            } else {
                exists = false;
            }
            patient.close();
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

    private void getPlanData() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT serviceLvlCheckUp, serviceLvlHygiene, serviceLvlRepair FROM healthcare_plan WHERE name='" + planName + "';";
            ResultSet plan = stmt.executeQuery(query);
            if (plan.next()) {
                healthcareCheckUp = plan.getInt("serviceLvlCheckUp");
                healthcareHygiene = plan.getInt("serviceLvlHygiene");
                healthcareRepair = plan.getInt("serviceLvlRepair");
            }
            plan.close();
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

    public boolean delete() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "DELETE FROM patients WHERE id='" + id+"';";
            stmt.execute(query);
            stmt.close();
            con.close();
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public int getOutstandingBill() {
        return outstandingBill;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getDob() {
        return dob;
    }

    public String getAddressid() {
        return addressid;
    }

    public String getPlanName() {
        return planName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPostcode() {
        return postcode;
    }

    public int getHealthcareCheckUp() {
        return healthcareCheckUp;
    }

    public int getHealthcareHygiene() {
        return healthcareHygiene;
    }

    public int getHealthcareRepair() {
        return healthcareRepair;
    }

    public static void main(String[] args) {
        Patient patient = new Patient("blah", "blah", "6HP39TN");
        System.out.println(patient.getDob());
        System.out.println(patient.getCityName());
        System.out.println(patient.getHealthcareCheckUp());
        patient.delete();
    }
}
