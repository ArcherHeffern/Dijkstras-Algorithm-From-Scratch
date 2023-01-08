package main;

import java.security.InvalidParameterException;

/**
 * PriorityQueue interface: Provides a layer of abstraction over my minHeap class
 * @author archerheffern
 * @email hefferna@brandeis.edu
 */

public interface PriorityQueue {

	
	/**
	 * this should insert g into the queue with its priority
	 * @param g The graphnode being inserted - uses priority field to dictate where in the PriorityQueue the value will be inserted
	 * O(logn)
	 */
	public void insert(GraphNode g);
	
	
	/**
	 *  Returns and removes from the priority queue the GraphNode
	 *  with the highest priority in the queue.
	 *  @return GraphNode in priority Queue with the highest priority
	 *  @throws EmptyHeapException if PriorityQueue is empty
	 *  O(1)
	 */
	public GraphNode pullHighestPriorityElement();
	
	
	/**
	 * Checks if PriorityQueue is empty 
	 * @return boolean if PriorityQueue is empty
	 * O(1)
	 */
	public boolean isEmpty();
	
	
	/**
	 * Restores heap property of Priority Queue at g
	 * @param g GraphNode where the heap property is being fixed
	 * @throws InvalidParameterException - g is null or does not exist
	 * O(logn)
	 */
	public void rebalance(GraphNode g);
	
	
	/**
	 * Restores heap property on graphNode g after changing its priority
	 * @param g GraphNode where the heap property is being fixed
	 * O(logn)
	 */
	public void heapify(GraphNode g);
	
	
	/**
	 * Updates a keys value and updates its position in the heap accordingly - this is the preferred way of updating a key
	 * @param g The GraphNode where to priority is being fixed
	 * @param newPriority The new priority of g
	 * @throws InvalidParameterException - g is null
	 * O(logn)
	 */
	public void updateKey(GraphNode g, int newPriority);
	
	
	/**
	 * checks PriorityQueue contains GraphNode g
	 * @param g The GraphNode being looked for in the PriorityQueue
	 * @return boolean if graphNode is in the priorityQueue
	 * Amortized O(1)
	 * Worst case O(n)
	 */
	public boolean contains(GraphNode g);
	
	
	/**
	 * checks if PriorityQueue at any time has had GraphNode g at any point
	 * @param g GraphNode being checked for
	 * @return boolean if graphNode has been or is currently in priorityQueue
	 * Amortized O(1)
	 * Worst case O(n)
	 */
	public boolean contained(GraphNode g);
	
	/**
	 * String representation of Priority Queue elements as an array
	 * @return String representation of Priority Queue elements as an array
	 * O(n)
	 */
	public String toString();
	
	
	}
