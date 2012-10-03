package com.danielvizzini.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class KnapsackTest {

	private static Furniture giganticCouch = new Furniture(8);
	private static Furniture couch = new Furniture(4);
	private static Furniture table = new Furniture(3);
	private static Furniture dresser = new Furniture(3);
	private static Furniture endTable = new Furniture(2);
	private static Furniture lamp = new Furniture(2);
	
	private static ArrayList<Furniture> packingListWithExcessiveItem = new ArrayList<Furniture>(Arrays.asList(giganticCouch, couch, table, dresser));
	private static ArrayList<Furniture> packingList = new ArrayList<Furniture>(Arrays.asList(couch, table, dresser));
	private static ArrayList<Furniture> packingListGcd = new ArrayList<Furniture>(Arrays.asList(couch, endTable, lamp));

	private static Knapsack<Furniture> knapsack;
	
	@Test
	public void testPackAndRemplenish() {
		knapsack = new Knapsack<Furniture>(new ArrayList<Furniture>(), 6, true);
		assertEquals(knapsack.size(), 0);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(0));
		knapsack = new Knapsack<Furniture>(packingList, 0, true);
		assertEquals(knapsack.size(), 0);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(0));
		knapsack = new Knapsack<Furniture>(packingList, 6, true);
		assertTrue(knapsack.contains(table));
		assertTrue(knapsack.contains(dresser));
		assertEquals(knapsack.size(), 2);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(6));
		knapsack = new Knapsack<Furniture>(packingListGcd, 6, true);
		assertTrue(knapsack.contains(couch));
		assertTrue(knapsack.contains(endTable));
		assertEquals(knapsack.size(), 2);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(6));
		knapsack = new Knapsack<Furniture>(packingListGcd, 3, true);
		assertTrue(knapsack.contains(endTable));
		assertEquals(knapsack.size(), 1);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(2));
		knapsack = new Knapsack<Furniture>(packingListGcd, 4, true);
		assertTrue(knapsack.contains(couch));
		assertEquals(knapsack.size(), 1);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(4));
		assertEquals(packingListGcd.size(), 3);
		assertTrue(packingListGcd.contains(couch));
		assertTrue(packingListGcd.contains(endTable));
		assertTrue(packingListGcd.contains(lamp));
		knapsack = new Knapsack<Furniture>(packingListGcd, 4, false);
		assertTrue(knapsack.contains(couch));
		assertEquals(knapsack.size(), 1);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(4));
		assertEquals(packingListGcd.size(), 2);
		assertTrue(packingListGcd.contains(endTable));
		assertTrue(packingListGcd.contains(lamp));
		knapsack = new Knapsack<Furniture>(packingListWithExcessiveItem, 6, true);
		assertTrue(knapsack.contains(table));
		assertTrue(knapsack.contains(dresser));
		assertEquals(knapsack.size(), 2);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(6));
		assertEquals(packingListWithExcessiveItem.size(), 4);
		assertTrue(packingListWithExcessiveItem.contains(giganticCouch));
		assertTrue(packingListWithExcessiveItem.contains(couch));
		assertTrue(packingListWithExcessiveItem.contains(table));
		assertTrue(packingListWithExcessiveItem.contains(dresser));
		knapsack = new Knapsack<Furniture>(packingListWithExcessiveItem, 6);
		assertTrue(knapsack.contains(table));
		assertTrue(knapsack.contains(dresser));
		assertEquals(knapsack.size(), 2);
		assertEquals(knapsack.getTotalWeight(), Integer.valueOf(6));
		assertEquals(packingListWithExcessiveItem.size(), 2);
		assertTrue(packingListWithExcessiveItem.contains(giganticCouch));
		assertTrue(packingListWithExcessiveItem.contains(couch));
	}

}