/**
 * Created by Joseph on 08/12/2015.
 */

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton submit;

    private final DateFormat date = new SimpleDateFormat("dd/mm/yyyy");

    public Registration() {

        // Array of titles to add into combo box
        String[] titles = new String[] {"Mr","Mrs","Miss","Ms","Dr"};

        //set properties of the frame
        setTitle("Registration form");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        submit = new JButton("Register"); // button
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
        add(titleLabel,0,0,1,1);
        add(forenameLabel,0,1,1,1);
        add(surnameLabel,0,2,1,1);
        add(dobLabel,0,3,1,1);
        add(phoneLabel,0,4,1,1);
        add(houseLabel,0,5,1,1);
        add(streetLabel,0,6,1,1);
        add(districtLabel,0,7,1,1);
        add(cityLabel,0,8,1,1);
        add(postcodeLabel,0,9,1,1);
        add(forenameError,2,1,1,1);
        add(surnameError,2,2,1,1);
        add(dobError,2,3,1,1);
        add(phoneError,2,4,1,1);
        add(houseError,2,5,1,1);
        add(streetError,2,6,1,1);
        add(districtError,2,7,1,1);
        add(cityError,2,8,1,1);
        add(postcodeError,2,9,1,1);

        // Laying out the components with GridBagConstraint
        add(title,1,0,1,1);
        add(forename,1,1,1,1);
        add(surname,1,2,1,1);
        add(dob,1,3,1,1);
        add(phone,1,4,1,1);
        add(house,1,5,1,1);
        add(street,1,6,1,1);
        add(district,1,7,1,1);
        add(city,1,8,1,1);
        add(postcode,1,9,1,1);
        add(submit,1,10,1,1); // button

        // Validation
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (forename.getText().toString().matches("[a-zA-z]+")) {
                    forenameError.setForeground(Color.GREEN);
                    forenameError.setText("✓");
                } else {
                    forenameError.setForeground(Color.RED);
                    forenameError.setText("X");
                }
                if (surname.getText().toString().matches("[a-zA-z]+")) {
                    surnameError.setForeground(Color.GREEN);
                    surnameError.setText("✓");
                } else {
                    surnameError.setForeground(Color.RED);
                    surnameError.setText("X");
                }
                if (dob.getText().toString().matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")) {
                    dobError.setForeground(Color.GREEN);
                    dobError.setText("✓");
                } else {
                    dobError.setForeground(Color.RED);
                    dobError.setText("X");
                }
                if (phone.getText().toString().matches("[0-9]+") && phone.getText().length() == 11) {
                    phoneError.setForeground(Color.GREEN);
                    phoneError.setText("✓");
                } else {
                    phoneError.setForeground(Color.RED);
                    phoneError.setText("X");
                }
                if (house.getText().toString().matches("[0-9a-zA-z]+")) {
                    houseError.setForeground(Color.GREEN);
                    houseError.setText("✓");
                } else {
                    houseError.setForeground(Color.RED);
                    houseError.setText("X");
                }
                if (street.getText().toString().matches("[a-zA-Z]+")) {
                    streetError.setForeground(Color.GREEN);
                    streetError.setText("✓");
                } else {
                    streetError.setForeground(Color.RED);
                    streetError.setText("X");
                }
                if (district.getText().toString().matches("[a-zA-Z]+")) {
                    districtError.setForeground(Color.GREEN);
                    districtError.setText("✓");
                } else {
                    districtError.setForeground(Color.RED);
                    districtError.setText("X");
                }
                if (city.getText().toString().matches("[a-zA-Z]+")) {
                    cityError.setForeground(Color.GREEN);
                    cityError.setText("✓");
                } else {
                    cityError.setForeground(Color.RED);
                    cityError.setText("X");
                }
                if (postcode.getText().toString().matches("[a-zA-Z0-9]+") &&
                        postcode.getText().length() >= 5 && postcode.getText().length() <= 7) {
                    postcodeError.setForeground(Color.GREEN);
                    postcodeError.setText("✓");
                } else {
                    postcodeError.setForeground(Color.RED);
                    postcodeError.setText("X");
                }
            }
        });

        // Setting size and visibility
        setBounds(100,100,400,350);
        setVisible(true);

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

    public static void main(String[] args) {

        // Creating the GUI
        new Registration();

    }

}
