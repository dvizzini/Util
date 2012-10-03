package com.danielvizzini.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

public class IterableUtilTest {

	private ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList("a","b","c","d"));

	@Test
	public void testJoinTwoParameters() {
		assertEquals(IterableUtil.join(new ArrayList<String>(),"-"), "");
		assertEquals(IterableUtil.join(new ArrayList<String>(Arrays.asList("a")),"-"), "a");
		assertEquals(IterableUtil.join(new ArrayList<String>(Arrays.asList("a","b")),"-"), "a-b");
		assertEquals(IterableUtil.join(new LinkedList<String>(Arrays.asList("a","b")),"-"), "a-b");
	}

	@Test
	public void testJoinOneParameter() {
		assertEquals(IterableUtil.join(new ArrayList<String>(Arrays.asList("a","b"))), "a b");
		assertEquals(IterableUtil.join(new LinkedList<String>(Arrays.asList("b ","a"))), "b a");
	}

	private ArrayList<String> phrases = new ArrayList<String>(Arrays.asList("a is one","b is two"));
	private ArrayList<String> phrasesBlank = new ArrayList<String>(Arrays.asList(""));
	private ArrayList<String> stringsInPhrases = new ArrayList<String>(Arrays.asList("A","C"));
	private ArrayList<String> stringsInPhrasesCaseSensitive = new ArrayList<String>(Arrays.asList("a","c"));
	private ArrayList<String> stringsNotInPhrases = new ArrayList<String>(Arrays.asList("d","f"));
	private String phrase = "a is one";
	private String stringInPhrases = "a Is";
	private String stringInPhrasesCaseSensitive = "a is";
	private String stringNotInPhrases = "c";

	@Test
	public void testStringsInPhrases() {
		assertFalse(IterableUtil.stringsInPhrases(stringsNotInPhrases, phrasesBlank));
		assertTrue(IterableUtil.stringsInPhrases(stringsInPhrases, phrases));
		assertTrue(IterableUtil.stringsInPhrases(stringsInPhrasesCaseSensitive, phrases));
		assertFalse(IterableUtil.stringsInPhrases(stringsNotInPhrases, phrases));
	}
	
	@Test
	public void testStringsInPhrasesCaseSensitive() {
		assertFalse(IterableUtil.stringsInPhrases(stringsNotInPhrases, phrasesBlank, true));
		assertFalse(IterableUtil.stringsInPhrases(stringsInPhrases, phrases, true));
		assertTrue(IterableUtil.stringsInPhrases(stringsInPhrasesCaseSensitive, phrases, true));
		assertFalse(IterableUtil.stringsInPhrases(stringsNotInPhrases, phrases, true));
	}
	
	@Test
	public void testStringInPhrases() {
		assertFalse(IterableUtil.stringInPhrases(stringNotInPhrases, phrasesBlank));
		assertTrue(IterableUtil.stringInPhrases(stringInPhrases, phrases));
		assertTrue(IterableUtil.stringInPhrases(stringInPhrasesCaseSensitive, phrases));
		assertFalse(IterableUtil.stringInPhrases(stringNotInPhrases, phrases));
	}

	@Test
	public void testStringInPhrasesCaseSensitive() {
		assertFalse(IterableUtil.stringInPhrases(stringNotInPhrases, phrasesBlank, true));
		assertFalse(IterableUtil.stringInPhrases(stringInPhrases, phrases, true));
		assertTrue(IterableUtil.stringInPhrases(stringInPhrasesCaseSensitive, phrases, true));
		assertFalse(IterableUtil.stringInPhrases(stringNotInPhrases, phrases, true));
	}

	@Test
	public void testStringInPhrase() {
		assertFalse(IterableUtil.stringsInPhrase(stringsNotInPhrases,""));
		assertTrue(IterableUtil.stringsInPhrase(stringsInPhrases, phrase));
		assertTrue(IterableUtil.stringsInPhrase(stringsInPhrasesCaseSensitive, phrase));
		assertFalse(IterableUtil.stringsInPhrase(stringsNotInPhrases, phrase));
	}
	
	@Test
	public void testStringInPhraseCaseSensitive() {
		assertFalse(IterableUtil.stringsInPhrase(stringsNotInPhrases,"", true));
		assertFalse(IterableUtil.stringsInPhrase(stringsInPhrases, phrase, true));
		assertTrue(IterableUtil.stringsInPhrase(stringsInPhrasesCaseSensitive, phrase, true));
		assertFalse(IterableUtil.stringsInPhrase(stringsNotInPhrases, phrase, true));
	}

	@Test
	public void testWordsInPhrase() {
		assertFalse(IterableUtil.wordsInPhrase(stringsInPhrases,""));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"a"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever\ta\twhatever"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever A whatever"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"a whatever"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever A"));
		assertFalse(IterableUtil.wordsInPhrase(stringsNotInPhrases,"whatever a"));
		assertFalse(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever aaaa whatever"));

		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever A"));
	}

	@Test
	public void testWordsInPhraseCaseSensitive() {
		assertFalse(IterableUtil.wordsInPhrase(stringsInPhrases,""));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"A"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever\tA\twhatever"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever A whatever"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"A whatever"));
		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever A"));
		assertFalse(IterableUtil.wordsInPhrase(stringsNotInPhrases,"whatever A"));
		assertFalse(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever AAAA whatever"));

		assertTrue(IterableUtil.wordsInPhrase(stringsInPhrases,"whatever A"));
	}

	@Test
	public void testGetContainingStrings() {
		assertEquals(IterableUtil.getContainingStrings(new ArrayList<String>(),"a is"), new ArrayList<String>());
		assertEquals(IterableUtil.getContainingStrings(arrayList,""), new ArrayList<String>());
		assertEquals(IterableUtil.getContainingStrings(arrayList,"zzz a is"), new ArrayList<String>(Arrays.asList("a")));
		assertEquals(IterableUtil.getContainingStrings(arrayList,"zzz a b is"), new ArrayList<String>(Arrays.asList("a","b")));
	}

	@Test 
	public void testGetStartingIndex() {
		assertTrue(IterableUtil.getStartingIndex(new ArrayList<String>(),"a is") == -1);
		assertTrue(IterableUtil.getStartingIndex(arrayList,"a is") == 0);
		assertTrue(IterableUtil.getStartingIndex(arrayList,"b") == 1);
		assertTrue(IterableUtil.getStartingIndex(arrayList,"e") == -1);
	}

	@Test
	public void testStartsWithAny() {
		assertFalse(IterableUtil.startsWithAny(new ArrayList<String>(),"a is"));
		assertTrue(IterableUtil.startsWithAny(arrayList,"a is"));
		assertTrue(IterableUtil.startsWithAny(arrayList,"b"));
		assertFalse(IterableUtil.startsWithAny(arrayList,"e"));
		assertFalse(IterableUtil.startsWithAny(new LinkedList<String>(),"a is"));
	}

	@Test
	public void testRemoveAnyStartingString() {
		assertEquals(IterableUtil.removeAnyStartingString(new ArrayList<String>(),"a is"),"a is");
		assertEquals(IterableUtil.removeAnyStartingString(arrayList,"a is"),"is");
		assertEquals(IterableUtil.removeAnyStartingString(arrayList,"A is"),"is");
		assertEquals(IterableUtil.removeAnyStartingString(arrayList,"b"),"");
		assertEquals(IterableUtil.removeAnyStartingString(arrayList,"e"),"e");
		assertEquals(IterableUtil.removeAnyStartingString(new LinkedList<String>(),"a is"),"a is");
	}

	@Test
	public void testSplit() {
		assertEquals(IterableUtil.split("","-"),new ArrayList<String>());
		assertEquals(IterableUtil.split("-","-"), new ArrayList<String>());
		assertEquals(IterableUtil.split("","-").size(), 0);
		assertEquals(IterableUtil.split("-","-").size(), 0);
		assertEquals(IterableUtil.split("a-b","-"), new ArrayList<String>(Arrays.asList("a","b")));
		assertEquals(IterableUtil.split("a- b","-"), new ArrayList<String>(Arrays.asList("a","b")));
		assertEquals(IterableUtil.split("a--b","-"), new ArrayList<String>(Arrays.asList("a","b")));
		assertEquals(IterableUtil.split("a-b-","-"), new ArrayList<String>(Arrays.asList("a","b")));
		assertEquals(IterableUtil.split("a-b--","-"), new ArrayList<String>(Arrays.asList("a","b")));
	}

	@Test
	public void testSplitIntoWords() {
		assertEquals(IterableUtil.split(""), new ArrayList<String>());
		assertEquals(IterableUtil.split(" "), new ArrayList<String>());
		assertEquals(IterableUtil.split("").size(), 0);
		assertEquals(IterableUtil.split(" ").size(), 0);
		assertEquals(IterableUtil.split(" "), new ArrayList<String>());
		assertEquals(IterableUtil.split("a b"), new ArrayList<String>(Arrays.asList("a","b")));
		assertEquals(IterableUtil.split("a  b"), new ArrayList<String>(Arrays.asList("a","b")));
		assertEquals(IterableUtil.split("a b "), new ArrayList<String>(Arrays.asList("a","b")));
		assertEquals(IterableUtil.split("a b  "), new ArrayList<String>(Arrays.asList("a","b")));
	}

	private ArrayList<Integer> toBeRemovedEnd = new ArrayList<Integer>(Arrays.asList(2, 3));
	private ArrayList<Integer> toBeRemovedBeginning = new ArrayList<Integer>(Arrays.asList(0, 1));
	private ArrayList<Integer> toBeRemovedAltEnd = new ArrayList<Integer>(Arrays.asList(3, 1));
	private ArrayList<Integer> toBeRemovedAltBeginning = new ArrayList<Integer>(Arrays.asList(2, 0));
	private ArrayList<Integer> toBeRemovedAll = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
	private ArrayList<Integer> toBeRemovedDuplicate = new ArrayList<Integer>(Arrays.asList(2, 2));
	private ArrayList<Integer> toBeRemovedNegative = new ArrayList<Integer>(Arrays.asList(-1, -2));
	private ArrayList<Integer> toBeRemovedMixed = new ArrayList<Integer>(Arrays.asList(-1, -2, 2, 4));

	@Test
	public void testRemoveIndices() {

		ArrayList<String> testArrayList = new ArrayList<String>();
		IterableUtil.removeIndices(testArrayList, toBeRemovedEnd);
		assertEquals(testArrayList, new ArrayList<String>());

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedEnd);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("a","b")));

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedBeginning);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("c","d")));

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedAltEnd);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("a","c")));

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedAltBeginning);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("b","d")));

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedAltBeginning);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("b","d")));

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedAll);
		assertEquals(testArrayList, new ArrayList<String>());

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedDuplicate);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("a","b","d")));

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedNegative);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("a","b","c","d")));

		testArrayList = new ArrayList<String>(arrayList);
		IterableUtil.removeIndices(testArrayList, toBeRemovedMixed);
		assertEquals(testArrayList, new ArrayList<String>(Arrays.asList("a","b","d")));

		LinkedList<String> testLinkedList = new LinkedList<String>();
		IterableUtil.removeIndices(testLinkedList, toBeRemovedEnd);
		assertEquals(testLinkedList, new LinkedList<String>());

	}
}