package hcsdteam12;

import java.sql.*;

/**
 * Created by Paul on 15/12/2015.
 */
public class Patient {
    private int id, outstandingBill, newOutstandingBill;
    private String title, forename, surname, dob, addressid, planName, phoneNumber,
            newTitle, newForename, newSurname, newDob, newPlanName, newPhoneNumber;
    private String houseNumber, streetName, districtName, cityName, postcode,
            newHouseNumber, newStreetName, newDistrictName,newCityName,newPostcode;
    private int healthcareCheckUp = 0, healthcareHygiene = 0, healthcareRepair = 0;

    public boolean isExists() {
        return exists;
    }

    private boolean exists = true;

    public Patient(String forename, String surname, String addressid) {
        this.forename = forename;
        this.surname = surname;
        this.addressid = addressid.replaceAll("\\s","");
        getPatientData();
        if (exists = true) {
            getPlanData();
            updateVar();
        }
    }

    public Patient(int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname, addressid FROM patients WHERE id=" + id + ";";
            ResultSet patient = stmt.executeQuery(query);
            if (patient.next()) {
                forename = patient.getString("forename");
                surname = patient.getString("surname");
                addressid = patient.getString("addressid");
                getPatientData();
                getPlanData();
                updateVar();
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

    private void updateVar() {
        newOutstandingBill = outstandingBill;
        newTitle = title;
        newForename = forename;
        newSurname = surname;
        newDob = dob;
        newPlanName = planName;
        newPhoneNumber = phoneNumber;
        newHouseNumber = houseNumber;
        newStreetName = streetName;
        newDistrictName = districtName;
        newCityName = cityName;
        newPostcode = postcode;
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
            if(getPatientsAtAddressID(addressid) == null) {
                query = "DELETE FROM address WHERE id='" + addressid+"';";
                stmt.execute(query);
            }
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

    private boolean updatePatient() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String newDBPlanName;
            if(newPlanName!=null) {
                newDBPlanName = "'"+newPlanName+"'";
            } else {
                newDBPlanName = "NULL";
            }
            String query = "UPDATE patients SET title='" + newTitle + "', forename='" + newForename + "', surname='" +
                    newSurname + "', dob='" + newDob + "', number='" + newPhoneNumber + "', plan_name="+newDBPlanName+", outstanding_bill="+
                    newOutstandingBill+" WHERE id=" + id +";";
            stmt.executeUpdate(query);
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

    private boolean updateAddress() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "UPDATE address SET id='" + getNewAddressid() + "', number='" + newHouseNumber + "', streetname='" +
                    newStreetName + "', districtname='" + newDistrictName + "', cityname='" + newCityName + "', postcode='" +
                    newPostcode + "' WHERE id='" + addressid +"';";
            stmt.executeUpdate(query);
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

    public boolean update() {
        if(updateAddress()&&updatePatient()){
            getPatientData();
            getPlanData();
            forename = newForename;
            surname = newSurname;
            addressid = getNewAddressid();
            return true;
        }
        return false;
    }

    public static boolean doesAddressExist(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String address = "SELECT * FROM address WHERE id='" + id + "';";
            if (!stmt.executeQuery(address).next()) {
                stmt.close();
                return false;
            } else {
                stmt.close();
                return true;
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
        return false;
    }

    public static boolean doesPatientExist(String forename,String surname,String addressid) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String patient = "SELECT * FROM patients WHERE forename='" + forename + "' AND surname='"
                    + surname + "' AND addressid='" + addressid + "';";
            if (!stmt.executeQuery(patient).next()) {
                stmt.close();
                return false;
            } else {
                stmt.close();
                return true;
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
        return false;
    }

    private static boolean createAddress(String id, String houseNumber, String streetName, String districtName, String cityName,
                                         String postcode) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "INSERT INTO address (id,number,streetname,districtname,cityname,postcode)" +
                    " VALUES ('" + id + "','" + houseNumber + "','" + streetName + "','" + districtName + "','" + cityName + "','" + postcode.replaceAll("\\s","") + "');";
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

    private static boolean createPatient(String title, String forename, String surname, String dob, String phoneNumber, String addressid) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "INSERT INTO patients (title,forename,surname,dob,number,addressid)" +
                    " VALUES ('" + title + "','" + forename + "','" + surname + "','" + dob + "','" + phoneNumber + "','" + addressid + "');";
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

    public static Patient create(String title, String forename, String surname, String dob, String phoneNumber,
                                 String houseNumber, String streetName, String districtName, String cityName,
                                 String postcode) {
        String addressid = (houseNumber+postcode).replaceAll("\\s","");
        dob = changeDateFromForm(dob);
        if(!doesPatientExist(forename,surname,addressid)) {
            if (!doesAddressExist(addressid)) {
                createAddress(addressid,houseNumber,streetName,districtName,cityName,postcode);
            }
            createPatient(title,forename,surname,dob,phoneNumber,addressid);
        }
        return new Patient(forename,surname,addressid);
    }

    public static Patient[] getPatientsAtAddressID(String addressid) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname FROM patients WHERE addressid='" + addressid + "';";
            ResultSet patients = stmt.executeQuery(query);
            if (patients.next()) {
                patients.last();
                Patient[] patientList = new Patient[patients.getRow()];
                patients.absolute(0);
                int i = 0;
                while (patients.next()) {
                    String forename = patients.getString("forename");
                    String surname = patients.getString("surname");
                    patientList[i] = new Patient(forename,surname,addressid);
                }
                patients.close();
                stmt.close();
                con.close();
                return patientList;
            } else {
                return null;
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
        return null;
    }

    public static Patient[] getPatientsAtPostcode(String postcode) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname, addressid FROM patients JOIN address ON patients.addressid=address.id WHERE address.postcode='" + postcode + "';";
            ResultSet patients = stmt.executeQuery(query);
            if (patients.next()) {
                patients.last();
                Patient[] patientList = new Patient[patients.getRow()];
                patients.absolute(0);
                int i = 0;
                while (patients.next()) {
                    String forename = patients.getString("forename");
                    String surname = patients.getString("surname");
                    String addressid = patients.getString("addressid");
                    patientList[i] = new Patient(forename,surname,addressid);
                }
                patients.close();
                stmt.close();
                con.close();
                return patientList;
            } else {
                return null;
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
        return null;
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

    private static String changeDateFromForm(String date) {
        String splitDate[] = date.split("/");
        return splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
    }

    private static String changeDateFromDatabase(String date) {
        String splitDate[] = date.split("-");
        return splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
    }

    public String getDob() {
        return changeDateFromDatabase(dob);
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

    private String getNewAddressid() {
        return (newHouseNumber+newPostcode).replaceAll("\\s","");
    }

    public void setOutstandingBill(int outstandingBill) {
        newOutstandingBill = outstandingBill;
    }

    public void setForename(String forename) {
        newForename = forename;
    }

    public void setSurname(String surname) {
        newSurname = surname;
    }

    public void setDob(String dob) {
        newDob = changeDateFromForm(dob);
    }

    public void setPlanName(String planName) {
        newPlanName=planName;
    }

    public void setPhoneNumber(String phoneNumber) {
        newPhoneNumber = phoneNumber;
    }

    public void setHouseNumber(String houseNumber) {
        newHouseNumber = houseNumber;
    }

    public void setStreetName(String streetName) {
        newStreetName = streetName;
    }

    public void setDistrictName(String districtName) {
        newDistrictName = districtName;
    }

    public void setCityName(String cityName) {
        newCityName = cityName;
    }

    public void setPostcode(String postcode) {
        newPostcode = postcode.replaceAll("\\s","");
    }

    public void setTitle(String title) {
        newTitle = title;
    }

    public static void main(String[] args) {
        System.out.println(getPatientsAtPostcode("HP39tz")[0].getAddressid());
    }
}