package main;


import java.security.InvalidParameterException;
import java.util.Arrays;


/**
 * Implements a minHeap
 * @author archerheffern
 * @email hefferna@brandeis.edu
 */
public class MinHeap implements PriorityQueue {
	
	private int capacity;
	private int size;
	private GraphNode[] minHeap;
	private Map<GraphNode> mappings;
	
	/**
	 * Constructs MinHeap Instance with default height of 4 (height is measured as the
	 * number of verteces from the root to the deepest leaf)
	 * Allows negative values by default
	 */
	public MinHeap() {
		this(3);
	}
	
	/**
	 * Constructs Minheap Instance with specified height (height is measured as the
	 * number of verteces from the root to the deepest leaf)
	 * @param height of minheap
	 * @throws InvalidParameterException if height less than 0
	 */
	public MinHeap(int height) {
		if (height < 0) throw new InvalidParameterException("Height must be greater than or equal to 0");
		this.capacity = (2 << height) - 1;
		size = 0;
		mappings = new HashMap<>();
		minHeap = new GraphNode[this.capacity];
	}

	/**
	 * Gets capacity of heap
	 * @return current max capacity 
	 * O(1)
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Gets size
	 * @return number of elements within the heap
	 * O(1)
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets internal mappings: DO NOT USE if you don't know what you are doing
	 * @return internal map which pairs GraphNodes to their index within the minHeap
	 * O(1)
	 */
	public Map<GraphNode> getMappings() {
		return mappings;
	}

	/**
	 * Inserts GraphNode g into the queue based on its priority
	 * If g is already in the MinHeap, this operaton will do nothing
	 * @param g The graphnode being inserted
	 * @throws InvalidParameterException if g is null
	 * amortized O(logn)
	 * worst case O(n)
	 */
	public void insert(GraphNode g) {
		if (g == null) throw new InvalidParameterException("g cannot be null");
		if (contains(g)) return;
		ensureCapacity();
		minHeap[size] = g;
		mappings.set(g, size);
		heapifyUp(g);
		size++;
	}
	
	/**
	 * checks if graphNode is in the MinHeap
	 * @return boolean if graphNode is in the MinHeap
	 * Amortized O(1)
	 * Worst case O(n)
	 */
	public boolean contains(GraphNode g) {
		Integer value = mappings.getValue(g);
		return value != null && value >= 0;
	}
	
	/**
	 * checks if MinHeap at any time has had GraphNode g
	 * @param g GraphNode being checked for
	 * @return boolean if graphNode has been in the MinHeap
	 * Amortized O(1)
	 * Worst case O(n)
	 */
	public boolean contained(GraphNode g) {
		Integer value = mappings.getValue(g);
		return value != null;
	}

	/**
	 * Resizes array if required
	 * O(n)
	 */
	private void ensureCapacity() {
		if (capacity != size) return;
		int newCapacity = (capacity << 1) + 1;
		GraphNode[] newMinHeap = new GraphNode[newCapacity]; 
		for (int i = 0; i < size; i++) {
			newMinHeap[i] = minHeap[i];
		} minHeap = newMinHeap;
		capacity = newCapacity;
	}
	
	/**
	 *  Returns and removes from the priority queue the GraphNode
	 *  with the highest priority in the queue - since this is a MinHeap,
	 *  this will be the value with the smallest priority
	 *  @return GraphNode in priority Queue with the highest priority
	 *  O(1)
	 */
	
	public GraphNode heapExtractMin() {
		return extractElement(0);
	}
	
	/**
	 *  Returns and removes from the priority queue the GraphNode
	 *  with the highest priority in the queue - since this is a MinHeap,
	 *  this will be the value with the smallest priority
	 *  @return GraphNode in priority Queue with the highest priority
	 *  O(1)
	 */
	public GraphNode pullHighestPriorityElement() {
		return heapExtractMin();
	}
	
	/**
	 * Removes element
	 * @param index of element being removed
	 * @return GraphNode removed element
	 * @throws EmptyHeapException if heap is empty
	 * @throws ArrayIndexOutOfBoundsException if index does not contain a value
	 * O(logn)
	 */
	public GraphNode extractElement(int index) {
		if (isEmpty()) throw new EmptyHeapException("Cannot extract element from empty Heap");
		if (index >= size || index < 0) throw new InvalidParameterException("Index must be in bounds");
		GraphNode rmGN = minHeap[index];
		swap(rmGN, minHeap[size - 1]);
		minHeap[size - 1] = null;
		mappings.set(rmGN, -1);	// update index to -1
		size--;
		if (index != size) heapifyDown(minHeap[index]);
		// check if removed self
		return rmGN;
	}
	
	/**
	 * Updates a keys value and updates its position in the heap accordingly - this is the preferred way of updating a key
	 * @throws InvalidParameterException - g is null
	 * O(logn)
	 */
	public void updateKey(GraphNode g, int newPriority) {
		if (g == null) throw new InvalidParameterException("g cannot be null");
		g.priority = newPriority;
		rebalance(g);
	}
	
	/**
	 * Restores heap property of Priority Queue at g
	 * @throws InvalidParameterException - g is null
	 * O(logn)
	 */
	public void rebalance(GraphNode g) {
		heapify(g);
	}
	
	/**
	 * Checks if PriorityQueue is empty 
	 * @return boolean if Priority
	 * O(1)
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Restores heap property on graphNode g after changing its value
	 * @throws InvalidParameterException - g is null
	 * O(logn)
	 */
	public void heapify(GraphNode g) {
		if (g == null) throw new InvalidParameterException("GraphNode g cannot be null");
		heapifyUp(g);
		heapifyDown(g);
	}
	
	/**
	 * Moves an item up until moving it up further would break the heap property
	 * @param g The graphNode where heap property is being fixed
	 * @throws InvalidParameterException g is null
	 * O(logn)
	 */
	public void heapifyUp(GraphNode g) {
		if (g == null) throw new InvalidParameterException("GraphNode g cannot be null");
		// find the index of the value
		int gindex = mappings.getValue(g);
		int parentIndex = (gindex-1)/2;
		while (gindex > 0 && minHeap[parentIndex].priority > g.priority) {
			swap(g, minHeap[parentIndex]);
			gindex = parentIndex;
			parentIndex = (gindex - 1)/2;
		}
	}
	
	/**
	 * Moves an item down until moving it further down would break the heap property
	 * @param g The graphNode where heap property is being fixed
	 * O(logn)
	 */
	public void heapifyDown(GraphNode g) {
		if (g == null) throw new InvalidParameterException("g cannot be null");
		// find the two children
		// compare the parent and the smaller child and swap
		// find the index of the value
		int gIndex = mappings.getValue(g);
		// find the smaller of the two children
		int LIndex = gIndex * 2 + 1;
		int RIndex = gIndex * 2 + 2;
		GraphNode LItem;
		GraphNode RItem;
		int childIndex;
		
		if (LIndex >= capacity) LItem = null;
		else LItem = minHeap[LIndex];
		if (RIndex >= capacity) RItem = null;
		else RItem = minHeap[RIndex];
		if (LItem == null && RItem == null) return;
		else if (LItem == null) childIndex = RIndex;
		else if (RItem == null) childIndex = LIndex;
		else if (RItem.priority > LItem.priority) childIndex = LIndex;
		else childIndex = RIndex;
		while (minHeap[childIndex].priority < g.priority) {
			swap(g, minHeap[childIndex]);
			// update gindex to be where the item just got swapped to
			gIndex = childIndex;
			// update the child to be the smaller one
			LIndex = gIndex * 2 + 1;
			RIndex = gIndex * 2 + 2;
			// ! need to ensure don't access value out of bounds with LIndex and RIndex
			if (LIndex >= capacity) LItem = null;
			else LItem = minHeap[LIndex];
			if (RIndex >= capacity) RItem = null;
			else RItem = minHeap[RIndex];
			if (LItem == null && RItem == null) return;
			else if (LItem == null) childIndex = RIndex;
			else if (RItem == null) childIndex = LIndex;
			else if (RItem.priority > LItem.priority) childIndex = LIndex;
			else childIndex = RIndex;
		}
	} 
	
	/**
	 * Swaps two values, will update their mappings.
	 * O(1)
	 */
	private void swap(GraphNode g1, GraphNode g2) {
		// get both indeces
		int index1 = mappings.getValue(g1);
		int index2 = mappings.getValue(g2);
		// swap positions in the array
		minHeap[index1] = g2;
		minHeap[index2] = g1;
		// swap indeces in map
		mappings.set(g1, index2);
		mappings.set(g2, index1);
	}
	
	@Override
	/**
	 * @return String representation of entire heap including spaces not yet used
	 * O(n)
	 */
	public String toString() {
		return Arrays.toString(minHeap);
	}
	
	/**
	 * Gives a truncated string representation of the minHeap. Does not print null vlaues
	 * @return shortened toString not including null values
	 * O(n)
	 */
	public String devToString() {
		String result = "[";
		for (int i = 0; i < size - 1; i++) {
			result += minHeap[i].priority + ", ";
		} if (size > 0) {
			result += minHeap[size - 1].priority;
		}
		return result + "]";
	}
}
