package hcsdteam12;
/**
 * Created by Paul on 15/12/2015.
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

public class AppointmentForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
    // Creation of all variables needed for the form
    private JLabel dateLabel, startTimeLabel, endTimeLabel, partnerNameLabel, patientNameLabel,
            dateError, startTimeError, endTimeError, partnerNameError, patientNameError,
            confirm, patientName, partnerName;
    private JFormattedTextField date;
    private JComboBox startH, startM, endH, endM;
    private JButton addAppointment, view, update;
    private int currentPatient, currentPartner;
    private String currentDate, currentStartTime;

    public AppointmentForm() {
        createForm();
        updatePatient();
        updatePartner();
    }

    private void updatePatient() {
        Patient patient = Prompt.getPatient();
        patientName.setText(patient.getForename()+" "+patient.getSurname());
        currentPatient = patient.getId();
    }

    private void updatePartner() {
        Partner partner = new Partner(Prompt.getPartnerID());
        partnerName.setText(partner.getForename()+" "+partner.getSurname()+" - "+partner.getRole());
        currentPartner = partner.getId();
    }

    public AppointmentForm(Appointment appointment) {
        createForm();
        update(appointment);
    }

    private void createForm() {
        //set properties of the frame
        String[] hours = new String[]{"09","10","11","12","13","14","15,","16"};
        String[] minutes = new String[]{"00","05","10","15","20","25","30","35","40","45","50","55",};
        setTitle("Appointment Form");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Creation of labels
        dateLabel = new JLabel("Date (dd/mm/yyyy):");
        startTimeLabel = new JLabel("Start time (hh:mm:ss):");
        endTimeLabel = new JLabel("End time (hh:mm:ss):");
        partnerNameLabel = new JLabel("Partner name:");
        patientNameLabel = new JLabel("Patient name:");
        dateError = new JLabel("");
        startTimeError = new JLabel("");
        endTimeError = new JLabel("");
        partnerNameError = new JLabel("");
        patientNameError = new JLabel("");
        confirm = new JLabel("");

        // Creation of components

        // Setting the dateFormat of birth function to take a particular format
        date = new JFormattedTextField(dateFormat);
        date.setColumns(5);
        startH = new JComboBox(hours);
        startM = new JComboBox(minutes);
        endH = new JComboBox(hours);
        endM = new JComboBox(minutes);
        patientName = new JLabel("");
        partnerName = new JLabel("");
        addAppointment = new JButton("Add Appointment"); // button
        view = new JButton("View Different Appointment");
        update = new JButton("Update Appointment");
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(date);
        } catch (ParseException ex) {
            Logger.getLogger(AppointmentForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Laying out the labels with GridBagConstraint
        add(patientNameLabel, 0, 1, 1, 1);
        add(partnerNameLabel, 0, 2, 1, 1);
        add(dateLabel, 0, 3, 1, 1);
        add(startTimeLabel, 0, 4, 1, 1);
        add(endTimeLabel, 0, 5, 1, 1);
        add(dateError, 2, 1, 1, 1);
        add(startTimeError, 2, 2, 1, 1);
        add(endTimeError, 2, 3, 1, 1);
        add(partnerNameError, 2, 4, 1, 1);
        add(patientNameError, 2, 5, 1, 1);
        add(confirm, 1, 13, 1, 1);

        // Laying out the components with GridBagConstraint
        add(patientName, 1, 1, 2, 1);
        add(partnerName, 1, 2, 2, 1);
        add(date, 1, 3, 2, 1);
        add(startH, 1, 4, 1, 1);
        add(startM, 2, 4, 1, 1);
        add(endH, 1, 5, 1, 1);
        add(endM, 2, 5, 1, 1);
        add(addAppointment, 1, 6, 2, 1); // button
        add(view, 0, 7, 2, 1); // button
        add(update, 0, 6, 1, 1); // button

        // Check for valid entry and adds details into database if valid, otherwise, returns an error
        addAppointment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirm.setText("");
                if (validEntry()) {
                    if (Appointment.create(date.getText(), startH.getSelectedItem().toString()+":"+startM.getSelectedItem().toString()+":00",
                            endH.getSelectedItem().toString()+":"+endM.getSelectedItem().toString()+":00", currentPatient, currentPartner) != null) {
                        confirm.setForeground(Color.GREEN);
                        confirm.setText("Added successfully");
                    } else {
                        confirm.setForeground(Color.RED);
                        confirm.setText("Appointment Clash");
                    }
                }
            }
        });

        // Setting size, visibility and position
        setBounds(100, 100, 450, 450);
        setVisible(true);
        setLocationRelativeTo(null);

        // Retrieves information about a patient after taking postcode and selecting their name
        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Appointment appointment = Prompt.getAppointment();
                update(appointment);
            }
        });

        // Updates patient information
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirm.setText("");
                if (validEntry()) {
                    Appointment appointment = new Appointment(currentDate, currentStartTime, currentPartner);
                    if(appointment!=null) {
                        appointment.setDate(date.getText());
                        appointment.setStartTime(startH.getSelectedItem().toString()+":"+startM.getSelectedItem().toString()+":00");
                        appointment.setEndTime(endH.getSelectedItem().toString()+":"+endM.getSelectedItem().toString()+":00");
                        appointment.setPatientid(currentPatient);
                        appointment.setPartnerid(currentPartner);
                        if (appointment.update()) {
                            confirm.setForeground(Color.GREEN);
                            confirm.setText("Updated successfully");
                        } else {
                            confirm.setForeground(Color.RED);
                            confirm.setText("Updated unsuccessfully");
                        }
                    } else {
                        confirm.setForeground(Color.RED);
                        confirm.setText("Not Updated due to a Clash");
                    }
                }
            }
        });
    }

    private void update(Appointment appointment) {
        if (appointment.isExists()) {
            date.setText(appointment.getDate());
            startH.setSelectedItem(appointment.getStartHour());
            startM.setSelectedItem(appointment.getStartMinute());
            endH.setSelectedItem(appointment.getEndHour());
            endM.setSelectedItem(appointment.getEndMinute());
            Patient patient = new Patient(appointment.getPatientid());
            patientName.setText(patient.getForename()+" "+patient.getSurname());
            Partner partner = new Partner(appointment.getPartnerid());
            partnerName.setText(partner.getForename()+" "+partner.getSurname()+" - "+partner.getRole());
            currentPatient = appointment.getPatientid();
            currentPartner = appointment.getPartnerid();
            currentDate = appointment.getDate();
            currentStartTime = appointment.getStartTime();
        } else {
            updatePatient();
            updatePartner();
        }
    }

    // Validates each field for type or whether it is filled in
    private boolean validEntry() {
        boolean state = true;
        if (date.getText().toString().matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            valSuccess(dateError);
        } else {
            valError(dateError);
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

    public static void main(String[] args) {
        new AppointmentForm(new Appointment("2015-12-14","10:00:00",2));
    }

}
