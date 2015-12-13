package hcsdteam12;

import hcsdteam12.calendar.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Authors: Paul
 */
public class DentalPractice extends JPanel implements ActionListener{

    JButton buttonSecretary,buttonPartner;
    JFrame logInFrame;

    public DentalPractice(JFrame frame) {
        logInFrame = frame;
        buttonSecretary = new JButton("Secretary");
        buttonSecretary.setActionCommand("secretary");

        buttonPartner = new JButton("Partner");
        buttonPartner.setActionCommand("partner");

        buttonSecretary.addActionListener(this);
        buttonPartner.addActionListener(this);

        add(buttonSecretary);
        add(buttonPartner);
    }

    public static void main (String args[]) {
        /**
         * Start by displaying a main screen with the different users that are needed
         * Users:
         * Secretary
         * Partner
         *
         * Once selected display the following options for Secretary
         * Register Patient
         * Update patient info (contained within is health care plan stuff)
         * Book appointment
         * Find and/or cancel appointment (display on calendar WEEKLY VIEW)
         * Record when paid
         * Book holiday (empty appointment)
         * Review unpaid treatments given to a patient as a list listing costs and total cost (auto uses healthcare plan)
         *
         *
         * Partner -
         * View appointments for week
         * Record having seen patient, put in treatments given and cost
         */
        //MainCalendar.display();
        createGUI();
    }

    private static void createGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Dental Practice System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DentalPractice newContentPane = new DentalPractice(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("secretary".equals(e.getActionCommand())) {
            MainCalendar.display();
            logInFrame.setVisible(false);
        }
    }
}
