package com.danielvizzini.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

/**
 * Kitchen sink of static methods used for reading files
 */
public class FileUtil {

	/**
	 * Reads file and returns contents as string
	 * @param file java.io.File object of file to read
	 * @return file contents as String, or "" if file is blank
	 * @throws FileNotFoundException
	 */
	public static String readFile(File file) throws FileNotFoundException {
		
		//setup
		String filePath = file.getAbsolutePath();
		FileInputStream fstream = new FileInputStream(filePath);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		String forReturn = "";
		
		try {

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				//add to returned String
				forReturn += strLine;
			}
			
			// Close the input stream
			in.close();			

		} catch (IOException e) {
			//do nothing, file is presumably empty so return ""
		}
		
		return forReturn;
		
	}
	
	/**
	 * Reads file and returns contents as ArrayDeque<String>, where each element is a line of the file
	 * @param file java.io.File object of file to read
	 * @return File contents as String, or "" if file is blank
	 * @throws FileNotFoundException
	 */
	public static ArrayDeque<String> readFileLineByLine(File file) throws FileNotFoundException {
		
		//Setup
		String filePath = file.getAbsolutePath();
		FileInputStream fstream = new FileInputStream(filePath);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		ArrayDeque<String> forReturn = new ArrayDeque<String>();
		
		try {
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				forReturn.add(strLine.trim());
			}

			// Close the input stream
			in.close();			
		} catch (IOException e) {
			//do nothing, file is presumably empty so return blank ArrayDeque<String>			
		}
		
		return forReturn;
		
	}
	
}