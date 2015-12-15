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
    private final DateFormat date = new SimpleDateFormat("dd/mm/yyyy");
    // Creation of all variables needed for the form
    private JLabel titleLabel, forenameLabel, surnameLabel, dobLabel, phoneLabel,
            houseLabel, streetLabel, districtLabel, cityLabel, postcodeLabel,
            forenameError, surnameError, dobError, phoneError,
            houseError, streetError, districtError, cityError, postcodeError,
            confirm;
    private JComboBox title;
    private JTextField forename, surname, phone,
            house, street, district, city, postcode;
    private JFormattedTextField dob;
    private JButton register, close, view, update;
    private int currentPatient; //Holds an int relating to the id of the patient currently being viewed/update

    public Registration() {

        // Array of titles to add into combo box
        String[] titles = new String[]{"Mr", "Mrs", "Miss", "Ms", "Dr"};

        //set properties of the frame
        setTitle("Patient Info");
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
        confirm = new JLabel("");

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
        } catch (ParseException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Laying out the labels with GridBagConstraint
        add(titleLabel, 0, 1, 1, 1);
        add(forenameLabel, 0, 2, 1, 1);
        add(surnameLabel, 0, 3, 1, 1);
        add(dobLabel, 0, 4, 1, 1);
        add(phoneLabel, 0, 5, 1, 1);
        add(houseLabel, 0, 6, 1, 1);
        add(streetLabel, 0, 7, 1, 1);
        add(districtLabel, 0, 8, 1, 1);
        add(cityLabel, 0, 9, 1, 1);
        add(postcodeLabel, 0, 10, 1, 1);
        add(forenameError, 2, 2, 1, 1);
        add(surnameError, 2, 3, 1, 1);
        add(dobError, 2, 4, 1, 1);
        add(phoneError, 2, 5, 1, 1);
        add(houseError, 2, 6, 1, 1);
        add(streetError, 2, 7, 1, 1);
        add(districtError, 2, 8, 1, 1);
        add(cityError, 2, 9, 1, 1);
        add(postcodeError, 2, 10, 1, 1);
        add(confirm, 1, 13, 1, 1);

        // Laying out the components with GridBagConstraint
        add(title, 1, 1, 1, 1);
        add(forename, 1, 2, 1, 1);
        add(surname, 1, 3, 1, 1);
        add(dob, 1, 4, 1, 1);
        add(phone, 1, 5, 1, 1);
        add(house, 1, 6, 1, 1);
        add(street, 1, 7, 1, 1);
        add(district, 1, 8, 1, 1);
        add(city, 1, 9, 1, 1);
        add(postcode, 1, 10, 1, 1);
        add(register, 1, 11, 1, 1); // button
        add(view, 0, 0, 1, 1); // button
        add(update, 0, 11, 1, 1); // button

        // Check for valid entry and adds details into database if valid, otherwise, returns an error
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirm.setText("");
                if (validEntry()) {
                    String dob = Database.changeDateFromForm(Registration.this.dob.getText());
                    if(Patient.create((String) title.getSelectedItem(),forename.getText(),surname.getText(),dob,phone.getText()
                            ,house.getText(),street.getText(),district.getText(),city.getText()
                            ,postcode.getText())!=null) {
                        confirm.setForeground(Color.GREEN);
                        confirm.setText("Added successfully");
                    } else {
                        confirm.setForeground(Color.RED);
                        confirm.setText("Add successful");
                    }
                }
            }
        });

        // Setting size, visibility and position
        setBounds(100, 100, 400, 450);
        setVisible(true);
        setLocationRelativeTo(null);

        // Retrieves information about a patient after taking postcode and selecting their name
        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Patient patient = Database.getPatient();
                if (patient != null) {
                        title.setSelectedItem(patient.getTitle());
                        forename.setText(patient.getForename());
                        surname.setText(patient.getSurname());
                        dob.setText(Database.changeDateFromDatabase(patient.getDob()));
                        phone.setText(patient.getPhoneNumber());
                        house.setText(patient.getHouseNumber());
                        street.setText(patient.getStreetName());
                        district.setText(patient.getDistrictName());
                        city.setText(patient.getCityName());
                        postcode.setText(patient.getPostcode());
                        currentPatient = patient.getId();
                }
            }
        });

        // Updates patient information
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirm.setText("");
                if (validEntry()) {
                    String dob = Database.changeDateFromForm(Registration.this.dob.getText());
                    Patient patient = new Patient(currentPatient);
                    patient.setTitle((String) title.getSelectedItem());
                    patient.setForename(forename.getText());
                    patient.setSurname(surname.getText());
                    patient.setDob(dob);
                    patient.setPhoneNumber(phone.getText());
                    patient.setHouseNumber(house.getText());
                    patient.setStreetName(street.getText());
                    patient.setDistrictName(district.getText());
                    patient.setCityName(city.getText());
                    patient.setPostcode(postcode.getText());
                    if(patient.update()) {
                        confirm.setForeground(Color.GREEN);
                        confirm.setText("Updated successfully");
                    } else {
                        confirm.setForeground(Color.RED);
                        confirm.setText("Updated unsuccessfully");
                    }
                }
            }
        });

    }

    // Validates each field for type or whether it is filled in
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

    // Formatting for validation if there is an error
    private void valError(JLabel label) {
        label.setForeground(Color.RED);
        label.setText("X");
    }

    // Formatting for validation if there is no error
    private void valSuccess(JLabel label) {
        label.setForeground(Color.GREEN);
        label.setText("✓");
    }

    // function for adding to the layout
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
