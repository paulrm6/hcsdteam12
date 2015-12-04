package com2002.assignment.calendar.data;

import org.w3c.dom.*;

public class Appointment {
    
    /** Used to represent time isn't set */
    private static final int NO_TIME = -1;
    
    /** Used to represent that there is no reminder */
    public static final int NO_REMINDER = -1;
    
    /** Used for setting appointments to recur weekly */
    public static final int RECUR_BY_WEEKLY = 1;
    
    /** Used for setting appointments to recur monthly */
    public static final int RECUR_BY_MONTHLY = 2;
    
    /** Used for setting appointments to recur yearly */
    public static final int RECUR_BY_YEARLY = 3;
    
    /** Used for setting appointments to recur forever */
    public static final int RECUR_NUMBER_FOREVER = 1000;
    
    /** Used for setting appointments to never recur */
    public static final int RECUR_NUMBER_NEVER = 0;
    
    /** Used for knowing whether or not an appointment is valid or not */
    private boolean valid;
   
    /** The title or caption of the appointment */
    private String title;
    
    /** The description of the appointment */
    private String description;
    
    /** The location of the appointment */
    private String location;    
    /** The starting minute of the appointment */
    private int startMinute;
    
    /** The starting hour of the appointment */
    private int startHour;
    
    /** The starting day of the appointment */
    private int startDay;
    
    /** The starting month of the appointment */
    private int startMonth;
    
    /** The starting year of the appointment */
    private int startYear;
    
    /** The duration of the appointment (minutes) */
    private int duration;
    
    /** Minutes before the appointment starts to set an alarm */
    private int alarmTime;
    
    /** E-mail address associated with the appointment */
    private String emailAddress;
    
    /** Web page address associated with the appointment */
    private String webAddress;
    
    /** Element location of the appointment in XML tree */
    private Element xmlElement;
    
    /** Day(s) of the week that the appointment recurs on */
    private int[] recurDays;
    
    /** What the appointment recurs on (weeks/months/years) */
    private int recurBy;
    
    /** How often the appointment recurs on (every ? weeks/months/years) */
    private int recurIncrement;
    
    /** How many recurrences (-1 for infinite, 0 by default) */
    private int recurNumber;
    
    /**
     * Default constructor
     * Constructs an invalid appointment
     */
    public Appointment() {
        setTitle(null);
        setDescription(null);
        setLocation(null);
        setEmailAddress(null);
        setWebAddress(null);
        setXmlElement(null);
        valid = false;
    }

    /**
     * Constructs a new appointment that starts at a specific time on the 
     * date specified. The appointment is constructed with no recurrence 
     * information by default. To set recurrence information, construct the
     * appointment and then call setRecurrence(...) method. The XmlElement 
     * will be set when the appointment is saved to disk.
     * @param startHour The hour that the appointment starts on. The hours are
     *      numbered 0-23 to represent 12a.m. to 11pm on the day specified.
     * @param startMinute The minute of the hour the appointment starts on.
     * @param startDay The day of the month the appointment starts on.
     * @param startMonth The month of the year the appointment starts on. Use
     *  the constants provided by Gregorian Calendar to set the month. 
     * @param startYear The year the appointment starts on.
     * @param duration The duration of the appointment (in minutes)
     * @param title The title or caption to give the appointment
     * @param description The appointment's details
     * @param location Where the appointment occurs
     * @param alarmTime Number of minutes before the start time to show reminder
     * @param emailAddress An e-mail address associated with the appointment
     * @param webAddress A web address associated with the appointment
     */
    public Appointment(int startHour, int startMinute, int startDay, int startMonth, int startYear, int duration, String title, 
    		String description, String location, int alarmTime, String emailAddress, String webAddress) {

        //Sets all instance variables except recurring information
    	setTitle(title);
        setDescription(description);
        setLocation(location);
    	setStartMinute(startMinute);
    	setStartHour(startHour);
        setStartDay(startDay);
        setStartMonth(startMonth);
        setStartYear(startYear);
        setDuration(duration);
        setAlarmTime(alarmTime);
        setEmailAddress(emailAddress);
        
        //Set default recurring information
        int[] recurringDays = new int[0];
        setRecurrence(recurringDays, RECUR_BY_MONTHLY, 0, RECUR_NUMBER_NEVER);
        
        //Leave XML Element null
        setXmlElement(null);
        
        //Sets valid to true - this is now a valid appointment
        valid = true;
    }
    
    /**
     * Constructs a new appointment that has no start time on the 
     * date specified. The appointment is constructed with no recurrence 
     * information by default. To set recurrence information, construct the
     * appointment and then call setRecurrence(...) method. The XmlElement 
     * will be set when the appointment is saved to disk.
     * @param startDay The day of the month the appointment starts on
     * @param startMonth The month of the year the appointment starts on. Use
     *  the constants provided by Gregorian Calendar to set the month. 
     * @param startYear The year the appointment starts on.
     * @param title The title or caption to give the appointment
     * @param description The appointment's details
     * @param location Where the appointment occurs
     * @param emailAddress An e-mail address associated with the appointment
     * @param webAddress A web address associated with the appointment
     */
    public Appointment(int startDay, int startMonth, int startYear, String title, String description, String location, String emailAddress, 
    		String webAddress) {
                    
         //Just call the other constructor
         this(NO_TIME, NO_TIME, startDay, startMonth, startYear, NO_TIME, title, 
            description, location, NO_TIME, emailAddress, webAddress);
    }
  
    /**
     * Sets the recurring information with the correct information
     */
    public void setRecurrence(int[] recurDays, int recurBy, int recurIncrement, int recurNumber) {
        setRecurDays(recurDays);
        setRecurBy(recurBy);
        setRecurIncrement(recurIncrement);
        setRecurNumber(recurNumber);
    }
    
    /**
     * Checks to see if an appointment occurs on a certain day, month, year.
     * Takes recurrence into account.
     * @return True if the appointment occurs on a certain day/month/year
     * @deprecated
     */
    public boolean isOn(int day, int month, int year) {
        return (day == getStartDay() && month == getStartMonth() 
                && year == getStartYear());
    }
    
    /**
     * Checks to see if an appointment recurs or not
     * @return True if the appointment does occur more than once
     */
    public boolean isRecurring() {
        return getRecurNumber() != RECUR_NUMBER_NEVER;
    }
    
    /**
     * Checks to see if a time is set for this appointment.
     * @return True if this appointment has a time set. Otherwise false.
     */
    public boolean hasTimeSet() {
        return (getStartHour() != NO_TIME);
    }
    
    /**
     * @return True if the appointment is valid
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * Sets the XML Element for this appointment
     */
    public void setXmlElement(Element xmlElement) {
        this.xmlElement = xmlElement;
    }
    
    /** Sets startMinute */
    private void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }
    
    /** Sets startHour */
    private void setStartHour(int startHour) {
        this.startHour = startHour;
    }
    
    /** Sets startDay */
    private void setStartDay(int startDay) {
        this.startDay = startDay;
    }
    
    /** Sets startMonth */
    private void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }
    
    /** Sets startYear */
    private void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    
    /** Sets duration */
    private void setDuration(int duration) {
        this.duration = duration;
    }
    
    /** Sets title */
    private void setTitle(String title) {
        if (title == null) 
            this.title = "";
        else
            this.title = title;
    }
    
    /** Sets description */
    private void setDescription(String description) {
        if (description == null)
            this.description = "";
        else
            this.description = description;
    }
    
    /** Sets location */
    private void setLocation(String location) {
        if (location == null) 
            this.location = "";
        else
            this.location = location;
    }
    
    /** Sets alarmTime */
    private void setAlarmTime(int alarmTime) {
        this.alarmTime = alarmTime;
    }
    
     /** Sets emailAddress */
    private void setEmailAddress(String emailAddress) {
        if (emailAddress == null)
            this.emailAddress = "";
        else
            this.emailAddress = emailAddress;
    }
    
    /** Sets webAddress */
    private void setWebAddress(String webAddress) {
        if (webAddress == null)
            this.webAddress = "";
        else
            this.webAddress = webAddress;
    }

    /** Sets recurDays */
    private void setRecurDays(int[] recurDays) {
        if (recurDays == null) {
            this.recurDays = new int[0];
        }
        else {
            this.recurDays = recurDays;
        }
    }
    
    /** Sets recurBy */
    private void setRecurBy(int recurBy) {
        this.recurBy = recurBy;
    }
    
     /** Sets recurIncrement */
    private void setRecurIncrement(int recurIncrement) {
        this.recurIncrement = recurIncrement;
    }
    
    /** Sets recurNumber */
    private void setRecurNumber(int recurNumber) {
        this.recurNumber = recurNumber;
    }
    
    /** Gets startHour */
    public int getStartHour() {
        return startHour;
    }
    
    /** Gets startHour */
    public int getStartMinute() {
        return startMinute;
    }
    
    /** Gets startDay */
    public int getStartDay() {
        return startDay;
    }
    
    /** Gets startMonth */
    public int getStartMonth() {
        return startMonth;
    }
    
    /** Gets startYear */
    public int getStartYear() {
        return startYear;
    }
    
    /** Gets duration */
    public int getDuration() {
        return duration;
    }
    
    /** Gets title */
    public String getTitle() {
        return title;
    }
    
    /** Gets description */
    public String getDescription() {
        return description;
    }
    
    /** Gets location */
    public String getLocation() {
        return location;
    }
    
    /** Gets alarmTime */
    public int getAlarmTime() {
        return alarmTime;
    }

    /** Gets recurDays */
    public int[] getRecurDays() {
        return recurDays;
    }
    
    /** Gets recurBy */
    public int getRecurBy() {
        return recurBy;
    }
    
    /** Gets recurIncrement */
    public int getRecurIncrement() {
        return recurIncrement;
    }
    
    /** Gets recurNumber */
    public int getRecurNumber() {
        return recurNumber;
    }
    
    /** Gets xmlElement */
    public Element getXmlElement() {
        return xmlElement;
    }

	/** Returns a simple string representing the appointment */
    public String toString() {
		if (!isValid()) {
		    return null;
		}
		else {
			if (hasTimeSet()) {
				return getStartHour() + ":" + getStartMinute() + " " + getTitle();
			}
			else {
				return getTitle();
			}
		}
	}
}
