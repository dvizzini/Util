package com.danielvizzini.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class InstantiationUtilTest {
	
	@Test
	public void testMap() {
		HashMap<String,String> stoogesMap = InstantiationUtil.newHashMap("Larry", "dumb","Moe", "dumber","Curly", "dumbest");

		assertEquals(stoogesMap.size(), 3);
		assertEquals(stoogesMap.get("Larry"), "dumb");
		assertEquals(stoogesMap.get("Moe"), "dumber");
		assertEquals(stoogesMap.get("Curly"), "dumbest");
	}

	@Test
	public void testList() {
		ArrayList<String> stoogesList = InstantiationUtil.newArrayList("Larry", "Moe", "Curly");

		assertEquals(stoogesList.size(), 3);
		assertEquals(stoogesList.get(0),"Larry");
		assertEquals(stoogesList.get(1),"Moe");
		assertEquals(stoogesList.get(2),"Curly");
	}

	@Test
	public void testHashSet() {
		HashSet<String> stoogesSet = InstantiationUtil.newHashSet("Larry", "Moe", "Curly");

		assertEquals(stoogesSet.size(), 3);
		assertTrue(stoogesSet.contains("Larry"));
		assertTrue(stoogesSet.contains("Moe"));
		assertTrue(stoogesSet.contains("Curly"));
	}

	@Test
	public void testArrayDeque() {
		ArrayDeque<String> stoogesArrayDeque = InstantiationUtil.newArrayDeque("Larry", "Moe", "Curly");

		assertEquals(stoogesArrayDeque.size(), 3);
		assertTrue(stoogesArrayDeque.contains("Larry"));
		assertTrue(stoogesArrayDeque.contains("Moe"));
		assertTrue(stoogesArrayDeque.contains("Curly"));
	}
}
