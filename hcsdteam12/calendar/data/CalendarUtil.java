package hcsdteam12.calendar.data;

/**
 * CalendarUtil.java is a class that houses all the utility methods and functions needed that the calender need.
 *
 * @author Seng Kin (Terence), Kong
 */

public class CalendarUtil {
    public static final int FEBRUARY = 1;
    public static int TotalDaysInEachMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //Return the number of days for a given month and year.
    public static int RowsInMonth(int year, int month) {
        int baseDays = TotalDaysInEachMonth[month];
        if (IsLeapYear(year) && (month == FEBRUARY)) {
            baseDays = baseDays + 1;
        }
        return baseDays;
    }

    //Determine the number of rows needed to display a month in a given year.
    public static int NumRowsNeeded(int year, int month) {
        int DayofFirstDayOfMonth;
        int numberOfBoxes; //Number of boxes a month needs.
        if (year < 1582) {
            return (-1);
        }
        if ((month < 0) || (month > 11)) {
            return (-1);
        }
        DayofFirstDayOfMonth = DayOneOfMonth(year, month);

        //Given that February has 28 days in a non-leap year, it needs only exactly 4 rows.
        if ((month == FEBRUARY) && (DayofFirstDayOfMonth == 0) && !IsLeapYear(year)) {
            return (4);
        }
        numberOfBoxes = DayofFirstDayOfMonth + TotalDaysInEachMonth[month];

        //Add another box to compensate for the additional day that is 29th February in a leap year.
        if ((month == FEBRUARY) && (IsLeapYear(year))) {
            numberOfBoxes++;
        }
        //In non-leap years, only 5 or 6 rows are required.
        if (numberOfBoxes <= 35) {
            return 5;
        } else {
            return 6;
        }
    }

    //Spots the first day of a given month of a year.
    public static int DayOneOfMonth(int year, int month) {
        int dayOnDayOne;
        if (year < 1582) {
            return (-1);
        }
        if ((month < 0) || (month > 11)) {
            return (-1);
        }

        dayOnDayOne = DayOnJanuaryFirst(year);

        //Loop function to figure out the first day of a month
        for (int i = 0; i < month; i++) {
            dayOnDayOne += TotalDaysInEachMonth[i];
        }

        //To allow February to proceed to the first day of March in a leap year.
        if ((month > FEBRUARY) && IsLeapYear(year)) {
            dayOnDayOne++;
        }
        return (dayOnDayOne % 7);
    }

    // Identifies whether or not a given year is a leap year.
    public static boolean IsLeapYear(int year) {
        if ((year % 100) == 0) { //Leap year is a multiple of 100, given 100 is a multiple of 4
            if ((year % 400) == 0) { //Leap year is a multiple of 400, given 400 is a multiple of 4
                return true;
            } else {
                return false;
            }
        }

        //Since leap year occurs every 4 years, it can be treated as a multiple of 4.
        if ((year % 4) == 0) {
            return true;
        } else {
            return false;
        }
    }

    //Finds out on which day of the week 1st of January falls on in a specific year.
    public static int DayOnJanuaryFirst(int year) {
        int numberOfLeapYears, leapYearsFrom1582;
        if (year < 1582) {
            return (-1);
        }
        numberOfLeapYears = FindNumberOfLeapYears(year);
        leapYearsFrom1582 = year - 1582;
        return ((5 + leapYearsFrom1582 + numberOfLeapYears) % 7);
    }

    //Find out how many leap years has occurred since 1582.
    public static int FindNumberOfLeapYears(int year) {
        int numberOfLeapYears, multipleHundred, multipleFourHundred;
        if (year < 1582) {
            return (-1);
        }
        numberOfLeapYears = (year - 1581) / 4;
        multipleHundred = (year - 1501) / 100;
        numberOfLeapYears = numberOfLeapYears - multipleHundred; //Non-leap years
        multipleFourHundred = (year - 1201) / 400;
        numberOfLeapYears = numberOfLeapYears + multipleFourHundred; //Leap years
        return numberOfLeapYears;
    }
};