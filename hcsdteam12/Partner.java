package hcsdteam12;

import java.sql.*;

/**
 * Created by Paul on 15/12/2015.
 * I declare that this code is my own work.
 * prmacdonald1@sheffield.ac.uk
 * 1350155458
 */
public class Partner {
    private int id;
    private String title, forename, surname, role;
    public Partner(int id){
        this.id = id;
        getDetails();
    }

    private void getDetails() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            String query = "SELECT title, forename, surname, role FROM partners WHERE id=" + id + ";";
            ResultSet partner = stmt.executeQuery(query);
            if (partner.next()) {
                title = partner.getString("title");
                forename = partner.getString("forename");
                surname = partner.getString("surname");
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

    public int getId() {
        return id;
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

    public String getRole() {
        return role;
    }
}
