package main;

import java.security.InvalidParameterException;

/**
 * 
 * @author Archer Heffern
 * @email hefferna@brandeis.edu
 * Hashmap implementation using linear probing
 * @param <T> must extend GraphNode
 */

public class HashMap<T extends GraphNode> implements Map<T> {
	
	private int capacity;
	private int size;
	private float loadFactor;
	private float maxloadFactor;
	private Entry[] hashTable;	// how to make generic
	
	/**
	 * Constructs hashmap with default capacity of 11 and default max load factor of .75
	 */
	public HashMap() {
		this(11, .75f);
	}
	
	/**
	 * Constructs hashmap with custom initial capacity
	 * @param capacity - initial max capacity
	 * @throws InvalidParameterException - if capacity less than 1
	 */
	public HashMap(int capacity) {
		this(capacity, .75f);
	}
	
	/**
	 * Constructs hashmap with custom load factor
	 * @param lf - loadfactor
	 * @throws InvalidParameterException - if load factor is greater than 1 or less than or equal to 0
	 */
	public HashMap(float lf) {
		this(11, lf);
	}
	
	/**
	 * Constructs hashmap with custom initial capacity and load factor
	 * @param capacity - initial capacity
	 * @param lf - max load factor before rehashing 
	 * @throws InvalidParameterException - if load factor is greater than 1 or less than or equal to 0
	 * @throws InvalidParameterException - if capacity less than 1
	 */
	public HashMap(int capacity, float lf) {
		if (capacity <= 0) throw new InvalidParameterException("Capacity must be greater than 0");
		if (lf > 1 || lf <= 0) throw new InvalidParameterException("Load factor must be greater than 0 and less or equal to 1");
		maxloadFactor = lf;
		this.capacity = capacity;
		setSize(0);
		// why is this giving error
		// how to create a list of entries of generic type T that extends GraphNode
		hashTable = new Entry[capacity];
	}
	
	/**
	 * Gets capacity
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Gets load factor
	 * @return loadFactor
	 */
	public float getLoadFactor() {
		return loadFactor;
	}
	
	/**
	 * Gets loadFactor required to trigger a rehash
	 * @return maximum load factor permitted
	 */
	public float getMaxLoadFactor() {
		return maxloadFactor;
	}
	
	/**
	 * Increments size by 1 and updates load factor
	 * O(1)
	 */
	private void incrementSize() {
		setSize(size + 1);
	}
	
	/**
	 * Sets size and updates load factor
	 * O(1)
	 */
	private void setSize(int size) {
		this.size = size;
		loadFactor = (float)this.size / capacity;
	}
	
	/**
	 * Gets size - the amount of elements within the hashmap
	 * @return int size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Inserts value at key in hashmap, overriding elements with equivalent key
	 * @param key being inserted
	 * @param value being inserted
	 * @throws InvalidParameterException Key cannot be null
	 * amortized O(1)
	 * worst case O(n)
	 */
	public void set(T key, int value) {
		if (key == null) throw new InvalidParameterException("Key cannot be a null value");
		setHelper(new Entry(key, value), archersHashCode(key.getId()), hashTable);
	}
	
	/**
	 * For testing purposes only, Inserts value at ID (stored within key) in hashmap
	 * @param key being inserted
	 * @param value being inserted
	 * @throws NumberFormatException - if key.getID() cannot be converted to an Integer
	 * amortized O(1)
	 * worst case O(n)
	 */
	public void devSet(T key, int value) {
		if (key == null) throw new InvalidParameterException("Key cannot be a null value");
		setHelper(new Entry(key, value), Integer.parseInt(key.getId()), hashTable);
	}
	
	/**
	 * Inserts value into hashTable at index given
	 * @param key
	 * amortized O(1)
	 * worst case O(n)
	 */
	private void setHelper(Entry e, int index, Entry[] hashTable) {
		if (e == null) throw new InvalidParameterException("e cannot be null");
		if (hashTable == null) throw new InvalidParameterException("Internal hashtable cannot be null");
		index = Math.abs(index) % capacity;
		boolean notInserted = true;
		while (notInserted) {
			if (hashTable[index] == null) {
				hashTable[index] = e;
				incrementSize();
				notInserted = false;
			}
			else if (hashTable[index].key.getId().equals(e.key.getId())) {
				hashTable[index] = e;
				notInserted = false;
			} else {
				index = ++index%capacity;	// hash function - linear probing
			}
		}
		if (loadFactor >= maxloadFactor) {
			rehash();
		}
	}
	
	/**
	 * Gets the value for the entry with g as the key
	 * @param g - uses ID value stored within to lookup value within map
	 * @return value of entry with g as the key or null if value does not exist
	 * amortized O(1)
	 * worst case O(n)
	 */
	public Integer getValue(T g) {
		if (g == null) throw new InvalidParameterException();
		return getValueHelper(g, archersHashCode(g.getId()));
	}
	
	/**
	 * For testing purposes only, gets the value for the entry with g as the key. Uses Integer.parseInt(key.ID) as key.
	 * @param g - uses ID value stored within to lookup value within map
	 * @return Integer value
	 * @throws NumberFormatException - if key.getID() cannot be converted to String
	 * amortized O(1)
	 * worst case O(n)
	 */
	public Integer getValueDev(T g) {
		return getValueHelper(g, Integer.parseInt(g.getId()));
	}
	
	/**
	 * Gets value stored within hashmap
	 * @param g - uses ID value stored within as seed to lookup value within map
	 * @param index - hashcode of value
	 * @return Integer value stored
	 * amortized O(1)
	 * worst case O(n)
	 */
	private Integer getValueHelper(T g, int index) {
		if (g == null) throw new InvalidParameterException();
		index = Math.abs(index) % capacity;
		while (true) {
			if (hashTable[index] == null) {
				return null;
			}
			else if (hashTable[index].key.getId() == g.getId()) {
				return hashTable[index].value;
			} else {
				index = ++index%capacity;	// hash function - linear probing
			}
		}
	}
	
	/**
	 * true if the hashmap has key g
	 * @param g - uses ID value stored within as seed for hashcode to lookup value within map
	 * @return boolean if hashmap has key g
	 * amortized O(1)
	 * worst case O(n)
	 */
	public boolean hasKey(T g) {
		return getValue(g) != null;
	}
	
	/**
	 * Rehashes hashtable using linear probing
	 * sets new capacity to the first prime number over 2x+1 as large as the current capacity
	 * O(n)
	 */
	private void rehash() {
		updateCapacity();
		setSize(0);
		Entry[] newHashTable = new Entry[capacity];
		for (Entry element: hashTable) {
			if (element !=  null) {
				setHelper(element, archersHashCode(element.key.getId()), newHashTable);				
			}
		}
		hashTable = newHashTable;
	}
	
	/**
	 * This is archers hash code
	 * @param input
	 * @return
	 */
	private int archersHashCode(String input) {
		if (input == null) throw new InvalidParameterException("input cannot be null");
		byte[] bytes = input.getBytes();
		int output = 0;
        for (byte v : bytes) {
            output = 29 * output + (v & 16);
        }
        return output;
	}
	
	/**
	 * Sets new capacity to the the next prime number over 2x+1 as large as the current capacity
	 */
	private void updateCapacity() {
		capacity = 2 * capacity + 1;
		while (!isPrime(capacity)) {
			capacity += 2;			
		}
		}
	
	/**
	 * Checks if a value is prime
	 * @param value being checked if is prime
	 * @return boolean if value is prime
	 * O(n)
	 */
	private boolean isPrime(int value) {
		if (value <= 0) return true;
		for (int i = 2; i <= value/2; i++) {
			if (value % i == 0) {
				return false;
			}
		}return true;
	}
	
	/**
	 * Gets inner hashTable
	 * @return String representation of inner hashMap
	 * O(n)
	 */
	public String innerArray() {
		String result = "[";
		for (int i = 0; i < hashTable.length - 1; i++) {
			Entry element = hashTable[i];
			if (element != null) result += element.value;
			else result += "null";
			result += ", ";
		}
		Entry lastValue = hashTable[hashTable.length - 1];
		if (lastValue == null) return result + "null]";
		return result + lastValue.value + "]";
	}
	
	/**
	 * @return string representation of key value pairs
	 * O(n)
	 */
	@Override
	public String toString() {
		String result = "{";
		int i = 0;
		int found = 0;
		while (found < size - 1) {
			Entry element = hashTable[i];
			if (element != null) {
				result += element.key.getId() + ": " + element.value + ", ";
				found++;
			}
			i++;
		}
		while (found < size) {
			Entry element = hashTable[i];
			if (element != null) {
				result += element.key.getId() + ": " + element.value;
				found++;
			}
			i++;
		}
		return result + "}";
	}
}
