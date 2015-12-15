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
    private final DateFormat time = new SimpleDateFormat("HH:mm:ss");
    // Creation of all variables needed for the form
    private JLabel dateLabel, startTimeLabel, endTimeLabel, partnerNameLabel, patientNameLabel,
            dateError, startTimeError, endTimeError, partnerNameError, patientNameError,
            confirm, patientName, partnerName;
    private JFormattedTextField date, startTime, endTime;
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
        startTime = new JFormattedTextField(time);
        startTime.setColumns(6);
        endTime = new JFormattedTextField(time);
        endTime.setColumns(6);
        patientName = new JLabel("");
        partnerName = new JLabel("");
        addAppointment = new JButton("Add Appointment"); // button
        view = new JButton("View Different Appointment");
        update = new JButton("Update Appointment");
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(date);
            MaskFormatter timeMask = new MaskFormatter("##:##:##");
            timeMask.install(startTime);
            timeMask.install(endTime);
        } catch (ParseException ex) {
            Logger.getLogger(AppointmentForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Laying out the labels with GridBagConstraint
        add(dateLabel, 0, 1, 1, 1);
        add(startTimeLabel, 0, 2, 1, 1);
        add(endTimeLabel, 0, 3, 1, 1);
        add(partnerNameLabel, 0, 4, 1, 1);
        add(patientNameLabel, 0, 5, 1, 1);
        add(dateError, 2, 1, 1, 1);
        add(startTimeError, 2, 2, 1, 1);
        add(endTimeError, 2, 3, 1, 1);
        add(partnerNameError, 2, 4, 1, 1);
        add(patientNameError, 2, 5, 1, 1);
        add(confirm, 1, 13, 1, 1);

        // Laying out the components with GridBagConstraint
        add(date, 1, 1, 1, 1);
        add(startTime, 1, 2, 1, 1);
        add(endTime, 1, 3, 1, 1);
        add(partnerName, 1, 4, 1, 1);
        add(patientName, 1, 5, 1, 1);
        add(addAppointment, 1, 11, 1, 1); // button
        add(view, 0, 0, 1, 1); // button
        add(update, 0, 11, 1, 1); // button

        // Check for valid entry and adds details into database if valid, otherwise, returns an error
        addAppointment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirm.setText("");
                if (validEntry()) {
                    if (Appointment.create(date.getText(), startTime.getText(), endTime.getText(), currentPatient, currentPartner) != null) {
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
                        appointment.setStartTime(startTime.getText());
                        appointment.setEndTime(endTime.getText());
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
            startTime.setText(appointment.getStartTime());
            endTime.setText(appointment.getEndTime());
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
        if (startTime.getText().toString().matches("([0-9]{2}):([0-9]{2}):([0-9]{2})")) {
            valSuccess(startTimeError);
        } else {
            valError(startTimeError);
            state = false;
        }
        if (endTime.getText().toString().matches("([0-9]{2}):([0-9]{2}):([0-9]{2})")) {
            valSuccess(endTimeError);
        } else {
            valError(endTimeError);
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
