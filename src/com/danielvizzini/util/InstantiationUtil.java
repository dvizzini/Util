package com.danielvizzini.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Kitchen sink of static methods used to instantiate sets, iterables, and maps
 */
public class InstantiationUtil {
	
	/**
	 * A constructor of ArrayDeque
	 * @param elements the elements in the array double-ended queue
	 * @return a double-ended queue with the elements in the order entered
	 */
	public static <E> HashSet<E> newHashSet(E... elements) {
	    HashSet<E> collection = new HashSet<E>();

	    for (E element: elements) {
	        collection.add(element);
	    }
	    return collection;
	    
	}
	
	/**
	 * A constructor of ArrayList
	 * @param elements the elements in the array list
	 * @return an array list with the elements in the order entered
	 */
	public static <E> ArrayList<E> newArrayList(E... elements) {
	    ArrayList<E> collection = new ArrayList<E>();

	    for (E element: elements) {
	        collection.add(element);
	    }
	    return collection;
	    
	}
	
	/**
	 * A constructor of ArrayDeque
	 * @param elements the elements in the array list
	 * @return an array list with the elements in the order entered
	 */
	public static <E> ArrayDeque<E> newArrayDeque(E... elements) {
	    ArrayDeque<E> collection = new ArrayDeque<E>();

	    for (E element: elements) {
	        collection.add(element);
	    }
	    return collection;
	    
	}
	
	/**
	 * A constructor of a HashMap<K,V> that takes parameters that must alternate between type K and then type V.<br/>
	 * WARNING: There is no type-checking on the parameter list. Use this method carefully and only when it is very convenient, such as when instantiating final variables.
	 * @param objects a list of alternating keys of type K and values of type V
	 * @return a HashMap with the specified values mapped to their corresponding keys 
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> HashMap<K,V> newHashMap(Object... objects) {
	    HashMap<K,V> map = new HashMap<K,V>();
	    
	    for (int i = 0; i < objects.length; i += 2) {
	        map.put((K) objects[i], (V) objects[i + 1]);
	    }
	    return map;
	    
	}
}
