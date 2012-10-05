	package com.danielvizzini.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class MiscUtilTest {

	private abstract class KeyValuePair {

		private String key;

		public String getKey() {
			return this.key;
		}

		protected void setKey(String key) {
			this.key = key;
		}

		private Object value;

		public Object getValue() {
			return this.value;
		}

		protected void setValue(Object value) {
			this.value = value;
		}

	}

	private class SpecialKeyValuePair extends KeyValuePair {

		@SuppressWarnings("unused")
		public SpecialKeyValuePair(ArrayList<String> whatever, Integer whateverInt)  {
			
			this.setKey("array");
			this.setValue("list");

		}

		@SuppressWarnings("unused")
		public <T> SpecialKeyValuePair(LinkedList<T> whatever, Integer whateverInt)  {
			
			this.setKey("linked");
			this.setValue("list");

		}

		@SuppressWarnings("unused")
		public SpecialKeyValuePair(ArrayDeque<?> whatever, Integer whateverInt)  {
			
			this.setKey("array");
			this.setValue("deque");

		}
	
		@SuppressWarnings("unused")
		public SpecialKeyValuePair(Iterable<?> whatever, Integer whateverInt)  {
			
			this.setKey("iterable");
			this.setValue("holler");

		}
	
		@SuppressWarnings("unused")
		public SpecialKeyValuePair(String whatever, Integer whateverInt)  {
			
			this.setKey("string");
			this.setValue("holler");

		}
	}

	@Test
	public void testFindConstructor() {
		try {
			KeyValuePair keyValuePair = MiscUtil.findConstructor(SpecialKeyValuePair.class, MiscUtilTest.class, ArrayList.class, Integer.class).newInstance(this, new ArrayList<String>(), 0);
			assertTrue(keyValuePair.getKey().equals("array"));
			assertTrue(keyValuePair.getValue().equals("list"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}

		try {
			KeyValuePair keyValuePair = MiscUtil.findConstructor(SpecialKeyValuePair.class, MiscUtilTest.class, LinkedList.class, Integer.class).newInstance(this, new LinkedList<String>(), 0);
			assertTrue(keyValuePair.getKey().equals("linked"));
			assertTrue(keyValuePair.getValue().equals("list"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}

		try {
			KeyValuePair keyValuePair = MiscUtil.findConstructor(SpecialKeyValuePair.class, MiscUtilTest.class, ArrayDeque.class, Integer.class).newInstance(this, new ArrayDeque<String>(), 0);
			assertTrue(keyValuePair.getKey().equals("array"));
			assertTrue(keyValuePair.getValue().equals("deque"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}

		try {
			KeyValuePair keyValuePair = MiscUtil.findConstructor(SpecialKeyValuePair.class, MiscUtilTest.class, Iterable.class, Integer.class).newInstance(this, new ArrayDeque<String>(), 0);
			assertTrue(keyValuePair.getKey().equals("iterable"));
			assertTrue(keyValuePair.getValue().equals("holler"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}

		try {
			KeyValuePair keyValuePair = MiscUtil.findConstructor(SpecialKeyValuePair.class, MiscUtilTest.class, String.class, Integer.class).newInstance(this, "whatever", 0);
			assertTrue(keyValuePair.getKey().equals("string"));
			assertTrue(keyValuePair.getValue().equals("holler"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
		
	}

	@Test
	public void testGetMD5() {
		try {
			assertTrue(MiscUtil.getMd5("What I got").equals(
					"571299b6fdd88df44bb5c0534910dfba"));
		} catch (NoSuchAlgorithmException e) {
			fail("Exception " + e.getMessage() + " thrown.");
		}
	}

	@Test
	public void testGetOrdinalFor() {
		assertTrue(MiscUtil.getOrdinalFor(0).equals("0th"));
		assertTrue(MiscUtil.getOrdinalFor(1).equals("1st"));
		assertTrue(MiscUtil.getOrdinalFor(2).equals("2nd"));
		assertTrue(MiscUtil.getOrdinalFor(3).equals("3rd"));
		assertTrue(MiscUtil.getOrdinalFor(4).equals("4th"));
		assertTrue(MiscUtil.getOrdinalFor(10).equals("10th"));
		assertTrue(MiscUtil.getOrdinalFor(11).equals("11th"));
		assertTrue(MiscUtil.getOrdinalFor(12).equals("12th"));
		assertTrue(MiscUtil.getOrdinalFor(13).equals("13th"));
		assertTrue(MiscUtil.getOrdinalFor(14).equals("14th"));
		assertTrue(MiscUtil.getOrdinalFor(-110).equals("-110th"));
		assertTrue(MiscUtil.getOrdinalFor(-111).equals("-111th"));
		assertTrue(MiscUtil.getOrdinalFor(-112).equals("-112th"));
		assertTrue(MiscUtil.getOrdinalFor(-113).equals("-113th"));
		assertTrue(MiscUtil.getOrdinalFor(-114).equals("-114th"));
		assertTrue(MiscUtil.getOrdinalFor(2220).equals("2220th"));
		assertTrue(MiscUtil.getOrdinalFor(2221).equals("2221st"));
		assertTrue(MiscUtil.getOrdinalFor(2222).equals("2222nd"));
		assertTrue(MiscUtil.getOrdinalFor(2223).equals("2223rd"));
		assertTrue(MiscUtil.getOrdinalFor(2224).equals("2224th"));
	}

	@Test
	public void testGetDistanceFromLatLon() {
		// verified using http://www.movable-type.co.uk/scripts/latlong.html
		assertTrue(Math
				.round(MiscUtil.getDistanceFromLatLon(20, 140, 20, -140) / 1000) == 8264);
		assertTrue(Math
				.round(MiscUtil.getDistanceFromLatLon(20, 140, 20, 140) / 1000) == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDistanceFromLatLonInvalid() {
		System.out.println(MiscUtil.getDistanceFromLatLon(190, 20, 140, 20) / 1000);
	}

	@Test
	public void testIsDouble() {
		assertTrue(MiscUtil.isDouble("3"));
		assertTrue(MiscUtil.isDouble("3.14"));
		assertFalse(MiscUtil.isDouble(""));
		assertFalse(MiscUtil.isDouble("moo"));
	}

	private enum DayOfWeek {

		MONDAY(2), TUESDAY(3);

		DayOfWeek(int index) {
			this.index = index;
		}

		private int index;

		int getIndex() {
			return this.index;
		}

	}

	@Test
	public void testValueOfIgnoreCase() {
		assertEquals(MiscUtil.valueOfIgnoreCase(DayOfWeek.class, "monday")
				.getIndex(), 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testValueOfIgnoreCaseInvalid() {
		assertEquals(
				MiscUtil.valueOfIgnoreCase(DayOfWeek.class, "moon").getIndex(), 2);
	}

	@Test
	public void testGreatestCommonDivisorTwoInts() {
		assertEquals(MiscUtil.greatestCommonDivisor(0, 0), 0);// mathematically its infinity, but programmers should be able to interpret this
		assertEquals(MiscUtil.greatestCommonDivisor(7, 0), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(7, 1), 1);
		assertEquals(MiscUtil.greatestCommonDivisor(7, 7), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(22, 11), 11);
		assertEquals(MiscUtil.greatestCommonDivisor(1024, 20), 4);
		assertEquals(MiscUtil.greatestCommonDivisor(-7, 0), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(-7, 1), 1);
		assertEquals(MiscUtil.greatestCommonDivisor(-7, -7), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(-22, -11), 11);
		assertEquals(MiscUtil.greatestCommonDivisor(-1024, -20), 4);
	}

	@Test
	public void testGreatestCommonDivisorArray() {
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { 0, 0, 0 }), 0);// mathematically its infinity, but programmers should be able to interpret this
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { 7, 0, 0 }), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { 7, 1, 0 }), 1);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { 7, 7, 7 }), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { 22, 11, 0 }), 11);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { 1024, 20, 40 }), 4);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { -7, 0, 0 }), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { -7, -1, 0 }), 1);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { -7, -7, -7 }), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { -22, -11, 0 }), 11);
		assertEquals(MiscUtil.greatestCommonDivisor(new int[] { -1024, -20, -40 }), 4);
	}

	@Test
	public void testGreatestCommonDivisorList() {
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(0, 0, 0))), 0);// mathematically its infinity, but
										// programmers should be able to
										// interpret this
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(7, 0, 0))), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(7, 1, 0))), 1);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(7, 7, 7))), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(22, 11, 0))), 11);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(1024, 20, 40))), 4);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(-7, 0, 0))), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(-7, -1, 0))), 1);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(-7, -7, -7))), 7);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(-22, -11, 0))), 11);
		assertEquals(MiscUtil.greatestCommonDivisor(new ArrayList<Integer>(Arrays.asList(-1024, -20, -40))), 4);
	}

	@Test
	public void testModPositive() {
		assertEquals(MiscUtil.modPositive(0, 5), 0);
		assertEquals(MiscUtil.modPositive(17, 5), 2);
		assertEquals(MiscUtil.modPositive(17, -5), 2);
		assertEquals(MiscUtil.modPositive(-17, 5), 3);
		assertEquals(MiscUtil.modPositive(-17, -5), 3);
	}

	@Test(expected = ArithmeticException.class)
	public void testModPositiveInvalid() {
		MiscUtil.modPositive(-3, 0);
	}

	@Test
	public <T> void testRemoveDuplicates() {

		ArrayList<String> testArrayList = new ArrayList<String>();
		MiscUtil.removeDuplicates(testArrayList);
		assertTrue(testArrayList.equals(new ArrayList<String>()));

		testArrayList = new ArrayList<String>(Arrays.asList("b", "a"));
		MiscUtil.removeDuplicates(testArrayList);
		assertTrue(testArrayList.equals(new ArrayList<String>(Arrays.asList("b", "a"))));

		testArrayList = new ArrayList<String>(Arrays.asList("b", "a", "b"));
		MiscUtil.removeDuplicates(testArrayList);
		assertTrue(testArrayList.equals(new ArrayList<String>(Arrays.asList("b", "a"))));

		testArrayList = new ArrayList<String>(Arrays.asList("b", "a", "b", "b"));
		MiscUtil.removeDuplicates(testArrayList);
		assertTrue(testArrayList.equals(new ArrayList<String>(Arrays.asList("b", "a"))));

		testArrayList = new ArrayList<String>(Arrays.asList("a", "b", "b", "b", "b"));
		MiscUtil.removeDuplicates(testArrayList);
		assertTrue(testArrayList.equals(new ArrayList<String>(Arrays.asList("a", "b"))));

		LinkedList<String> testLinkedList = new LinkedList<String>(Arrays.asList("b", "a", "b"));
		MiscUtil.removeDuplicates(testLinkedList);
		assertTrue(testLinkedList.equals(new LinkedList<String>(Arrays.asList("b", "a"))));

		testLinkedList = new LinkedList<String>(Arrays.asList("b", "a", "b", "b"));
		MiscUtil.removeDuplicates(testLinkedList);
		assertTrue(testLinkedList.equals(new LinkedList<String>(Arrays.asList("b", "a"))));

		testLinkedList = new LinkedList<String>(Arrays.asList("a", "b", "b", "b", "b"));
		MiscUtil.removeDuplicates(testLinkedList);
		assertTrue(testLinkedList.equals(new LinkedList<String>(Arrays.asList("a", "b"))));

	}
	
	@Test
	public void testTrimTrailingZeros() {
		assertEquals(MiscUtil.trimTrailingZeros("123.01000000"),"123.01");
		assertEquals(MiscUtil.trimTrailingZeros("12301000000"),"12301000000");
		assertEquals(MiscUtil.trimTrailingZeros("1.2301E10"), "1.2301E10");
		assertEquals(MiscUtil.trimTrailingZeros("123"),"123");
		assertEquals(MiscUtil.trimTrailingZeros("123."),"123");
		assertEquals(MiscUtil.trimTrailingZeros("123.0"),"123");
		assertEquals(MiscUtil.trimTrailingZeros("1230.0"),"1230");
	}

	@Test
	public void testTrimTrailingZerosObject() {
		assertEquals(MiscUtil.trimTrailingZeros(123.01000000),"123.01");
		assertEquals(MiscUtil.trimTrailingZeros(12301000000.),"1.2301E10");
		assertEquals(MiscUtil.trimTrailingZeros(123.),"123");
		assertEquals(MiscUtil.trimTrailingZeros(123.0),"123");
		assertEquals(MiscUtil.trimTrailingZeros(1230.0),"1230");
	}

	@Test
	public void testGetLongestString() {
		assertEquals(MiscUtil.getLongestString(new ArrayList<String>()),"");
		assertEquals(MiscUtil.getLongestString(InstantiationUtil.newArrayList("hello", "heeeellllooooo")),"heeeellllooooo");
		assertEquals(MiscUtil.getLongestString(InstantiationUtil.newArrayList("heeeellllooooo", "heeeelllloooop")),"heeeellllooooo");
	}
	
	@Test
	public void testCapitalize() {
		assertEquals(MiscUtil.captialize(""),"");
		assertEquals(MiscUtil.captialize("a"),"A");
		assertEquals(MiscUtil.captialize("aaa"),"Aaa");
	}
}