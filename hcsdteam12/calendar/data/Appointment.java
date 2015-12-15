package hcsdteam12.calendar.data;

/**
 * Appointment.java is used to retrieve and store data/information obtained from AppointmentForm.java
 *
 * @author Seng Kin(Terence), Kong
 **/

import org.w3c.dom.Element;

public class Appointment {
    public static final int NO_REMINDER = -1;
    public static final int WEEKLY_REOCCURENCE = 1, MONTHLY_REOCCURENCE = 2, YEARLY_REOCCURENCE = 3, INFINITE_REOCCURENCE = 1000, NO_REOCCURENCE = 0, TIME_NOT_SET = -1;
    private boolean legitimate;

    private int startHour, startMinute, startDay, startMonth, startYear, duration, alert;
    private String name, plan, unique, partner, description;
    private Element xmlItem;

    //Private instances that handles reoccurencesDay(s) of the week that the appointment recurs on
    private int[] reoccurWhichDay; //On what day
    private int reoccurOn; //At what intervals? Weeks? Months?
    private int reoccurRate, numberOfReoccurences; //Specifies the frequency of the appointment reoccurences and how many

    //Generates an invalid Appointment object
    public Appointment() {
        establishPatientName(null);
        establishHealthcarePlan(null);
        establishPatientID(null);
        retrievePartner(null);
        retrieveDescription(null);
        establishXML(null);
        legitimate = false;
    }

    //Creates a new Appointment object that has a start time on the specific date. The reoccurence settings is not set by default.
    //To create a reoccurence, call upon setReccurence() method.
    public Appointment(int startHour, int startMinute, int startDay, int startMonth, int startYear, int duration, String name, String plan, String unique, int alert, String partner, String description) {

        //Sets all instance variables except recurring information
        establishStartHour(startHour);
        establishStartMinute(startMinute);
        establishStartDay(startDay);
        establishStartMonth(startMonth);
        establishStartYear(startYear);
        establishDuration(duration);
        establishPatientName(name);
        establishPatientName(plan);
        establishPatientID(unique);
        establishAlertTime(alert);
        retrievePartner(partner);
        retrieveDescription(description);

        //When appointment object is brought up, the reoccurence settings are not configured.
        int[] recurringDays = new int[0];
        establishRecurrence(recurringDays, MONTHLY_REOCCURENCE, 0, NO_REOCCURENCE);
        establishXML(null);
        legitimate = true; //Once all the info of an appointment is compiled, it is then approved as an legitimate appointment
    }

    public Appointment(int startDay, int startMonth, int startYear, String name, String plan, String unique, String partner, String description) {
        this(TIME_NOT_SET, TIME_NOT_SET, startDay, startMonth, startYear, TIME_NOT_SET, name, plan, unique, TIME_NOT_SET, partner, description);
    }

    //Configure the reoccurence settings with the information given.
    public void establishRecurrence(int[] reoccurWhichDay, int reoccurOn, int reoccurRate, int numberOfReoccurences) {
        establishReoccurWhichDay(reoccurWhichDay);
        establishreoccurOn(reoccurOn);
        establishReoccurRate(reoccurRate);
        establishNumberOfReoccurences(numberOfReoccurences);
    }

    public boolean isAppointmentScheduled(int day, int month, int year) {
        return (day == retrieveStartDay() && month == retrieveStartMonth() && year == retrieveStartYear());
    }

    public boolean isAppointmentReoccuring() {
        return getNumberOfReoccurences() != NO_REOCCURENCE;
    }

    public boolean hasStartTime() {
        return (retrieveStartHour() != TIME_NOT_SET);
    }

    public boolean isAppointmentLegitimate() {
        return legitimate;
    }

    public void establishXML(Element xmlRecord) {
        this.xmlItem = xmlRecord;
    }

    private void establishStartHour(int startHour) {
        this.startHour = startHour;
    }

    private void establishStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    private void establishStartDay(int startDay) {
        this.startDay = startDay;
    }

    private void establishStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    private void establishStartYear(int startYear) {
        this.startYear = startYear;
    }

    private void establishDuration(int duration) {
        this.duration = duration;
    }

    private void establishPatientName(String name) {
        if (name == null)
            this.name = "";
        else
            this.name = name;
    }

    private void establishHealthcarePlan(String plan) {
        if (plan == null)
            this.plan = "";
        else
            this.plan = plan;
    }

    private void establishPatientID(String unique) {
        if (unique == null)
            this.unique = "";
        else
            this.unique = unique;
    }

    private void establishAlertTime(int alert) {
        this.alert = alert;
    }

    private void retrievePartner(String partner) {
        if (partner == null)
            this.partner = "";
        else
            this.partner = partner;
    }

    private void retrieveDescription(String description) {
        if (description == null)
            this.description = "";
        else
            this.description = description;
    }

    private void establishReoccurWhichDay(int[] reoccurWhichDay) {
        if (reoccurWhichDay == null) {
            this.reoccurWhichDay = new int[0];
        } else {
            this.reoccurWhichDay = reoccurWhichDay;
        }
    }

    private void establishreoccurOn(int reoccurOn) {
        this.reoccurOn = reoccurOn;
    }

    private void establishReoccurRate(int reoccurRate) {
        this.reoccurRate = reoccurRate;
    }

    private void establishNumberOfReoccurences(int numberOfReoccurences) {
        this.numberOfReoccurences = numberOfReoccurences;
    }

    public int retrieveStartHour() {
        return startHour;
    }

    public int retrieveStartMinute() {
        return startMinute;
    }

    public int retrieveStartDay() {
        return startDay;
    }

    public int retrieveStartMonth() {
        return startMonth;
    }

    public int retrieveStartYear() {
        return startYear;
    }

    public int retrieveDuration() {
        return duration;
    }

    public String retrievePatientName() {
        return name;
    }

    public String retrieveHealthcarePlan() {
        return plan;
    }

    public String retrievePatientID() {
        return unique;
    }

    public int retrieveAlertTime() {
        return alert;
    }

    public String retrievePartner() {
        return partner;
    }

    public String retrieveDescription() {
        return description;
    }

    public int[] getReoccurWhichDay() {
        return reoccurWhichDay;
    }

    public int getRetrieveReoccurOn() {
        return reoccurOn;
    }

    public int getReoccurRate() {
        return reoccurRate;
    }

    public int getNumberOfReoccurences() {
        return numberOfReoccurences;
    }

    //Outputs a simple string message to represent appointment details.
    public String toString() {
        if (!isAppointmentLegitimate()) {
            return null;
        } else {
            if (hasStartTime()) {
                return retrieveStartHour() + ":" + retrieveStartMinute() + " " + retrievePatientName();
            } else {
                return retrievePatientName();
            }
        }
    }
}