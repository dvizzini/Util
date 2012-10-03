package com.danielvizzini.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

public class DateUtilTest {
	
	GregorianCalendar now = new GregorianCalendar();
	String differentTimeZoneName = now.getTimeZone().getDisplayName().equals("Pacific Standard Time") ? "US/Pacific" : "US/Central";
	TimeZone differentTimeZone = TimeZone.getTimeZone(differentTimeZoneName);
	
	GregorianCalendar negativeEndDate = new GregorianCalendar(2010, 11, 25);
	GregorianCalendar startDate = new GregorianCalendar(2011, 0, 1);
	GregorianCalendar partialEndDate = new GregorianCalendar(2011, 0, 1, 12, 0);
	GregorianCalendar shortEndDate = new GregorianCalendar(2011, 0, 2);
	GregorianCalendar someTuesday = new GregorianCalendar(2011, 0, 4);
	GregorianCalendar longEndDate = new GregorianCalendar(2011, 3, 2);
	GregorianCalendar differentTimeZoneDate = new GregorianCalendar(differentTimeZone);
	
	ArrayList<GregorianCalendar> noDays = new ArrayList<GregorianCalendar>();
	ArrayList<GregorianCalendar> oneDay = new ArrayList<GregorianCalendar>(Arrays.asList(startDate));
	ArrayList<GregorianCalendar> scatteredDates = new ArrayList<GregorianCalendar>(Arrays.asList(negativeEndDate, startDate, partialEndDate, shortEndDate, someTuesday, longEndDate));
	ArrayList<GregorianCalendar> differentTimeZoneDates = new ArrayList<GregorianCalendar>(Arrays.asList(negativeEndDate, startDate, partialEndDate, shortEndDate, someTuesday, longEndDate, differentTimeZoneDate));
	
	boolean[] tuesdayThursday = {false, false, true, false, true, false, false};
	boolean[] tooShort = {false, false, true, false, true, false};
	boolean[] tooLong = {false, false, true, false, true, false, false, false};
	
	@Test
	public void testGetEligibleDaysBetweenTwoDates() {
		assertEquals(DateUtil.getEligibleDaysBetweenTwoDates(startDate, negativeEndDate, tuesdayThursday), 2);
		assertEquals(DateUtil.getEligibleDaysBetweenTwoDates(startDate, startDate, tuesdayThursday), 0);		
		assertEquals(DateUtil.getEligibleDaysBetweenTwoDates(startDate, partialEndDate, tuesdayThursday), 0);
		assertEquals(DateUtil.getEligibleDaysBetweenTwoDates(startDate, shortEndDate, tuesdayThursday), 0);
		assertEquals(DateUtil.getEligibleDaysBetweenTwoDates(startDate, someTuesday, tuesdayThursday), 1);
		assertEquals(DateUtil.getEligibleDaysBetweenTwoDates(startDate, longEndDate, tuesdayThursday), 26);
	}

	@Test (expected = IllegalArgumentException.class) 
	public void testGetEligibleDaysBetweenTwoDatesTooShort() {
		DateUtil.getEligibleDaysBetweenTwoDates(startDate, negativeEndDate, tooShort);		
	}

	@Test (expected = IllegalArgumentException.class) 
	public void testGetEligibleDaysBetweenTwoDatesTooLong() {
		DateUtil.getEligibleDaysBetweenTwoDates(startDate, negativeEndDate, tooLong);		
	}

	@Test (expected = IllegalArgumentException.class) 
	public void testGetEligibleDaysBetweenTwoDatesDifferentTimeZone() {
		DateUtil.getEligibleDaysBetweenTwoDates(startDate, differentTimeZoneDate, tuesdayThursday);		
	}

	@Test
	public void testGetDaysBetweenTwoDates() {
		assertEquals(DateUtil.getDaysBetweenTwoDates(startDate, negativeEndDate), 8);
		assertEquals(DateUtil.getDaysBetweenTwoDates(startDate, startDate), 1);		
		assertEquals(DateUtil.getDaysBetweenTwoDates(startDate, partialEndDate), 1);		
		assertEquals(DateUtil.getDaysBetweenTwoDates(startDate, shortEndDate), 2);
		assertEquals(DateUtil.getDaysBetweenTwoDates(startDate, someTuesday), 4);
		assertEquals(DateUtil.getDaysBetweenTwoDates(startDate, longEndDate), 92);
	}

	@Test
	public void testGetWeekdaysBetweenTwoDates() {
		assertEquals(DateUtil.getWeekdaysBetweenTwoDates(startDate, negativeEndDate), 5);
		assertEquals(DateUtil.getWeekdaysBetweenTwoDates(startDate, startDate), 0);		
		assertEquals(DateUtil.getWeekdaysBetweenTwoDates(startDate, partialEndDate), 0);		
		assertEquals(DateUtil.getWeekdaysBetweenTwoDates(startDate, shortEndDate), 0);
		assertEquals(DateUtil.getWeekdaysBetweenTwoDates(startDate, someTuesday), 2);
		assertEquals(DateUtil.getWeekdaysBetweenTwoDates(startDate, longEndDate), 65);
	}

	@Test
	public void testGetWeekendDaysBetweenTwoDates() {
		assertEquals(DateUtil.getWeekendDaysBetweenTwoDates(startDate, negativeEndDate), 3);
		assertEquals(DateUtil.getWeekendDaysBetweenTwoDates(startDate, startDate), 1);		
		assertEquals(DateUtil.getWeekendDaysBetweenTwoDates(startDate, partialEndDate), 1);		
		assertEquals(DateUtil.getWeekendDaysBetweenTwoDates(startDate, shortEndDate), 2);
		assertEquals(DateUtil.getWeekendDaysBetweenTwoDates(startDate, someTuesday), 2);
		assertEquals(DateUtil.getWeekendDaysBetweenTwoDates(startDate, longEndDate), 27);
	}

	@Test
	public void testGetEligibleDaysInArray() {
		assertEquals(DateUtil.getEligibleDaysInArray(noDays, tuesdayThursday), 0);
		assertEquals(DateUtil.getEligibleDaysInArray(oneDay, tuesdayThursday), 0);		
		assertEquals(DateUtil.getEligibleDaysInArray(scatteredDates, tuesdayThursday), 1);		
	}

	@Test (expected = IllegalArgumentException.class) 
	public void testGetEligibleDaysInArrayTooShort() {
		DateUtil.getEligibleDaysInArray(oneDay, tooShort);		
	}

	@Test (expected = IllegalArgumentException.class) 
	public void testGetEligibleDaysInArrayTooLong() {
		DateUtil.getEligibleDaysInArray(noDays, tooLong);		
	}

	@Test (expected = IllegalArgumentException.class) 
	public void testGetEligibleDaysInArrayDifferentTimeZone() {
		DateUtil.getEligibleDaysInArray(differentTimeZoneDates, tooLong);		
	}

	@Test
	public void testGetDaysInArray() {
		assertEquals(DateUtil.getDaysInArray(noDays), 0);
		assertEquals(DateUtil.getDaysInArray(oneDay), 1);		
		assertEquals(DateUtil.getDaysInArray(scatteredDates), 5);		
	}

	@Test
	public void testGetWeekdaysInArray() {
		assertEquals(DateUtil.getWeekdaysInArray(noDays), 0);
		assertEquals(DateUtil.getWeekdaysInArray(oneDay), 0);		
		assertEquals(DateUtil.getWeekdaysInArray(scatteredDates), 1);
	}

	@Test
	public void testGetWeekendDaysInArray() {
		assertEquals(DateUtil.getWeekendDaysInArray(noDays), 0);
		assertEquals(DateUtil.getWeekendDaysInArray(oneDay), 1);		
		assertEquals(DateUtil.getWeekendDaysInArray(scatteredDates), 4);		
	}
	
	@Test
	public void testIsValidDate() {

		assertTrue(DateUtil.isValidDate("543/1/1"));
		assertTrue(DateUtil.isValidDate("1543/01/01"));
		assertTrue(DateUtil.isValidDate("2000/02/29"));
		assertTrue(DateUtil.isValidDate("2000/12/29"));
		assertFalse(DateUtil.isValidDate("2000/13/29"));
		assertFalse(DateUtil.isValidDate(""));
		assertFalse(DateUtil.isValidDate("//"));
		assertFalse(DateUtil.isValidDate("543//1"));
		assertFalse(DateUtil.isValidDate("1999/2/29"));
	}

	@Test
	public void testMinutesSinceMidnightToString() {
		assertEquals(DateUtil.minutesSinceMidnightToString(-10), "11:50PM");
		assertEquals(DateUtil.minutesSinceMidnightToString(0), "12:00AM");
		assertEquals(DateUtil.minutesSinceMidnightToString(75), "1:15AM");
		assertEquals(DateUtil.minutesSinceMidnightToString(720), "12:00PM");
		assertEquals(DateUtil.minutesSinceMidnightToString(795), "1:15PM");
		assertEquals(DateUtil.minutesSinceMidnightToString(1440), "12:00AM");
		assertEquals(DateUtil.minutesSinceMidnightToString(1515), "1:15AM");
	}
}