package main;

/**
 * Map interface: Provides a layer of abstraction over my HashMap class
 * @author archerheffern
 * @param <T> Extends GraphNode. Defines the key of Map
 * @email hefferna@brandeis.edu
 */

public interface Map <T extends GraphNode> {
	
	/**
	 * Inserts value at key in hashmap, overriding elements with equivalent key
	 * @param key GraphNode
	 * @param value int
	 */
	public void set(T key, int value);
	
	
	/**
	 * Gets the value for the entry with g as the key
	 * @param g GraphNode - uses the ID field within as the hashvalue
	 * @return Integer value of entry with g as the key
	 */
	public Integer getValue(T g);
	
	
	/**
	 * boolean if the hashmap has key g
	 * @param g GraphNode - uses the ID field within as the hashvalue
	 * @return boolean if hashmap has key g
	 */
	public boolean hasKey(T g);
	
	
	/**
	 * String representation of keys value pairs within Map
	 * @return String - key values pairs
	 */
	public String toString();
	
}
