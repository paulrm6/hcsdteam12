/**
 * Created by Joseph on 08/12/2015.
 */

import javax.swing.*;
import java.awt.*;

public class Registration extends JFrame {

    public static void main(String[] args) {

        String [] titles = new String[] {"Mr", "Ms", "Miss", "Mrs", "Dr"};
        JComboBox title = new JComboBox(titles);
        JTextField forename = new JTextField();
        JTextField surname = new JTextField();
        JTextField dob = new JTextField();
        JTextField phone = new JTextField();
        JTextField house = new JTextField();
        JTextField street = new JTextField();
        JTextField district = new JTextField();
        JTextField city = new JTextField();
        JTextField postcode = new JTextField();
        JButton submit = new JButton("Submit");

        JFrame box = new JFrame("Registration form");
        box.setSize(500,500);
        GridLayout grid = new GridLayout(11,2,30,0);
        box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        box.setLayout(grid);
        box.add(new JLabel("Title: "));
        box.add(title);
        box.add(new JLabel("Forename: "));
        box.add(forename);
        box.add(new JLabel("Surname: "));
        box.add(surname);
        box.add(new JLabel("Date of birth: "));
        box.add(dob);
        box.add(new JLabel("Phone number: "));
        box.add(phone);
        box.add(new JLabel("House number: "));
        box.add(house);
        box.add(new JLabel("Street: "));
        box.add(street);
        box.add(new JLabel("District: "));
        box.add(district);
        box.add(new JLabel("City: "));
        box.add(city);
        box.add(new JLabel("Postcode: "));
        box.add(postcode);
        box.add(new JLabel(""));
        box.add(submit);

        box.setVisible(true);

    }

}
