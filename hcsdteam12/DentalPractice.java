package hcsdteam12;

import hcsdteam12.calendar.*;
import hcsdteam12.calendar.gui.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;

/**
 * Created by Paul on 13/12/2015
 * Modified by Joseph on 14/13/2015 (layout)
 */
public class DentalPractice extends JFrame implements ActionListener{
    private JPanel logIn = new JPanel(),
            secretaryPanel = new JPanel(),
            partnerPanel = new JPanel();
    private JLabel welcome;

    public DentalPractice() {
        setTitle("Dental Practice System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logIn.setPreferredSize(new Dimension(500,300));
        secretaryPanel.setPreferredSize(new Dimension(400,400));
        partnerPanel.setPreferredSize(new Dimension(400,400));

        welcome = new JLabel("Welcome!",JLabel.CENTER);
        welcome.setFont(new Font("Serif", Font.PLAIN, 35));
    }

    private void createFrame(){
        logIn.setLayout(new GridBagLayout());
        secretaryPanel.setLayout(new GridBagLayout());
        partnerPanel.setLayout(new GridBagLayout());
        JTextArea welcome = new JTextArea("Welcome to HCSDTeam12's Dental System!",5,20);
        textProperty(welcome, logIn);
        JTextArea secretaryText = new JTextArea("Secretary's panel",5,20);
        textProperty(secretaryText, secretaryPanel);
        JTextArea partnerText = new JTextArea("Partner's panel",5,20);
        textProperty(partnerText, partnerPanel);
        addComponent(addButton("Secretary"),0,1,1,1,logIn);
        addComponent(addButton("Partner"),1,1,1,1,logIn);
        addComponent(addButton("Patient Info"),1,2,1,1,secretaryPanel);
        addComponent(addButton("Calendar/Appointments"),1,3,1,1,secretaryPanel);
        addComponent(addButton("Healthcare Plans"),1,4,1,1,secretaryPanel);
        addComponent(addButton("Treatments"),1,5,1,1,secretaryPanel);
        addComponent(addButton("Back"),2,5,1,1,secretaryPanel);
        addComponent(addButton("Calendar"),1,1,1,1,partnerPanel);
        addComponent(addButton("Post Appointment"),1,2,1,1,partnerPanel);
        addComponent(addButton("Back"),2,2,1,1,partnerPanel);
        add(logIn);
        add(secretaryPanel);
        add(partnerPanel);
        setContentPane(logIn);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void textProperty(JTextArea area, JPanel panel) {
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setEditable(false);
        area.setFont(new Font("Calibri", Font.PLAIN, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = GridBagConstraints.RELATIVE;
        panel.add(area, gbc);
    }

    private JButton addButton(String name){
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(150,100));
        button.setActionCommand(name);
        button.addActionListener(this);
        return button;
    }

    public void addComponent(Component c, int x, int y, int w, int h, JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.insets = new Insets(0,50,20,50);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(c, gbc);
    }

//    @Override
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
        if ("Patient Info".equals(e.getActionCommand())) {
            new Registration();
        }
        if ("Healthcare Plans".equals(e.getActionCommand())) {
            new HealthcarePlans();
        }
        if ("Treatments".equals(e.getActionCommand())) {
            new Treatments();
        }
        if ("Post Appointment".equals(e.getActionCommand())) {
            new PostAppointment();
        }
    }

    public static void main (String args[]) {
        DentalPractice dp = new DentalPractice();
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