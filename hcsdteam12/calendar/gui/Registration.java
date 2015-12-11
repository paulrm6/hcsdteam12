package hcsdteam12.calendar.gui;

/**
 * Created by Joseph on 08/12/2015.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Registration extends JFrame {

    // Creation of all variables needed for the form
    private JLabel titleLabel, forenameLabel, surnameLabel, dobLabel, phoneLabel,
                    houseLabel, streetLabel, districtLabel, cityLabel, postcodeLabel;
    private JComboBox title;
    private JTextField forename, surname, dob, phone,
                        house, street, district, city, postcode;
    private JButton submit;

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
        dobLabel = new JLabel("Date of birth:");
        phoneLabel = new JLabel("Phone number:");
        houseLabel = new JLabel("House number:");
        streetLabel = new JLabel("Street:");
        districtLabel = new JLabel("District:");
        cityLabel = new JLabel("City:");
        postcodeLabel = new JLabel("Postcode:");

        // Creation of components
        title = new JComboBox(titles);
        forename = new JTextField(10);
        surname = new JTextField(10);
        dob = new JTextField(5);
        phone = new JTextField(10);
        house = new JTextField(1);
        street = new JTextField(10);
        district = new JTextField(10);
        city = new JTextField(10);
        postcode = new JTextField(5);
        submit = new JButton("Register"); // button

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

        // Setting size and visibility
        setBounds(100,100,300,350);
        setVisible(true);

    }

    // function for adding to the layout
    public void add(Component c, int x, int y, int w, int h) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; // horizontal
        gbc.gridy = y; // vertical
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.ipadx = 20; // padding between the components horizontally
        gbc.ipady = 5; // padding between the components vertically
        gbc.anchor = GridBagConstraints.WEST;
        add(c, gbc);
    }

    public static void main(String[] args) {

        // Creating the GUI
        new Registration();

    }

}