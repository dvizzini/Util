package com.danielvizzini.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.TimeZone;

/**
 * Kitchen sink of static methods used for date and times
 */
public class DateUtil {
	
	//hide default constructor
	private DateUtil() {}
	
	private static boolean[] everyday = {true, true, true, true, true, true, true};
	private static boolean[] workweek = {false, true, true, true, true, true, false};
	private static boolean[] weekend = {true, false, false, false, false, false, true};
	
	/**
	 * Calculates the number of eligible days between two dates, inclusively. If the dateAtOneEnd and dateAtOtherEnd are identical, the function will return 1 if this date is eligible and 0 if it is not.
	 * <p>
	 * Iterates over days, so use subtraction if you are concerned about performance for very long intervals.
	 * @param dateAtOneEnd date at one end of interval
	 * @param dateAtOtherEnd date at other end of interval, must be in same time zone as dateAtOneEnd
	 * @param dayEligibility boolean array of length 7, with each day corresponding to a day of the week, where true indicates the day is eligible. Index 0 is Sunday.
	 * @return the number of eligible days between dateAtOneEnd and dateAtOtherEnd, inclusively. Note that is dateAtOneEnd equals dateAtOtherEnd, 1 will be returned if the date is eligible and 0 if it is not.
	 * @throws IllegalArgumentException if dates are in multiple time zones or dayEligibility boolean not of length seven or entirely false
	 */
	public static int getEligibleDaysBetweenTwoDates(GregorianCalendar dateAtOneEnd, GregorianCalendar dateAtOtherEnd, boolean[] dayEligibility) throws IllegalArgumentException {
		
		checkDayEligibility(dayEligibility);
		
		//make sure all days are in same time zone
		TimeZone timeZone = dateAtOneEnd.getTimeZone();
		if (!dateAtOtherEnd.getTimeZone().equals(timeZone)) {
			throw new IllegalArgumentException("Both dates must be in the same time zone.");
		}
		
		//put earlier day first			    
	    if (dateAtOneEnd.getTimeInMillis() > dateAtOtherEnd.getTimeInMillis()) {
	        GregorianCalendar tempDate = (GregorianCalendar) dateAtOneEnd.clone();
	    	dateAtOneEnd = (GregorianCalendar) dateAtOtherEnd.clone();
	        dateAtOtherEnd = (GregorianCalendar) tempDate.clone();
	    }

	    int eligibleDays = 0;
	    GregorianCalendar dateBetween = (GregorianCalendar) dateAtOneEnd.clone();
	    
		while (dateBetween.getTimeInMillis() <= dateAtOtherEnd.getTimeInMillis()) {
	    	if (dayEligibility[dateBetween.get(Calendar.DAY_OF_WEEK) - 1]) {
	    		eligibleDays++;
	    	}
            dateBetween.add(Calendar.DAY_OF_MONTH, 1);
		}

		return eligibleDays;
	}
	
	/**
	 * Calculates the number of days between two dates, inclusively. If the dateAtOneEnd and dateAtOtherEnd are identical, the function will return 1 if this date is eligible and 0 if it is not.
	 * <p>
	 * Iterates over days, so use subtraction if you are concerned about performance for very long intervals
	 * @param dateAtOneEnd date at one end of interval
	 * @param dateAtOtherEnd date at other end of interval, must be in same time zone as dateAtOneEnd
	 * @throws IllegalArgumentException if dates are in multiple time zones
	 * @return the number of eligible days between dateAtOneEnd and dateAtOtherEnd, inclusively. Note that is dateAtOneEnd equals dateAtOtherEnd, 1 will be returned if the date is eligible and 0 if it is not.
	 */
	public static int getDaysBetweenTwoDates(GregorianCalendar dateAtOneEnd, GregorianCalendar dateAtOtherEnd) throws IllegalArgumentException {
		return getEligibleDaysBetweenTwoDates(dateAtOneEnd, dateAtOtherEnd, everyday);
	}
	
	/**
	 * Calculates the number of weekdays between two dates, inclusively. If the dateAtOneEnd and dateAtOtherEnd are identical, the function will return 1 if this date is eligible and 0 if it is not.
	 * <p>
	 * Iterates over days, so use subtraction if you are concerned about performance for very long intervals
	 * @param dateAtOneEnd date at one end of interval
	 * @param dateAtOtherEnd date at other end of interval, must be in same time zone as dateAtOneEnd
	 * @throws IllegalArgumentException if dates are in multiple time zones
	 * @return the number of eligible days between dateAtOneEnd and dateAtOtherEnd, inclusively. Note that is dateAtOneEnd equals dateAtOtherEnd, 1 will be returned if the date is eligible and 0 if it is not.
	 */
	public static int getWeekdaysBetweenTwoDates(GregorianCalendar dateAtOneEnd, GregorianCalendar dateAtOtherEnd) throws IllegalArgumentException {
		return getEligibleDaysBetweenTwoDates(dateAtOneEnd, dateAtOtherEnd, workweek);
	}
	
	/**
	 * Calculates the number of weekend days between two dates, inclusively. If the dateAtOneEnd and dateAtOtherEnd are identical, the function will return 1 if this date is eligible and 0 if it is not.
	 * <p>
	 * Iterates over days, so use subtraction if you are concerned about performance for very long intervals
	 * @param dateAtOneEnd date at one end of interval
	 * @param dateAtOtherEnd date at other end of interval, must be in same time zone as dateAtOneEnd
	 * @throws IllegalArgumentException if dates are in multiple time zones
	 * @return the number of eligible days between dateAtOneEnd and dateAtOtherEnd, inclusively. Note that is dateAtOneEnd equals dateAtOtherEnd, 1 will be returned if the date is eligible and 0 if it is not.
	 */
	public static int getWeekendDaysBetweenTwoDates(GregorianCalendar dateAtOneEnd, GregorianCalendar dateAtOtherEnd) throws IllegalArgumentException {
		return getEligibleDaysBetweenTwoDates(dateAtOneEnd, dateAtOtherEnd, weekend);
	}
	
	/**
	 * Calculates the number of eligible days in an array of GregorianCalendar objects. Note that duplicates will only be counted once, as will two objects that fall in the same date.
	 * <p>
	 * Note that all dates must be in the same time zone, or an exception will be thrown.
	 * @param dates array of days to determine if eligible
	 * @param dayEligibility boolean array of length 7, with each day corresponding to a day of the week, where true indicates the day is eligible. Index 0 is Sunday.
	 * @throws IllegalArgumentException if dates are in multiple time zones or dayEligibility boolean not of length seven or entirely false
	 * @return the number of eligible days in an array of GregorianCalendar objects
	 */
	public static int getEligibleDaysInArray(Iterable<GregorianCalendar> dates, boolean[] dayEligibility) throws IllegalArgumentException {
		
		checkDayEligibility(dayEligibility);
		
		//return 0 if dates array is empty
		if (!dates.iterator().hasNext()) {
			return 0;
		}
		
		//make sure all days are in same time zone
		TimeZone timeZone = dates.iterator().next().getTimeZone();
		for (GregorianCalendar date : dates) {
			if (!date.getTimeZone().equals(timeZone)) {
				throw new IllegalArgumentException("All dates must be in the same time zone.");
			}
		}
		
	    int eligibleDays = 0;
	    
	    //hash days so that duplicates (or two instances at different times of the same day) are removed
	    HashSet<GregorianCalendar> datesHash = new HashSet<GregorianCalendar>();
	    for (GregorianCalendar date : dates) {
	    	datesHash.add(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH),date.get(Calendar.DATE)));
	    }
	    
	    for (GregorianCalendar date : datesHash) {
	    	if (dayEligibility[date.get(Calendar.DAY_OF_WEEK) - 1]) {
	    		eligibleDays++;
	    	}
	    }

	    return eligibleDays;
	    
	}
	
	/**
	 * Calculates the number of days in an array of GregorianCalendar objects. Note that duplicates will only be counted once, as will two objects in the same date.
	 * <p>
	 * This will be used instead of just finding the array length to eliminate counting elements in the same day more than once.
	 * <p>
	 * Note that all dates must be in the same time zone, or an exception will be thrown.
	 * @param dates array of days to determine if eligible
	 * @throws IllegalArgumentException if dates are in multiple time zones
	 * @return the number of eligible days in an array of GregorianCalendar objects
	 */
	public static int getDaysInArray(Iterable<GregorianCalendar> dates) throws IllegalArgumentException {
		return getEligibleDaysInArray(dates, everyday);
	}

	/**
	 * Calculates the number of weekdays in an array of GregorianCalendar objects. Note that duplicates will only be counted once, as will two objects in the same date.
	 * <p>
	 * Note that all dates must be in the same time zone, or an exception will be thrown.
	 * @param dates array of days to determine if eligible
	 * @throws IllegalArgumentException if dates are in multiple time zones
	 * @return the number of eligible days in an array of GregorianCalendar objects
	 */
	public static int getWeekdaysInArray(Iterable<GregorianCalendar> dates) throws IllegalArgumentException {
		return getEligibleDaysInArray(dates, workweek);
	}

	/**
	 * Calculates the number of weekdays in an array of GregorianCalendar objects. Note that duplicates will only be counted once, as will two objects in the same date.
	 * <p>
	 * Note that all dates must be in the same time zone, or an exception will be thrown.
	 * @param dates array of days to determine if eligible
	 * @throws IllegalArgumentException if dates are in multiple time zones
	 * @return the number of eligible days in an array of GregorianCalendar objects
	 */
	public static int getWeekendDaysInArray(Iterable<GregorianCalendar> dates) throws IllegalArgumentException {
		return getEligibleDaysInArray(dates, weekend);
	}

	/** ensures that dayEligibility argument is of write length and has a true element */
	private static void checkDayEligibility(boolean[] dayEligibility) {
		if (dayEligibility.length != 7) {
			throw new IllegalArgumentException("dayEligibility must be of length 7, with index 0 corresponding to Sunday");
		}
		
		boolean atLeastOneDayIsEligible = false;
		for (boolean day : dayEligibility) {
			atLeastOneDayIsEligible |= day;
		}
		
		if (!atLeastOneDayIsEligible) {
			throw new IllegalArgumentException("At least one day must be eligible");
		}
	}
	
	/**
	 * Take date string in "yyyy/mm/dd" format and see if it is valid
	 * @param yyyySlashmmSlashdd date in "yyyy/mm/dd" format
	 * @return true if the date is valid, false otherwise
	 */
	public static boolean isValidDate(String yyyySlashmmSlashdd) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            format.setLenient(false);
            format.parse(yyyySlashmmSlashdd);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
	
	/**
	 * Take minutes since midnight and return string formatted #H:MM{AM,PM}
	 * @param minutesSinceMidnight number of minutes since midnight
	 * @return string formatted #H:MM{AM,PM}
	 */
	public static String minutesSinceMidnightToString(int minutesSinceMidnight) {
		//get minutes in range
		minutesSinceMidnight = MiscUtil.modPositive(minutesSinceMidnight, 1440);
		
		//calculate hours in 24-hour time
        int hours = minutesSinceMidnight / 60 % 24;

        //convert to 12-hour time
        String amPm = hours < 12 ? "AM" : "PM";
        hours = hours % 12;
        if (hours == 0) hours = hours + 12;
        
        //calculate minutes
        int minutes = minutesSinceMidnight % 60;
        String minutesString = (minutes < 10 ? "0" : "") + minutes;
        
        return hours + ":" + minutesString + amPm;
    }
}