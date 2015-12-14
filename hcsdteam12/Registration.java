package hcsdteam12;
/**
 * Created by Joseph on 08/12/2015.
 * Modified by Paul on 13/12/2015, 14/12/2015 to add minor functionality
 * Modified by Adam to add relevant SQL to all functions on 13/12/2015
 */

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registration extends JFrame {

	private static final long serialVersionUID = 1L;
	// Creation of all variables needed for the form
    private JLabel titleLabel, forenameLabel, surnameLabel, dobLabel, phoneLabel,
                    houseLabel, streetLabel, districtLabel, cityLabel, postcodeLabel,
                    forenameError, surnameError, dobError, phoneError,
                    houseError, streetError, districtError, cityError, postcodeError;
    private JComboBox title;
    private JTextField forename, surname, phone,
                        house, street, district, city, postcode;
    private JFormattedTextField dob;
    private JButton register, close, view, update;

    private int currentPatient; //Holds an int relating to the id of the patient currently being viewed/update

    private final DateFormat date = new SimpleDateFormat("dd/mm/yyyy");

    public Registration() {

        // Array of titles to add into combo box
        String[] titles = new String[] {"Mr","Mrs","Miss","Ms","Dr"};

        //set properties of the frame
        setTitle("hcsdteam12.Registration form");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Creation of labels
        titleLabel = new JLabel("Title:");
        forenameLabel = new JLabel("Forename:");
        surnameLabel = new JLabel("Surname:");
        dobLabel = new JLabel("Date of birth (dd/mm/yyyy):");
        phoneLabel = new JLabel("Phone number:");
        houseLabel = new JLabel("House number:");
        streetLabel = new JLabel("Street:");
        districtLabel = new JLabel("District:");
        cityLabel = new JLabel("City:");
        postcodeLabel = new JLabel("Postcode:");
        forenameError = new JLabel("");
        surnameError = new JLabel("");
        dobError = new JLabel("");
        phoneError = new JLabel("");
        houseError = new JLabel("");
        streetError = new JLabel("");
        districtError = new JLabel("");
        cityError = new JLabel("");
        postcodeError = new JLabel("");

        // Creation of components
        title = new JComboBox(titles);
        forename = new JTextField(10);
        surname = new JTextField(10);
        phone = new JTextField(10);
        house = new JTextField(1);
        street = new JTextField(10);
        district = new JTextField(10);
        city = new JTextField(10);
        postcode = new JTextField(5);
        register = new JButton("Register"); // button
        close = new JButton("Close"); // button
        view = new JButton("View patient");
        update = new JButton("Update");
        // Setting the date of birth function to take a particular format
        dob = new JFormattedTextField(date);
        dob.setColumns(5);
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(dob);
        } catch (ParseException ex){
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Laying out the labels with GridBagConstraint
        add(titleLabel,0,1,1,1);
        add(forenameLabel,0,2,1,1);
        add(surnameLabel,0,3,1,1);
        add(dobLabel,0,4,1,1);
        add(phoneLabel,0,5,1,1);
        add(houseLabel,0,6,1,1);
        add(streetLabel,0,7,1,1);
        add(districtLabel,0,8,1,1);
        add(cityLabel,0,9,1,1);
        add(postcodeLabel,0,10,1,1);
        add(forenameError,2,2,1,1);
        add(surnameError,2,3,1,1);
        add(dobError,2,4,1,1);
        add(phoneError,2,5,1,1);
        add(houseError,2,6,1,1);
        add(streetError,2,7,1,1);
        add(districtError,2,8,1,1);
        add(cityError,2,9,1,1);
        add(postcodeError,2,10,1,1);

        // Laying out the components with GridBagConstraint
        add(title,1,1,1,1);
        add(forename,1,2,1,1);
        add(surname,1,3,1,1);
        add(dob,1,4,1,1);
        add(phone,1,5,1,1);
        add(house,1,6,1,1);
        add(street,1,7,1,1);
        add(district,1,8,1,1);
        add(city,1,9,1,1);
        add(postcode,1,10,1,1);
        add(register,1,11,1,1); // button
        add(close,0,13,1,1); // button
        add(view,0,0,1,1); // button
        add(update,0,11,1,1); // button

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        // Validation
        register.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (validEntry()) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                        Statement stmt = con.createStatement();
                        String postcode = Registration.this.postcode.getText().replaceAll("\\s","");
                        String dob = Database.changeDateFromForm(Registration.this.dob.getText());
                        if (!addressExists(con, house.getText(), postcode)) {
                            String query = "INSERT INTO address VALUES ('" + house.getText() + postcode + "','" + house.getText() + "','" + street.getText() + "','" + district.getText() + "','" + city.getText() + "','" + postcode + "');";
                            stmt.executeUpdate(query);
                        }
                        if (!personExists(con,forename.getText(), surname.getText(), dob, house.getText(), postcode)) {
                            String query2 = "INSERT INTO patients(title, forename, surname, dob, number, addressid, outstanding_bill) VALUES ('" + title.getSelectedItem() + "','" + forename.getText() + "','" + surname.getText() + "','" + dob + "','" + phone.getText() + "','" + house.getText() + postcode + "', 0);";
                            stmt.executeUpdate(query2);
                        }
                        stmt.close();
                        con.close();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                       e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // Setting size and visibility
        setBounds(100,100,400,450);
        setVisible(true);

        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] details = Database.getPatient();
                if (details != null) {
                    String forenameView = details[0];
                    String surnameView = details[1];
                    String addressidView = details[2];
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                        Statement stmt = con.createStatement();
                        String query2 = "SELECT patients.id, title, forename, surname, dob, patients.number, address.number, streetname, districtname, cityname, postcode FROM patients JOIN address ON patients.addressid=address.id " +
                                "WHERE forename='" + forenameView + "' AND surname='" + surnameView + "' AND addressid='" + addressidView + "';";
                        ResultSet patient = stmt.executeQuery(query2);
                        patient.next();
                        title.setSelectedItem(patient.getString("title"));
                        forename.setText(patient.getString("forename"));
                        surname.setText(patient.getString("surname"));
                        dob.setText(Database.changeDateFromDatabase(patient.getString("dob")));
                        phone.setText(patient.getString("patients.number"));
                        house.setText(patient.getString("address.number"));
                        street.setText(patient.getString("streetname"));
                        district.setText(patient.getString("districtname"));
                        city.setText(patient.getString("cityname"));
                        postcode.setText(patient.getString("postcode"));
                        currentPatient = patient.getInt("patients.id");
                        patient.close();
                        stmt.close();
                        con.close();
                    } catch (NullPointerException e1) {

                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validEntry()) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
                        Statement stmt = con.createStatement();
                        String postcode = Registration.this.postcode.getText().replaceAll("\\s","");
                        String dob = Database.changeDateFromForm(Registration.this.dob.getText());
                        if (!addressExists(con, house.getText(), postcode)) {
                            String query = "INSERT INTO address VALUES ('"+house.getText()+ postcode+"','"+house.getText()+"','" + street.getText()+"','"+district.getText()+"','"+city.getText()+"','"+ postcode+"');";
                            stmt.executeUpdate(query);
                        }
                        //Needs code to remove redudant addresses
                        String query = "UPDATE patients SET title='"+title.getSelectedItem()+"', forename='"+forename.getText()+"', surname='"+
                                    surname.getText()+"', dob='"+dob+"', number='"+phone.getText()+"', addressid='"+house.getText()+ postcode+"' WHERE id="+currentPatient+";";stmt.executeUpdate(query);
                        stmt.close();
                        con.close();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

    private boolean validEntry() {
        boolean state = true;
        if (forename.getText().toString().matches("[a-zA-z]+")) {
            valSuccess(forenameError);
        } else {
            valError(forenameError);
            state = false;
        }
        if (surname.getText().toString().matches("[a-zA-z]+")) {
            valSuccess(surnameError);
        } else {
            valError(surnameError);
            state = false;
        }
        if (dob.getText().toString().matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            valSuccess(dobError);
        } else {
            valError(dobError);
            state = false;
        }
        if (phone.getText().toString().matches("[0-9]+") && phone.getText().length() == 11) {
            valSuccess(phoneError);
        } else {
            valError(phoneError);
            state = false;
        }
        if (house.getText().toString().matches("[0-9a-zA-z ]+")) {
            valSuccess(houseError);
        } else {
            valError(houseError);
            state = false;
        }
        if (street.getText().toString().matches("[a-zA-Z ]+")) {
            valSuccess(streetError);
        } else {
            valError(streetError);
            state = false;
        }
        if (district.getText().toString().matches("[a-zA-Z ]+")) {
            valSuccess(districtError);
        } else {
            valError(districtError);
            state = false;
        }
        if (city.getText().toString().matches("[a-zA-Z ]+")) {
            valSuccess(cityError);
        } else {
            valError(cityError);
            state = false;
        }
        if (postcode.getText().toString().matches("[a-zA-Z0-9 ]+") &&
                postcode.getText().length() >= 5 && postcode.getText().length() <= 7) {
            valSuccess(postcodeError);
        } else {
            valError(postcodeError);
            state = false;
        }
        return state;
    }

    private boolean addressExists(Connection con, String house, String postcode) throws SQLException {
        String s1 = "SELECT * FROM address WHERE id='"+house+postcode+"';";
        Statement stmt = con.createStatement();
        if (!stmt.executeQuery(s1).next()) {
            stmt.close();
            return false;
        } else {
            stmt.close();
            return true;
        }
    }

    private boolean personExists(Connection con, String forename, String surname, String dob, String house, String postcode) throws SQLException {
        String s1 = "SELECT * FROM patients WHERE forename='"+forename+"' AND surname='"+surname+"' AND dob='"+dob+"' AND addressid='"+house+postcode+"';";
        Statement stmt = con.createStatement();
        if (!stmt.executeQuery(s1).next()) {
            stmt.close();
            return false;
        } else {
            stmt.close();
            JOptionPane.showMessageDialog(null, "Patient already exists");
            return true;
        }
    }

    private void valError(JLabel label) {
        label.setForeground(Color.RED);
        label.setText("X");
    }

    private void valSuccess(JLabel label) {
        label.setForeground(Color.GREEN);
        label.setText("✓");
    }

    // function for adding to the layout
    public void add(Component c, int x, int y, int w, int h) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; // horizontal
        gbc.gridy = y; // vertical
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 20; // padding between the components horizontally
        gbc.ipady = 5; // padding between the components vertically
        add(c, gbc);
    }

}
