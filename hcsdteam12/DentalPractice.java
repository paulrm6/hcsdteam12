package hcsdteam12;

import hcsdteam12.calendar.gui.CalendarGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Paul on 13/12/2015
 * Modified by Joseph on 14/12/2015 (layout)
 * Modified by Terence Kong on 15/12/2015
 */
public class DentalPractice extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel logIn = new JPanel(),
            secretaryPanel = new JPanel(),
            partnerPanel = new JPanel();

    public DentalPractice() {

        // Setting frame properties
        setTitle("Dental Practice System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logIn.setPreferredSize(new Dimension(500, 300));
        secretaryPanel.setPreferredSize(new Dimension(500, 300));
        partnerPanel.setPreferredSize(new Dimension(500, 300));
    }

    public static void main(String args[]) {
        DentalPractice dp = new DentalPractice();
        dp.createFrame();
    }

    // Adds all the components into the panel with positioning
    private void createFrame() {
        logIn.setLayout(new GridBagLayout());
        secretaryPanel.setLayout(new GridBagLayout());
        partnerPanel.setLayout(new GridBagLayout());
        JTextArea welcome = new JTextArea("Welcome to HCSDTeam12's Dental System!", 5, 20);
        textProperty(welcome, logIn);
        JTextArea secretaryText = new JTextArea("Secretary's panel", 5, 20);
        textProperty(secretaryText, secretaryPanel);
        JTextArea partnerText = new JTextArea("Partner's panel", 5, 20);
        textProperty(partnerText, partnerPanel);
        addComponent(addButton("Secretary"), 0, 1, 1, 1, logIn);
        addComponent(addButton("Partner"), 1, 1, 1, 1, logIn);
        addComponent(addButton("Patient Info"), 1, 2, 1, 1, secretaryPanel);
        addComponent(addButton("Book Appointment"), 1, 3, 1, 1, secretaryPanel);
        addComponent(addButton("Calendar/Appointments"), 1, 4, 1, 1, secretaryPanel);
        addComponent(addButton("Healthcare Plans"), 1, 5, 1, 1, secretaryPanel);
        addComponent(addButton("Receipts"), 1, 6, 1, 1, secretaryPanel);
        addComponent(addButton("Back"), 2, 6, 1, 1, secretaryPanel);
        addComponent(addButton("Calendar"), 1, 1, 1, 1, partnerPanel);
        addComponent(addButton("Post Appointment"), 1, 2, 1, 1, partnerPanel);
        addComponent(addButton("Back"), 2, 2, 1, 1, partnerPanel);
        add(logIn);
        add(secretaryPanel);
        add(partnerPanel);
        setContentPane(logIn);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Adds the same properties to each text field
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

    // Adds the same properties to each button
    private JButton addButton(String name) {
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(150, 100));
        button.setActionCommand(name);
        button.addActionListener(this);
        return button;
    }

    // Adds components (buttons) with particular positioning and properties
    public void addComponent(Component c, int x, int y, int w, int h, JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.insets = new Insets(0, 50, 20, 50);
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
        if ("Book Appointment".equals(e.getActionCommand())) {
            new AppointmentForm();
        }
        if ("Calendar/Appointments".equals(e.getActionCommand())) {
            new Calendar(0);
        }
        if ("Patient Info".equals(e.getActionCommand())) {
            new Registration();
        }
        if ("Healthcare Plans".equals(e.getActionCommand())) {
            new HealthcarePlans();
        }
        if ("Receipts".equals(e.getActionCommand())) {
            new Receipts();
        }
        if ("Post Appointment".equals(e.getActionCommand())) {
            new PostAppointment();
        }
        if("Calendar".equals(e.getActionCommand())) {
            int partnerID = Prompt.getPartnerID();
            if(partnerID != -1) {

            }
        }
    }
}


/**
 * Start by displaying a main screen with the different users that are needed
 * Users:
 * Secretary
 * Partner
 * <p>
 * Once selected display the following options for Secretary
 * Register Patient
 * Update patient info (contained within is health care plan stuff)
 * Book appointment
 * Find and/or cancel appointment (display on calendar WEEKLY VIEW)
 * Record when paid
 * Book holiday (empty appointment)
 * Review unpaid treatments given to a patient as a list listing costs and total cost (auto uses healthcare plan)
 * <p>
 * <p>
 * Partner -
 * View appointments for week
 * Record having seen patient, put in treatments given and cost
 */