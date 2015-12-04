/**
*	CalendarUtil.java
**/

package com2002.assignment.calendar.data;

/**
*	A class containing utility methods that a calendar might
*	need to function
**/
public class CalendarUtil {
	
	/** integers specifying the number of days in each month **/
	public static int DaysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/** static var used for February **/
	public static final int FEBRUARY = 1;

	/** 
	*	Get number of days in specified year and month
	*	
	*	@param year the year in integer form
	*	@param month the month in integer form (0 is January, etc...)
	*	@return the number of days in the specified month and year
	**/
	public static int NumDaysInMonth(int year, int month) {
		
		int baseDays = DaysInMonth[month];
	
		if(IsLeapYear(year) && (month == FEBRUARY)) {
		baseDays = baseDays + 1;
		}
	
		return baseDays;
	}


	/**
	*	Find number of rows needed to display the month
	*	
	*	@param year the year in integer form
	*	@param month the month in integer form (0 is January, etc...)
	*	@return the number of rows needed to display specified month and year
	**/
	public static int NumRowsNeeded(int year, int month)
	{
		int firstDay;	// day of week of the first of the month 
		int numBlocks;	// number of "blocks" the month needs
		
		//modern calendar started in 1582    
		if (year < 1582) {
			return (-1);
		}
	
		//if the month is not 1 thru 12
		if ((month < 0) || (month > 11)) {
			return (-1);
		}
	
		firstDay = CalcFirstOfMonth(year, month);
		
		//a February which isn't a leap year with Sunday as first day needs 4 rows 
		if ((month == FEBRUARY) && (firstDay == 0) && !IsLeapYear(year)) {
			return (4);
		}
		
		numBlocks = firstDay + DaysInMonth[month];
		
		//we need an extra block for the 29th in a leap year February
		if ((month == FEBRUARY) && (IsLeapYear(year))) {
			numBlocks++;
		}
		//all other cases will need 5 or 6 rows 
		if(numBlocks <= 35) {
			return 5;
		}
		else{
			return 6;
		}
	}

	/**
		Find first day of the month
		
		@param year the year in integer form
		@param month the month in integer form (0 is January, etc...)
		@return number of the day of the week (1 is Monday, etc...)
	**/
	public static int CalcFirstOfMonth(int year, int month) {
		
		int firstDay;
	
		//modern calendar started in 1582
		if (year < 1582) {
			return (-1);
		}
	
	//if the month is not 1 thru 12
		if ((month < 0) || (month > 11)) {
			return (-1);
		}
	
		firstDay = CalcJanuaryFirst(year);
	
	
		//loop to find first day of month
		for (int i = 0; i < month; i++)
		{
			firstDay += DaysInMonth[i];
		}
	
	//go to next day if it's a leap year and month is after February
		if ((month > FEBRUARY) && IsLeapYear(year)) {
			firstDay++;
		}
	
		return (firstDay % 7);
	}

	/**
	*	Determines if the specified year is a Leap Year
	*	
	*	@param year the year
	*	@return true if the year is a Leap Year, false otherwise
	**/
	public static boolean IsLeapYear(int year) {
	//if the year is a multiple of 100, the year is a leap year if its also a multiple of 400
		if ((year % 100) == 0) {
			
			if((year % 400) == 0) {
				return true;
			}
			else{
				return false;
			}
		}
	
		//year is also leap year if multiple of 4
		if ((year % 4) == 0) {
			return true;
		}
		else{
			return false;
		}
	}

	
	/**
	*	Determines the day of the week January 1st of the given year occurs on
	*	
	*	@param year the year
	*	@return number of the day of the week (1 is Monday, etc...)
	*	
	**/
	public static int CalcJanuaryFirst(int year) {
		
		int numLeapYears, numYearsSince1582;
		
		//modern calendar started in 1582
		if (year < 1582){
			return (-1);
		}
	
		//1-1-1582 was a friday, so 5 days + a day for every year since 1582.
		//add an extra day leap years because January 1st moved two days those years  
		numLeapYears = CalcLeapYears(year);
		numYearsSince1582 = year - 1582;
		return ( ( 5 + numYearsSince1582 + numLeapYears ) % 7 ); 
	}

	
	/**
	*	Determines number of leap years since 1582 (not including current year)
	*	
	*	@param year the year
	*	@return number of leap years since 1582 (not including current year
	**/
	public static int CalcLeapYears(int year) {
		int numLeapYears, multipleHundred ,multipleFourHundred;   
		
		//modern calendar started in 1582
		if (year < 1582) {
			return (-1);
		}

		//# of years that are multiple of 4
		numLeapYears = (year - 1581) / 4;
		
		//# years that are multiple of a hundred
		multipleHundred = (year - 1501) / 100;
		
		//those years aren't leap years
		numLeapYears = numLeapYears - multipleHundred;
		
		
		//# years that are multiple of four hundred
		multipleFourHundred = (year - 1201) / 400;
		
		//those years are leap years
		numLeapYears = numLeapYears + multipleFourHundred;
		
		return numLeapYears;
	}
};