package main;


import java.security.InvalidParameterException;

/**
 * Class of Key value pairs to be stored in HashMap
 * @author archerheffern
 * @email hefferna@brandeis.edu
 */
public class Entry {

	/**
	 * Key  
	 */
	public GraphNode key;
	
	/**
	 * Value
	 */
	public int value;
	
	/**
	 * Constructs new Entry to be used in hashmap
	 * @param key GraphNode
	 * @param value index of the graph node in the heap array
	 * @throws InvalidParameterException if key is null
	 */
	public Entry(GraphNode key, int value) {
		if (key == null) throw new InvalidParameterException("Key cannot be null");
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Entry [key=" + key.getId() + ", value=" + value + "]";
	}
}
