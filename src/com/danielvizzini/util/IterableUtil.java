package com.danielvizzini.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Kitchen sink of static methods with iterables as parameters.
 * Note that the split and join methods may be deprecated in favor of Guava.
 */
public class IterableUtil {
	
	//hide implicit constructor
	private IterableUtil() {}
	
	/**
	 * Joins elements of a List into a String, separated by specified separator
	 * @param pieces List to join, e.g. ArrayList<String>(Arrays.asList("a", "b"))
	 * @param separator String to separate elements of list, e.g. ", "
	 * @return String of list elements joined by separator, e.g. "a, b"
	 */
    public static String join(Iterable<?> pieces, String separator) {
        Iterator<?> i = pieces.iterator();
        if (!i.hasNext()) return "";
        StringBuilder buf = new StringBuilder();
        buf.append( String.valueOf(i.next()).trim() );        // We already know that size > 1
        while (i.hasNext()) {
          buf.append(separator).append( String.valueOf(i.next()) );
        }
        return buf.toString();
    }
    
	
	/**
	 * Converts List<String> to String, joining with spaces
	 * @param pieces List<String> to convert
	 * @return String containing all items in arrayList
	 */
	public static String join(Iterable<?> pieces) {
		return join(pieces, " ");
	}
	
	/**
	 * Splits Sting into ArrayList<String> of words separated by separator<br/>
	 * No elements will be "".<br/>
	 * e.g. split("a b", " ") returns ArrayList<String>(Arrays.asList({"a","b"}))<br/>
	 * @param str String to be split
	 * @param separator String that marks splits 
	 * @return This same string as ArrayList<String> split by separator
	 */
	public static ArrayList<String> split(String str, String separator) {
		ArrayList<String> forReturn = new ArrayList<String>(Arrays.asList((str + separator).split(separator)));		
		Iterator<String> iter;

		//trim elements
		iter = forReturn.iterator();
		int i = 0;
		while (iter.hasNext()) {
			forReturn.set(i, iter.next().trim());
			i++;
		}
		
		//remove empty elements
		iter = forReturn.iterator();
		while (iter.hasNext()) {
			String string = iter.next();
			if (string.equals("")) {
				iter.remove();
			}
		}

		return forReturn;

	}

	/**
	 * Splits Sting into ArrayList<String> of words separated by spaces
	 * No elements will be "".
	 * e.g. split("a b", " ") returns ArrayList<String>(Arrays.asList({"a","b"}))
	 * @param str String to be split
	 * @return This same string as ArrayList<String> of words
	 */
	public static ArrayList<String> split(String str) {
		return split(str, " ");		
	}
	
	/**
	 * Determines if any strings in a list are in a list of phrases
	 * Different than org.apache.commons.collections.CollectionUtils.containsAny() because one phrase must contain a string, not be a string
	 * @param strings iterable of words that may or may not be in phrases
	 * @param phrases iterable of phrases to be examined
	 * @param caseSensitive true if string is matched case sensitively, false otherwise
	 * @return true if any words are in any phrase, false otherwise
	 */
	public static boolean stringsInPhrases(Iterable<String> strings, Iterable<String> phrases, Boolean caseSensitive) {
		
		for (String phrase : phrases) {
			for (String string: strings) {
				string = Pattern.quote(string);
				Pattern pattern = caseSensitive ? Pattern.compile(string) : Pattern.compile(string, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
				if (pattern.matcher(phrase).find()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Determines if any strings in a list are in a list of phrases, case insensitively
	 * Different than org.apache.commons.collections.CollectionUtils.containsAny() because one phrase must contain a string, not be a string
	 * @param strings iterable of words that may or may not be in phrases
	 * @param phrases iterable of phrases to be examined
	 * @return true if any words are in any phrase, case insensitively, false otherwise
	 */
	public static boolean stringsInPhrases(Iterable<String> strings, Iterable<String> phrases) {
		return stringsInPhrases(strings, phrases, false);
	}
	
	/**
	 * Determines if a string is in a list of phrases
	 * @param string String of word(s) that may or may not be in phrases
	 * @param phrases iterable of phrases to be examined
	 * @param caseSensitive true if string is matched case sensitively, false otherwise
	 * @return true if any words are in any phrase, false otherwise
	 */
	public static boolean stringInPhrases(String string, Iterable<String> phrases, Boolean caseSensitive) {
		
		string = Pattern.quote(string);
		Pattern pattern = caseSensitive ? Pattern.compile(string) : Pattern.compile(string, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		for (String phrase : phrases) {
			if (pattern.matcher(phrase).find()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines if a string is in a list of phrases, case insensitively
	 * @param string String of word(s) that may or may not be in phrases
	 * @param phrases iterable of phrases to be examined
	 * @return true if any words are in any phrase, case insensitvely, false otherwise
	 */
	public static boolean stringInPhrases(String string, Iterable<String> phrases) {
		return stringInPhrases(string, phrases, false);
	}
	
	/**
	 * Determines if any string in a list are in a given phrase
	 * @param strings String of word(s) that may or may not be in phrases
	 * @param phrase iterable of phrases to be examined
	 * @param caseSensitive true if strings are matched case sensitively, false otherwise
	 * @return true if any words are in any phrase, false otherwise
	 */
	public static boolean stringsInPhrase(Iterable<String> strings, String phrase, Boolean caseSensitive) {
		
		for (String string : strings) {
			string = Pattern.quote(string);
			Pattern pattern = caseSensitive ? Pattern.compile(string) : Pattern.compile(string, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			if (pattern.matcher(phrase).find()) {
				return true;
			}
		}
		
		return false;

	}
	
	/**
	 * Determines if any string in a list are in a given phrase, case insensitively
	 * @param strings String of word(s) that may or may not be in phrases
	 * @param phrase iterable of phrases to be examined
	 * @return true if any words are in any phrase, case insensitively, false otherwise
	 */
	public static boolean stringsInPhrase(Iterable<String> strings, String phrase) {
		return stringsInPhrase(strings, phrase, false);
	}
	
	/**
	 * Determines if any words in a list are in a given phrase. A word is defined as what is between spaces.
	 * @param words iterable of words, separated by whitespace, that may or may not be in phrase
	 * @param phrase phrase to be examined
	 * @param caseSensitive true if words are matched case sensitively, false otherwise
	 * @return true if any words are in the phrase with whitespace on either side, false otherwise<br\>
	 * e.g. wordsInPhrase(Iterable("a"), "a bear", false) returns true and wordsInPhrase(Iterable("a"), "the bear") returns false, even though "bear" contains "a" 
	 */
	public static boolean wordsInPhrase(Iterable<String> words, String phrase, Boolean caseSensitive) {
				
		for (String word : words) {
			word = Pattern.quote(word);
			String[] regexes = new String[]{".*\\s" + word, ".*\\s" + word + "\\s.*", word + "\\s.*", word};
			for (String regex : regexes) {
				Pattern pattern = caseSensitive ? Pattern.compile(regex) : Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
				if (pattern.matcher(phrase).matches()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Determines if any words in a list are in a given phrase, case insensitively. A word is defined as what is between spaces.
	 * @param words iterable of words, separated by whitespace, that may or may not be in phrase
	 * @param phrase phrase to be examined
	 * @return true if any words are in the phrase with whitespace on either side, false otherwise<br\>
	 * e.g. wordsInPhrase(Iterable("A"), "a bear") returns true and wordsInPhrase(Iterable("A"), "the bear") returns false, even though "bear" contains "a" 
	 */
	public static boolean wordsInPhrase(Iterable<String> words, String phrase) {
		return wordsInPhrase(words, phrase, false);
	}
	
	/**
	 * @param stringList iterable of possible containing words
	 * @param str String to be examined
	 * @return list of matching element in startArray if matched, null otherwise
	 */
	public static ArrayList<String> getContainingStrings(Iterable<String> stringList, String str) {
		ArrayList<String> forReturn = new ArrayList<String>();
		for (String possibility : stringList) {
			if (str.contains(possibility)) {
				forReturn.add(possibility);
			}
		}
		return forReturn;
	}
	
	/**
	 * @param stringList iterable of possible starting words
	 * @param examinedString String to be examined 
	 * @return index of first matching element in startArray if matched, -1 otherwise
	 */
	public static int getStartingIndex(Iterable<String> stringList, String examinedString) {
		int i = 0;
		for (String string : stringList) {
			if (examinedString.startsWith(string)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/**
	 * @param stringList iterable of possible starting words
	 * @param str String to be examined
	 * @return true if str starts with any of the Strings in startArray, false otherwise.
	 */
	public static boolean startsWithAny(Iterable<String> stringList, String str) {
		return getStartingIndex(stringList, str) != -1;
	}
	
	/**
	 * @param stringList iterable of possible starting words
	 * @param str String to be examined
	 * @return String without the starting element, matched case insensitively
	 */
	public static String removeAnyStartingString(Iterable<String> stringList, String str) {
		for (String startingString : stringList) {
			Pattern pattern = Pattern.compile("^" + startingString + ".*$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			if (pattern.matcher(str).matches()) {
				Pattern replacementPattern = Pattern.compile(startingString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
				return replacementPattern.matcher(str).replaceFirst("").trim();
			}
		}
		return str;
	}
	
	/**
	 * mutates list by removing set of indices from a list
	 * @param list List of objects that will be trimmed
	 * @param toBeRemoved List<Integer> of indices to be removed
	 */
	public static void removeIndices(Iterable<?> list, Collection<Integer> toBeRemoved) {

		//remove duplicates discarding order
		HashSet<Integer> h = new HashSet<Integer>(toBeRemoved);
		toBeRemoved.clear();
		toBeRemoved.addAll(h);

		//remove valid subtractions
		int i = 0;
		Iterator<?> iter = list.iterator();
		while (iter.hasNext()) {
			iter.next();
			if (toBeRemoved.contains(i++)) {
				iter.remove();
			}
		}
	}
}