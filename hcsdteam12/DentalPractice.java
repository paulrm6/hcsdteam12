package hcsdteam12;

import hcsdteam12.calendar.*;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Paul on 13/12/2015
 */
public class DentalPractice extends JFrame implements ActionListener{
    private JPanel logIn = new JPanel(),
            secretaryPanel = new JPanel(),
            partnerPanel = new JPanel();

    public DentalPractice() {
        setTitle("Dental Practice System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logIn.setPreferredSize(new Dimension(500,500));
        secretaryPanel.setPreferredSize(new Dimension(500,500));
        partnerPanel.setPreferredSize(new Dimension(500,500));
    }

    private void createFrame(){
        add(logIn);
        add(secretaryPanel);
        add(partnerPanel);
        setContentPane(logIn);
        setSize(500,500);
        pack();
        setVisible(true);
    }

    private void addButton(String name, JPanel panel){
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(100,50));
        button.setActionCommand(name);
        button.addActionListener(this);
        panel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Secretary".equals(e.getActionCommand())) {
            setContentPane(secretaryPanel);
            validate();
        }
        if ("Partner".equals(e.getActionCommand())) {
            setContentPane(partnerPanel);
            validate();
        }
        if ("Back".equals(e.getActionCommand())) {
            setContentPane(logIn);
            validate();
        }
        if ("Calendar/Appointments".equals(e.getActionCommand())) {
            MainCalendar.display();
        }
    }

    public static void main (String args[]) {
        DentalPractice dp = new DentalPractice();
        dp.addButton("Secretary", dp.logIn);
        dp.addButton("Partner", dp.logIn);
        dp.addButton("Back", dp.secretaryPanel);
        dp.addButton("Patient Info", dp.secretaryPanel);
        dp.addButton("Calendar/Appointments", dp.secretaryPanel);
        dp.addButton("Back", dp.partnerPanel);
        dp.addButton("Calendar", dp.partnerPanel);
        dp.addButton("Post Appointment", dp.partnerPanel);
        dp.createFrame();
    }
}


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