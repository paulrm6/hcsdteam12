package hcsdteam12;

import hcsdteam12.calendar.*;
import sun.applet.Main;

/**
 * Authors: Adam and Paul
 */
public class DentalPractice {

    public static void main (String args[]) {
        /**
         * Start by displaying a main screen with the different options that are needed
         * Options:
         * Secretary -
         * Register Patient
         * Update patient info (contained within is health care plan stuff)
         * Register appointment
         * Find and/or cancel appointment (display on calendar)
         * Record when paid
         * Book holiday (empty appointment)
         * Review unpaid treatments given to a patient as a list listing costs and total cost (auto uses hleathcare plan)
         * Partner -
         * View appointments for week
         * Record having seen patient, put in treatments given and cost
         */
        System.out.println("Test");
        MainCalendar.display();

    }
}
