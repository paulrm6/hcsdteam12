package hcsdteam12;

import java.sql.*;

/**
 * Created by Paul on 15/12/2015.
 */
public class Appointment {
    private String date, startTime, endTime, newDate, newStartTime, newEndTime;
    private int patientid,partnerid;
    private int seen,newPatientid,newPartnerid,newSeen;
    private String patientForename, patientSurname, addressid;
    private String partnerForename, partnerSurname, role;
    private boolean exists = true;

    /**
     * Constructor. Used to get all the possible information needed about an appointment.
     *
     * @param date      The date of the appointment yyyy-MM-dd
     * @param startTime The start time of the appointment hh:mm:ss
     * @param partnerid The id of the partner
     */
    public Appointment(String date, String startTime, int partnerid) {
        this.date = date;
        this.startTime = startTime;
        this.partnerid = partnerid;
        getAppointmentData();
        if (exists) {
            getPatientData();
            getPartnerData();
            newDate = this.date;
            newStartTime = this.startTime;
            newEndTime = this.endTime;
            newPatientid = this.patientid;
            newPartnerid = this.partnerid;
            newSeen = this.seen;
        }
    }

    private void getAppointmentData() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT patientid, endTime, seen FROM appointment WHERE date='" + date + "' AND startTime='" +
                    startTime + "' AND partnerid=" + partnerid + ";";
            ResultSet appointment = stmt.executeQuery(query);
            if (appointment.next()) {
                patientid = appointment.getInt("patientid");
                endTime = appointment.getString("endTime");
                seen = appointment.getInt("seen");
            } else {
                exists = false;
            }
            appointment.close();
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

    private void getPatientData() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname, addressid FROM patients WHERE id=" + patientid + ";";
            ResultSet patient = stmt.executeQuery(query);
            if (patient.next()) {
                patientForename = patient.getString("forename");
                patientSurname = patient.getString("surname");
                addressid = patient.getString("addressid");
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

    private void getPartnerData() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT forename, surname, role FROM partners WHERE id=" + partnerid + ";";
            ResultSet partner = stmt.executeQuery(query);
            if (partner.next()) {
                partnerForename = partner.getString("forename");
                partnerSurname = partner.getString("surname");
                role = partner.getString("role");
            }
            partner.close();
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

    /**
     * Updates an appointment based of the data that has been set (i.e. .setDate)
     *
     * @return a boolean indicating success
     */
    public boolean update() {
        if(checkForClash(newDate,newStartTime,newEndTime,newPartnerid)) {
            return false;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "UPDATE appointment SET date='" + newDate + "', startTime='" + newStartTime + "', endTime='" +
                    newEndTime + "', seen=" + newSeen + ", patientid=" + newPatientid + ", partnerid=" + newPartnerid + " WHERE date='" + date +
                    "' AND startTime='" + startTime + "' AND partnerid=" + partnerid + ";";
            stmt.executeUpdate(query);
            stmt.close();
            con.close();
            date = newDate;
            startTime = newStartTime;
            partnerid = newPartnerid;
            getAppointmentData();
            getPatientData();
            getPartnerData();
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

    /**
     * Deletes the appointment from the database
     *
     * @return boolean indication of success
     */
    public boolean delete() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "DELETE FROM appointment WHERE  date='" + date + "' AND startTime='" +
                    startTime + "' AND partnerid=" + partnerid + ";";
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

    /**
     * Finds if there is a clash for an appointment
     *
     * @param date      the date (yyyy-MM-dd)
     * @param startTime the start time (hh:mm:ss)
     * @param endTime   the end time (hh:mm:ss)
     * @param partnerid the parter id
     * @return true if there is a clash, false if there isn't
     */
    public static boolean checkForClash(String date, String startTime, String endTime, int partnerid) {
        Appointment[] Appointments = getAppointments(date, partnerid);
        boolean clash = false;
        if (Appointments == null) {
            return false;
        }
        for (Appointment appointment : Appointments) {
            if (appointment == null) {
                return false;
            }
            if (timeToMins(appointment.startTime) < timeToMins(endTime) &&
                    timeToMins(appointment.endTime) > timeToMins(startTime)) {
                clash = true;
            }
        }
        return clash;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getPatientid() {
        return patientid;
    }

    public int getPartnerid() {
        return partnerid;
    }

    public String getPatientForename() {
        return patientForename;
    }

    public String getPatientSurname() {
        return patientSurname;
    }

    public String getAddressid() {
        return addressid;
    }

    public String getPartnerForename() {
        return partnerForename;
    }

    public String getPartnerSurname() {
        return partnerSurname;
    }

    public String getRole() {
        return role;
    }

    public String getDuration() {
        int startTimeMins = timeToMins(startTime);
        int endTimeMins = timeToMins(endTime);
        int durationMins = endTimeMins - startTimeMins;
        String duration = (durationMins / 60) + ":" + (durationMins % 60) + ":00";
        return duration;
    }

    private static int timeToMins(String time) {
        return Integer.parseInt(time.split(":")[0]) * 60 + Integer.parseInt(time.split(":")[1]);
    }

    public int getSeen() {
        return seen;
    }

    /**
     * Set the new date before calling update()
     * @param date new date (yyyy-MM-dd)
     * @return true if it has been set, false if there was a clash and it was not set
     */
    public boolean setDate(String date) {
        if (!checkForClash(date, startTime, endTime, partnerid)) {
            newDate = date;
            return true;
        }
        return false;
    }

    /**
     * Set the new start time before calling update()
     * @param startTime new start time (hh:mm:ss)
     * @return true if it has been set, false if there was a clash and it was not set
     */
    public boolean setStartTime(String startTime) {
        if (!checkForClash(date, startTime, endTime, partnerid)) {
            newStartTime = startTime;
            return true;
        }
        return false;
    }

    /**
     * Set the new end time before calling update()
     * @param endTime new end time (hh:mm:ss)
     * @return true if it has been set, false if there was a clash and it was not set
     */
    public boolean setEndTime(String endTime) {
        if (!checkForClash(date, startTime, endTime, partnerid)) {
            newEndTime = endTime;
            return true;
        }
        return false;
    }

    /**
     * Set the new date, start time and end time before calling update()
     * @param date new date (yyyy-MM-dd)
     * @param startTime new start time (hh:mm:ss)
     * @param endTime new end time (hh:mm:ss)
     * @return true if they have been set, false if there was a clash and they were not set
     */
    public boolean setDateTime(String date, String startTime, String endTime) {
        if (!checkForClash(date, startTime, endTime, partnerid)) {
            newDate = date;
            newStartTime = startTime;
            newEndTime = endTime;
            return true;
        }
        return false;
    }

    /**
     * Set the new partnerid before calling update()
     * @param partnerid new partner id
     * @return true if it has been set, false if there was a clash and it was not set
     */
    public boolean setPartnerid(int partnerid) {
        if (!checkForClash(date, startTime, endTime, partnerid)) {
            newPartnerid = partnerid;
            return true;
        }
        return false;
    }

    public void setPatientid(int patientid) {
        newPatientid = patientid;
    }

    public void setSeen(int seen) {
        newSeen = seen;
    }

    /**
     * Returns all the appointments on a given day for a partner
     * @param date the date (yyyy-MM-dd)
     * @param partnerid the partner id
     * @return a list of appointments
     */
    public static Appointment[] getAppointments(String date, int partnerid) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT startTime FROM appointment WHERE date='" + date + "' AND partnerid=" + partnerid + ";";
            ResultSet appointments = stmt.executeQuery(query);
            if (appointments.next()) {
                appointments.last();
                Appointment[] appointmentsList = new Appointment[appointments.getRow()];
                appointments.absolute(0);
                int i = 0;
                while (appointments.next()) {
                    String startTime = appointments.getString("startTime");
                    appointmentsList[i] = new Appointment(date, startTime, partnerid);
                    i += 1;
                }
                return appointmentsList;
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

    /**
     * Creates a new appointment
     *
     * @param date      date of the appointment (yyyy-MM-dd)
     * @param startTime start time of the appointment (hh:mm:ss)
     * @param endTime   end time of the appointment (hh:mm:ss)
     * @param patientid the id of the patient (null if holiday)
     * @param partnerid the id of the partner
     * @return The newly created appointment as an Appointment or null if it overlaps another appointment
     */
    public static Appointment createAppointment(String date, String startTime, String endTime, int patientid, int partnerid) {
        if (checkForClash(date, startTime, endTime, partnerid)) {
            return null;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "INSERT INTO appointment (date,startTime,endTime,patientid,partnerid)" +
                    " VALUES ('" + date + "','" + startTime + "','" + endTime + "'," + patientid + "," + partnerid + ");";
            stmt.execute(query);
            stmt.close();
            con.close();
            return new Appointment(date, startTime, partnerid);
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
}
