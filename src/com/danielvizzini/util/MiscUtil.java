package com.danielvizzini.util;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Kitchen sink of static methods with iterables as parameters to be used in multiple projects, kept in one place to avoid code drift.
 */
public class MiscUtil {

	//hide default constructor
	private MiscUtil() {}
	
    /**
     * Given a string, returns the MD5 hash
     *
     * @param s String whose MD5 hash value is required
     * @return An MD5 hash string
     * @throws java.security.NoSuchAlgorithmException if the MD5 hash algorithm is not found
     */
    public static String getMd5(String s) throws NoSuchAlgorithmException {

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update( s.getBytes(), 0, s.length() );

        String md5 = new BigInteger(1, m.digest()).toString(16);

        return md5;
    }
    
    
    /**
     * Return the ordinal for an integer, e.g. getOrdinalFor(1) returns 1st
     * @param value cardinal integer for which an ordinal will be returned
     * @return ordinal of cardinal integer
     */
	public static String getOrdinalFor(int value) {
		int hundredRemainder = value % 100;
		int tenRemainder = value % 10;
		if (hundredRemainder - tenRemainder == 10) {
			return value + "th";
		}

		switch (tenRemainder) {
		case 1:
			return value + "st";
		case 2:
			return value + "nd";
		case 3:
			return value + "rd";
		default:
			return value + "th";
		}
	}
	
	/**
	 * Calculates distance in meters between two geographical points
	 * @param lat1 latitude of first point
	 * @param lon1 longitude of first point
	 * @param lat2 latitude of second point
	 * @param lon2 longitude of second point
	 * @return great circle distance between the two specified points
	 */
    public static double getDistanceFromLatLon(double lat1, double lon1, double lat2, double lon2) {
    	
    	//validate
    	if (lat1 > 90 || lat1 < -90 || lat2 > 90 || lat2 < -90 || lon1 > 180 || lon1 < -180 || lon2 > 180 || lon2 < -180) {
    		throw new IllegalArgumentException("Cannot find distance between invalid coordinate(s)");
    	}
    	
        int R = 6371000; // meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * 
        	Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (R * c);
    }
    
    /**
     * Returns the constructor of a class given its parameters. Used instead of getConstructor because of that will not handle parameters with generics.
     * @param klass the class for the constructor sought
     * @param parameterTypes parameter types of constructor returned
     * @return constructor that constructs and instance of klass using the parameters specified
     * @throws NoSuchMethodException if the constructor specified does not exist
     */
	public static <T> Constructor<T> findConstructor(Class<T> klass, Class<?>... parameterTypes) throws NoSuchMethodException {
		@SuppressWarnings("unchecked")
		Constructor<T>[] constructors = (Constructor<T>[]) klass.getConstructors();
		for (Constructor<T> constructor : constructors) {
			Class<?>[] thisConstructorParameterTypes = constructor.getParameterTypes();
			if (thisConstructorParameterTypes.length == parameterTypes.length) {
				boolean match = true;
				for (int i = 0; i < thisConstructorParameterTypes.length && match; i++) {
					if (!thisConstructorParameterTypes[i].isAssignableFrom(parameterTypes[i]))
						match = false;
				}
				if (match)
					return constructor;
			}
		}
		throw new NoSuchMethodException();
	}
	
	/**
	 * @param string that may or may not be parsable to double
	 * @return true if string parses to double, false otherwise
	 */
	public static boolean isDouble(String string) {
		try {
			Double.valueOf(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Returns an enum instance given the enum class and the name of the instance. Similar to Enum.valueOf() but case insensitive
	 * @param enumeration Enum class (e.g. DaysOfWeek.class)
	 * @param name name of enum instance, regardless of case (e.g. "MoNdAy")
	 * @return the enum instance (e.g. MONDAY)
	 */
	public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumeration, String name) {
	    for(T enumValue : enumeration.getEnumConstants()) {
	        if(enumValue.name().equalsIgnoreCase(name)) {
	            return enumValue;
	        }
	    }
	    throw new IllegalArgumentException("There is no value with name '" + name + "' in Enum " + enumeration.getClass().getName());
	}
	
	/**
	 * Calculates the greatest common divisor of an array using Euclid's method. Thank you Euclid.
	 * @param integers Iterable of integers, can be negative or positive and in any order
	 * @return the greatest common divisor of all elements of the array
	 * <p>
	 * If the array is all zeros the greatest common divisor will be zero, and not the more mathematically correct infinity
	 */
	public static int greatestCommonDivisor(Iterable<Integer> integers) {
		
		int result = integers.iterator().next();
		for(int integer : integers){
		    result = greatestCommonDivisor(result, integer);
		}
		return result;	    
	}
	
	/**
	 * Calculates the greatest common divisor of an array using Euclid's method. Thank you Euclid.
	 * @param integers array of integers, can be negative or positive and in any order
	 * @return the greatest common divisor of all elements of the array
	 * <p>
	 * If the array is all zeros the greatest common divisor will be zero, and not the more mathematically correct infinity
	 */
	public static int greatestCommonDivisor(int[] integers) {
		
		int result = integers[0];
		for(int integer : integers){
		    result = greatestCommonDivisor(result, integer);
		}
		return result;	    
	}
	
	/**
	 * Calculates the greatest common divisor using Euclid's method
	 * @param firstInteger any integer, can be negative or positive, greater or smaller than second
	 * @param secondInteger any integer, can be negative or positive, greater or smaller than first
	 * @return the greatest common divisor of firstInteger and secondInteger
	 * <p>
	 * If both parameters are zero the greatest common divisor will be zero, and not the more mathematically correct infinity
	 */
	public static int greatestCommonDivisor(int firstInteger, int secondInteger) {
		
		//convert to positive numbers
		firstInteger = Math.abs(firstInteger);
		secondInteger = Math.abs(secondInteger);
		
		//find biggest and smallest absolute values
		int biggestPositiveInteger = Math.max(firstInteger, secondInteger);
		int smallestPositiveInteger = Math.min(firstInteger, secondInteger);
		
		//use Euclid's method (thank you Euclid)
		return greatestCommonDivisorInternal(biggestPositiveInteger, smallestPositiveInteger);
	    
	}
	
	/**
	 * Internal method to be used once parameters are cleaned.
	 */
	private static int greatestCommonDivisorInternal(int biggestPositiveInteger, int smallestPositiveInteger) {

		//use Euclid's algorithm (thank you Euclid)
	    if (smallestPositiveInteger == 0) {
	        return biggestPositiveInteger;
	    }
	    return greatestCommonDivisorInternal(smallestPositiveInteger, biggestPositiveInteger % smallestPositiveInteger);

	}
	
	/**
	 * @param integer the integer that one is applying the modulus operator to
	 * @param modulus the base of the modulus operator
	 * @return integer % modulus, set to be positive<br/> 
	 * Examples:<br/>
	 * modPositive(17, 5) == 2//true<br/>
	 * modPositive(17, -5) == 2//true<br/>
	 * modPositive(-17, 5) == 3//true<br/>
	 * modPositive(-17, -5) == 3//true<br/>
	 */
	public static int modPositive(int integer, int modulus) {
		
		int mod = integer % modulus;
	    return mod + (mod < 0 ? Math.abs(modulus) : 0);

	}

	/**
	 * Mutates list by removing duplicates and maintains order.
	 * @param list to be mutated
	 */
	public static <T> void removeDuplicates(Collection<T> list) {
		Set<T> set = new HashSet<T>();
		List<T> newList = new ArrayList<T>();
		for (Iterator<T> iter = list.iterator(); iter.hasNext();) {
			T element = iter.next();
			if (set.add(element)) {
				newList.add(element);				
			}
		}
		list.clear();
		list.addAll(newList);
	}
	
	/**
	 * Trims trailing zeros from string that represents a number
	 * @param numberString a string representing a number (e.g. "123.000")
	 * @return that string without its trailing zeros (e.g. "123")
	 */
	public static String trimTrailingZeros(String numberString) {
		if (numberString.contains("E")) return numberString;
		if (!numberString.contains(".")) return numberString;
		int length =  numberString.length();
	    while (numberString.charAt(length - 1) == '0') { 
	        length--;
	    }
	    if (numberString.charAt(length - 1) == '.') length--;
	    return numberString.substring(0,length);
	}
	
	/**
	 * Converts number to String and trims it of trailing zeros
	 * @param number Number object to be trimmed (e.g. Double.valueOf(123))
	 * @return that string without its trailing zeros (e.g. "123")
	 */
	public static String trimTrailingZeros(Number number) {
		return trimTrailingZeros(number.toString());
	}
	
	/**
	 * @param stringList list of strings
	 * @return longest string. If iterable is empty, "" is returned. If two strings are equally long, the first is returned.
	 */
	public static String getLongestString(Iterable<String> stringList) {
		String forReturn = "";
		for (String string : stringList) {
			if (string.length() > forReturn.length()) {
				forReturn = string;
			}
		}
		return forReturn;
	}

	/**
	 * Capitalizes first letter of String
	 * @param string string to be capitalized
	 * @return Capitalized String. If string is "", "" is returned.
	 */
	public static String captialize(String string) {
		if (string.length() <= 1) return string.toUpperCase();
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
}