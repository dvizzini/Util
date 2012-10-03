package com.danielvizzini.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void testReadFile() {
		try {
			File file = new File("lib/hello_world.txt");
			assertEquals(FileUtil.readFile(file), "Hello, world!");			
		} catch (IOException e) {
			fail("readFile() failed");
		}
	}
	
	@Test
	public void testReadFileBlank() {
		try {
			File file = new File("lib/blank_file.txt");
			assertEquals(FileUtil.readFile(file),"");
		} catch (IOException e) {
			fail("readFile() failed");
		}
	}

	@Test
	public void testReadFileNone() {
		try {
			File file = new File("lib/non_existent_file.txt");
			FileUtil.readFile(file);			
		} catch (FileNotFoundException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testReadFileLineByLine() {
		try {
			File file = new File("lib/hello_world.txt");
			ArrayDeque<String> calculatedQueue = new ArrayDeque<String>(FileUtil.readFileLineByLine(file));
			assertEquals(calculatedQueue.size(), 2);
			assertEquals(calculatedQueue.pop(), "Hello,");
			assertEquals(calculatedQueue.pop(), "world!");
		} catch (IOException e) {
			fail("readFile() failed");
		}
	}
	
	@Test
	public void testReadFileLineByLineBlank() {
		try {
			File file = new File("lib/blank_file.txt");
			assertEquals(FileUtil.readFileLineByLine(file).size(), 0);
		} catch (IOException e) {
			fail("readFile() failed");
		}
	}

	@Test
	public void testReadFileLineByLineNone() {
		try {
			File file = new File("lib/non_existent_file.txt");
			FileUtil.readFileLineByLine(file);			
		} catch (FileNotFoundException e) {
			assertTrue(true);
		}
	}
}
