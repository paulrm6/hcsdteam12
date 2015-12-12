package hcsdteam12.calendar.gui;

/**
 * AppointmentForm.java launches a window where appointments can be made or altered.
 * 
 * @author Seng Kin(Terence), Kong
 **/
import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import hcsdteam12.calendar.data.Appointment;

public class AppointmentForm extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	protected Calendar presenttime;
	protected CalendarFrame frame;
	protected String partner, description;
	protected int month;
	protected Appointment appointment; 
	public boolean EDIT; //Is the appointment editable?
	public boolean TIME; //Does appointment have a start time?
	
	//Generates a new black appointment window after "Add New Appointment" button is clicked on.
	public AppointmentForm(CalendarFrame frame2, String title, boolean modal) {
		super(frame2, title, modal);
		commenceComponents();
		
		EDIT = TIME = false;
		frame = frame2;
		partner = description = "";
		
		presenttime = Calendar.getInstance();
		month = Calendar.MONTH;
		MonthsDropDown.setSelectedIndex(presenttime.get(month));
		YearText.setText((new Integer(presenttime.get(Calendar.YEAR))).toString());
		DayText.setText((new Integer(presenttime.get(Calendar.DAY_OF_MONTH))).toString());
	}

	//Utilized to edit when a window is opened to edit an appointment
	public AppointmentForm(CalendarFrame frame, String header, boolean modal, Appointment editedInfo) {
		super(frame, header, modal);
		commenceComponents();
		
		PatientNameText.setText(editedInfo.retrievePatientName());
		HealthcarePlanText.setText(editedInfo.retrieveHealthcarePlan());
		PatientIDText.setText(editedInfo.retrievePatientID());
		MonthsDropDown.setSelectedIndex(editedInfo.retrieveStartMonth());
	
		//If the Appointment was already given a selected start and end time.   	  
		if(editedInfo.retrieveDuration() > 0) {
		
			TIME = true;
			StartMinutesDropDown.setSelectedIndex(editedInfo.retrieveStartMinute());
			StartHoursDropDown.setSelectedIndex(editedInfo.retrieveStartHour());
			EndMinutesDropDown.setSelectedIndex(editedInfo.retrieveDuration() / 60);
			EndHoursDropDown.setSelectedIndex(editedInfo.retrieveDuration() % 60);
			DurationTickBox.setSelected(true);
		}
	
		TIME = false;
		YearText.setText(String.valueOf(editedInfo.retrieveStartYear()));
		DayText.setText(String.valueOf(editedInfo.retrieveStartDay()));
		
		PartnerText.setText(String.valueOf(editedInfo.retrievePartner()));
		DescriptionText.setText(String.valueOf(editedInfo.retrieveDescription()));
		appointment = editedInfo;
		EDIT = true;
	}
	
	//Runs a validation check to whether the text fields contains information.
    private boolean confirmTextFields() {
		if(PatientNameText.getText().equals("") || HealthcarePlanText.getText().equals("") ||PatientIDText.getText().equals("") || DayText.getText().equals("") || YearText.getText().equals("")) {
			return false;
		}
		else {
			return true;
		}
	}

    //Obtain the value of a month
	private int obtainValueOfMonth(String monthValue) {
		if(monthValue.equals("January"))
			return GregorianCalendar.JANUARY;
		else if (monthValue.equals("February"))
			return GregorianCalendar.FEBRUARY;
		else if (monthValue.equals("March"))
			return GregorianCalendar.MARCH;
		else if (monthValue.equals("April"))
			return GregorianCalendar.APRIL;
		else if (monthValue.equals("May"))
			return GregorianCalendar.MAY;
		else if (monthValue.equals("June"))
			return GregorianCalendar.JUNE;
		else if (monthValue.equals("July"))
			return GregorianCalendar.JULY;
		else if (monthValue.equals("August"))
			return GregorianCalendar.AUGUST;
		else if (monthValue.equals("September"))
			return GregorianCalendar.SEPTEMBER;
		else if (monthValue.equals("October"))
			return GregorianCalendar.OCTOBER;
		else if (monthValue.equals("November"))
			return GregorianCalendar.NOVEMBER;
		else if (monthValue.equals("December"))
			return GregorianCalendar.DECEMBER;
		return -1; //if invalid
	}

	private javax.swing.JButton toggleInsertAppointment;
	private javax.swing.JButton toggleEmptyFields;
	private javax.swing.JButton toggleExit;
	private javax.swing.JPanel container1;
	private javax.swing.JLabel textdisplay1;
	private javax.swing.JLabel textdisplay2;
	private javax.swing.JLabel textdisplay3;
	private javax.swing.JLabel textdisplay4;
	private javax.swing.JLabel textdisplay5;
	private javax.swing.JLabel textdisplay6;
	private javax.swing.JLabel textdisplay7;
	private javax.swing.JLabel textdisplay8;
	private javax.swing.JLabel textdisplay9;
	private javax.swing.JLabel textdisplay10;
	private javax.swing.JLabel textdisplay11;
	private javax.swing.JTextField PatientNameText;
	private javax.swing.JTextField HealthcarePlanText;
	private javax.swing.JTextField PatientIDText;
	private javax.swing.JTextField PartnerText;
	private javax.swing.JTextField DescriptionText;
	private javax.swing.JTextField DayText;
	private javax.swing.JTextField YearText;
	private javax.swing.JComboBox StartHoursDropDown;
	private javax.swing.JComboBox StartMinutesDropDown;
	private javax.swing.JComboBox EndHoursDropDown;
	private javax.swing.JComboBox EndMinutesDropDown;
	private javax.swing.JComboBox MonthsDropDown;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.JRadioButton StartAM;
	private javax.swing.JRadioButton StartPM;
	private javax.swing.JRadioButton EndAM;
	private javax.swing.JRadioButton EndPM;
	private javax.swing.JSeparator Divider1;
	private javax.swing.JSeparator Divider2;
	private javax.swing.JSeparator Divider3;
	private javax.swing.JCheckBox DurationTickBox;
	
   //Launch all the components in the appointment form 
   private void commenceComponents() {
		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();
		container1 = new javax.swing.JPanel();
		toggleInsertAppointment = new javax.swing.JButton();
		toggleExit = new javax.swing.JButton();
		textdisplay1 = new javax.swing.JLabel();
		textdisplay2 = new javax.swing.JLabel();
		textdisplay3 = new javax.swing.JLabel();
		textdisplay4 = new javax.swing.JLabel();
		textdisplay5 = new javax.swing.JLabel();
		textdisplay6 = new javax.swing.JLabel();
		textdisplay7 = new javax.swing.JLabel();
		textdisplay8 = new javax.swing.JLabel();
		textdisplay9 = new javax.swing.JLabel();
		textdisplay10 = new javax.swing.JLabel();
		toggleEmptyFields = new javax.swing.JButton();
		PatientNameText = new javax.swing.JTextField();
		HealthcarePlanText = new javax.swing.JTextField();
		PatientIDText = new javax.swing.JTextField();
		PartnerText = new javax.swing.JTextField();
		DescriptionText = new javax.swing.JTextField();
		DayText = new javax.swing.JTextField();
		MonthsDropDown = new javax.swing.JComboBox();
		YearText = new javax.swing.JTextField();
		StartHoursDropDown = new javax.swing.JComboBox();
		Divider1 = new javax.swing.JSeparator();
		Divider2 = new javax.swing.JSeparator();
		Divider3 = new javax.swing.JSeparator();
		StartAM = new javax.swing.JRadioButton();
		StartPM = new javax.swing.JRadioButton();
		StartMinutesDropDown = new javax.swing.JComboBox();
		DurationTickBox = new javax.swing.JCheckBox();
		EndHoursDropDown = new javax.swing.JComboBox();
		EndMinutesDropDown = new javax.swing.JComboBox();
		EndAM = new javax.swing.JRadioButton();
		EndPM = new javax.swing.JRadioButton();
		textdisplay11 = new javax.swing.JLabel();
		
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent action) {
				closeDialog(action);
			}
		});
		
		container1.setLayout(new design.awtcomponents.AbsoluteLayout());
		
		toggleInsertAppointment.setText("Insert Appointment");
		toggleInsertAppointment.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent action) {
				toggleInsertAppointmentMouseClicked(action);
			}
		});
		
		container1.add(toggleInsertAppointment, new design.awtcomponents.AbsoluteConstraints(10, 430, 150, -1));
		
		toggleEmptyFields.setText("Empty Fields");
		toggleEmptyFields.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent action) {
				toggleEmptyFieldsMouseClicked(action);
			}
		});
		container1.add(toggleEmptyFields, new design.awtcomponents.AbsoluteConstraints(160, 430, 150, -1));
		
		toggleExit.setText("Exit Window");
		toggleExit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent action) {
				toggleExitMouseClicked(action);
			}
		});
		container1.add(toggleExit, new design.awtcomponents.AbsoluteConstraints(310, 430, 150, -1));
		
		textdisplay1.setText("Patient Name");
		container1.add(textdisplay1, new design.awtcomponents.AbsoluteConstraints(10, 24, 100, -1));
		
		textdisplay2.setText("Healthcare Plan");
		container1.add(textdisplay2, new design.awtcomponents.AbsoluteConstraints(10, 55, 100, -1));
		
		textdisplay3.setText("Patient ID");
		container1.add(textdisplay3, new design.awtcomponents.AbsoluteConstraints(10, 86, -1, -1));
		
		container1.add(Divider1, new design.awtcomponents.AbsoluteConstraints(10, 110, 430, 10));
		
		textdisplay4.setText("Start Time");
		container1.add(textdisplay4, new design.awtcomponents.AbsoluteConstraints(10, 123, 80, -1));
		
		textdisplay11.setText("Stop Time");
		container1.add(textdisplay11, new design.awtcomponents.AbsoluteConstraints(10, 148, 80, -1));
		
		textdisplay5.setText("Day");
		container1.add(textdisplay5, new design.awtcomponents.AbsoluteConstraints(10, 180, 30, -1));
		
		textdisplay6.setText("Month");
		container1.add(textdisplay6, new design.awtcomponents.AbsoluteConstraints(130, 180, 40, -1));
		
		textdisplay7.setText("Year");
		container1.add(textdisplay7, new design.awtcomponents.AbsoluteConstraints(320, 180, 30, -1));
		
		container1.add(Divider2, new design.awtcomponents.AbsoluteConstraints(10, 210, 430, 10));
		
		textdisplay8.setText("Partner");
		container1.add(textdisplay8, new design.awtcomponents.AbsoluteConstraints(10, 230, 90, -1));
		
		textdisplay9.setText("Description");
		container1.add(textdisplay9, new design.awtcomponents.AbsoluteConstraints(10, 260, 110, -1));
		
		container1.add(Divider3, new design.awtcomponents.AbsoluteConstraints(10, 290, 430, 10));
		
		textdisplay10.setText("Recurrence:");
		container1.add(textdisplay10, new design.awtcomponents.AbsoluteConstraints(10, 300, 130, -1));
		
		container1.add(PatientNameText, new design.awtcomponents.AbsoluteConstraints(130, 20, 270, -1));
		container1.add(HealthcarePlanText, new design.awtcomponents.AbsoluteConstraints(130, 50, 270, -1));
		container1.add(PatientIDText, new design.awtcomponents.AbsoluteConstraints(130, 80, 270, -1));
		container1.add(PartnerText, new design.awtcomponents.AbsoluteConstraints(110, 225, 270, -1));
		container1.add(DescriptionText, new design.awtcomponents.AbsoluteConstraints(110, 255, 270, -1));
		
		container1.add(DayText, new design.awtcomponents.AbsoluteConstraints(50, 176, 50, -1));
		
		MonthsDropDown.setMaximumRowCount(12);
		MonthsDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
		MonthsDropDown.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent action) {
				MonthsDropDownActionPerformed(action);
			}
		});
		
		container1.add(MonthsDropDown, new design.awtcomponents.AbsoluteConstraints(170, 179, 130, 20));
		container1.add(YearText, new design.awtcomponents.AbsoluteConstraints(350, 175, 50, -1));
		
		StartHoursDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		container1.add(StartHoursDropDown, new design.awtcomponents.AbsoluteConstraints(130, 125, 50, 20));
		
		StartMinutesDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
		container1.add(StartMinutesDropDown, new design.awtcomponents.AbsoluteConstraints(180, 125, 50, 20));
		
		StartAM.setText("AM");
		buttonGroup1.add(StartAM);
		container1.add(StartAM, new design.awtcomponents.AbsoluteConstraints(240, 125, 60, 20));
		
		StartPM.setText("PM");
		buttonGroup1.add(StartPM);
		StartPM.setSelected(true);
		
		container1.add(StartPM, new design.awtcomponents.AbsoluteConstraints(290, 125, 60, 20));
		
		DurationTickBox.setText("Duration");
		container1.add(DurationTickBox, new design.awtcomponents.AbsoluteConstraints(350, 125, 90, 20));
		
		EndHoursDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		container1.add(EndHoursDropDown, new design.awtcomponents.AbsoluteConstraints(130, 150, 50, 20));
		
		EndMinutesDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
		container1.add(EndMinutesDropDown, new design.awtcomponents.AbsoluteConstraints(180, 150, 50, 20));
		
		EndAM.setText("AM");
		buttonGroup2.add(EndAM);
		container1.add(EndAM, new design.awtcomponents.AbsoluteConstraints(240, 150, 60, 20));
		
		EndPM.setText("PM");
		buttonGroup2.add(EndPM);
		EndPM.setSelected(true);
		container1.add(EndPM, new design.awtcomponents.AbsoluteConstraints(290, 150, 60, 20));
		
		getContentPane().add(container1, java.awt.BorderLayout.SOUTH);
		pack();
	}

    //When the "Insert New Appointment" button is clicked on, it display a window to add appointment details.
	private void toggleInsertAppointmentMouseClicked(java.awt.event.MouseEvent evt) {
		if(confirmTextFields()) {
		Calendar when = new GregorianCalendar(Integer.parseInt(YearText.getText()), obtainValueOfMonth((String)MonthsDropDown.getSelectedItem()), Integer.parseInt(DayText.getText()));
		//If the appointment has been given a selected start and end time.
			if(DurationTickBox.isSelected()) {
				int StartHours = Integer.parseInt((String)StartHoursDropDown.getSelectedItem());
				int StartMinutes = Integer.parseInt((String)StartMinutesDropDown.getSelectedItem());
				int EndHours = Integer.parseInt((String)EndHoursDropDown.getSelectedItem());
				int EndMinutes = Integer.parseInt((String)EndMinutesDropDown.getSelectedItem());
				if(StartPM.isSelected()) {
					StartHours = StartHours + 12;
					if(StartHours == 24) {
						StartHours = 0;
					}
				}
				if(EndPM.isSelected()) {
					EndHours = EndHours + 12;
					if(EndHours == 24) {
						EndHours = 0;
					}
				}
				int duration = ((EndHours - StartHours) * 60) + (EndMinutes - StartMinutes);
				
				frame.makeAppointment(new Appointment(StartHours, StartMinutes, when.get(Calendar.DAY_OF_MONTH), when.get(Calendar.MONTH), when.get(Calendar.YEAR), duration,
					PatientNameText.getText(), HealthcarePlanText.getText(), PatientIDText.getText(), Appointment.NO_REMINDER, PartnerText.getText(), DescriptionText.getText())); 	
			}
			else { //If the appointment has not been given a start or stop time.
				frame.makeAppointment(new Appointment(when.get(Calendar.DAY_OF_MONTH), when.get(Calendar.MONTH),
				when.get(Calendar.YEAR), PatientNameText.getText(), 
				HealthcarePlanText.getText(), PatientIDText.getText(),
				PartnerText.getText(), DescriptionText.getText()));
			}
			if(EDIT) { //Remove the previous details of an appointment and replaces it with new ones.
				frame.deleteAppointment(appointment);
			}
			setVisible(false);
		}
		else { //If all the fields are not attended to.
			JOptionPane.showMessageDialog(this, "Some fields are either empty or the info entered is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//When the "Empty Fields" button is clicked on, it removes erases all information from the text fields.
	private void toggleEmptyFieldsMouseClicked(java.awt.event.MouseEvent action) {
		PatientNameText.setText("");
		HealthcarePlanText.setText("");
		PatientIDText.setText("");
		PartnerText.setText("");
		DescriptionText.setText("");
		DayText.setText("");
	}

	//When the "Exit Window" button is clicked on, it closes the appointment window.
	private void toggleExitMouseClicked(java.awt.event.MouseEvent action) {
		setVisible(false);
		dispose();
	}

	//When a month is chosen, it retrieves the value of that month.
	private void MonthsDropDownActionPerformed(java.awt.event.ActionEvent action) {
		JComboBox origin = (JComboBox)action.getSource();
		String nameOfMonth = (String)origin.getSelectedItem();
		month = obtainValueOfMonth(nameOfMonth);
	}

	//Closes the appointment window.
	private void closeDialog(java.awt.event.WindowEvent action) {
		setVisible(false);
		dispose();
	}
}