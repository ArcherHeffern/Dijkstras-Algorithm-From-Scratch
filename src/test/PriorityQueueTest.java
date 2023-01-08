package test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import main.EmptyHeapException;
import main.GraphNode;
import main.MinHeap;
import main.PriorityQueue;

/**
 * @author archerheffern
 * @email hefferna@brandeis.edu
 */
public class PriorityQueueTest {

	@Test
	void TestConstructor() {
		MinHeap mh1 = new MinHeap();
		assertEquals(15, mh1.getCapacity());
		assertEquals(0, mh1.getSize());
		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
		assertEquals("{}", mh1.getMappings().toString());

		MinHeap mh2 = new MinHeap(10);
		assertEquals(2047, mh2.getCapacity());
		assertEquals(0, mh2.getSize());
		assertEquals(Arrays.toString(new Integer[2047]), mh2.toString());
		assertEquals("{}", mh2.getMappings().toString());
		
		MinHeap mh3 = new MinHeap(0);
		assertEquals(1, mh3.getCapacity());
		assertEquals(0, mh3.getSize());
		assertEquals(Arrays.toString(new Integer[1]), mh3.toString());
		assertEquals("{}", mh3.getMappings().toString());
	}
	
	@Test
	void TestConstructorExceptions() {
		assertThrows(InvalidParameterException.class, ()-> {
			new MinHeap(-1);
		});
		assertThrows(InvalidParameterException.class, ()-> {
			new MinHeap(-10);
		});
	}
	
	@Test
	void TestInsertBasic() {
		
//		mh1.insert(new GraphNode("5", false));
//		map.set(new GraphNode("5", false), "5".hashCode());
//		assertEquals(map.toString(), mh1.getMappings().toString());
//		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
//		
//		mh1.insert(new GraphNode("3", false));
//		map.set(new GraphNode("3", false), "3".hashCode());
//		assertEquals(map.toString(), mh1.getMappings().toString());
//		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
//		
//		mh1.insert(new GraphNode("4", false));
//		map.set(new GraphNode("4", false), "4".hashCode());
//		assertEquals(map.toString(), mh1.getMappings().toString());
//		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
//		
//		mh1.insert(new GraphNode("2", false));
//		map.set(new GraphNode("2", false), "2".hashCode());
//		assertEquals(map.toString(), mh1.getMappings().toString());
//		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
//		
//		mh1.insert(new GraphNode("10", false));
//		map.set(new GraphNode("10", false), "10".hashCode());
//		assertEquals(map.toString(), mh1.getMappings().toString());
//		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
//		
//		mh1.insert(new GraphNode("1", false));
//		map.set(new GraphNode("1", false), "1".hashCode());
//		assertEquals(map.toString(), mh1.getMappings().toString());
//		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
//		
//		mh1.insert(new GraphNode("6", false));
//		map.set(new GraphNode("6", false), "6".hashCode());
//		assertEquals(map.toString(), mh1.getMappings().toString());
//		assertEquals(Arrays.toString(new Integer[15]), mh1.toString());
//		
		MinHeap mh = new MinHeap();
		String[] elements = new String[] {"5", "3", "4", "2", "10", "1", "6"};
		String [][] temp = new String[][] {
			{"5"},
			{"3", "5"},
			{"3", "5", "4"},
			{"2", "3", "4", "5"},
			{"2", "3", "4", "5", "10"},
			{"1", "3", "2", "5", "10", "4"},
			{"1", "3", "2", "5", "10", "4", "6"}
			};
		assertEquals("[]", mh.devToString());
		for (int i = 0; i < temp.length; i++) {
			String element = elements[i];
			String innerHeap = Arrays.toString(temp[i]);
			GraphNode tempGN = new GraphNode(element, false);
			tempGN.priority = Integer.parseInt(element);
			mh.insert(tempGN);
			assertEquals(innerHeap, mh.devToString());
		}
	}
	
	@Test 
	void TestExceptions() {
		assertThrows(InvalidParameterException.class, ()->{
			PriorityQueue pq = new MinHeap();
			pq.insert(null);
		});
		assertThrows(InvalidParameterException.class, ()->{
			PriorityQueue pq = new MinHeap();
			pq.heapify(null);
		});
		assertThrows(InvalidParameterException.class, ()->{
			PriorityQueue pq = new MinHeap();
			pq.rebalance(null);
		});
	}
	
	@Test
	void TestResize() {
		// tests resize
		// tests 
		MinHeap mh = new MinHeap(0);
		String[] elements = new String[] {"5", "3", "4", "2", "10", "1", "6"};
		String [][] temp = new String[][] {
			{"5"},
			{"3", "5"},
			{"3", "5", "4"},
			{"2", "3", "4", "5"},
			{"2", "3", "4", "5", "10"},
			{"1", "3", "2", "5", "10", "4"},
			{"1", "3", "2", "5", "10", "4", "6"}
			};
		assertEquals("[]", mh.devToString());
		for (int i = 0; i < temp.length; i++) {
			String element = elements[i];
			String innerHeap = Arrays.toString(temp[i]);
			GraphNode tempGN = new GraphNode(element, false);
			tempGN.priority = Integer.parseInt(element);
			mh.insert(tempGN);
			assertEquals(innerHeap, mh.devToString());
		}
	}
	
	@Test
	void TestInsertAdvanced() {
		MinHeap mh = new MinHeap(2);
		String[] elements = new String[] {"4", "1", "3", "2", "16", "9", "10", "14", "8", "0"};
		String [][] temp = new String[][] {
			{"4"},
			{"1", "4"},
			{"1", "4", "3"},
			{"1", "2", "3", "4"},
			{"1", "2", "3", "4", "16"},
			{"1", "2", "3", "4", "16", "9"},
			{"1", "2", "3", "4", "16", "9", "10"},
			{"1", "2", "3", "4", "16", "9", "10", "14"},
			{"1", "2", "3", "4", "16", "9", "10", "14", "8"},
			{"0", "1", "3", "4", "2", "9", "10", "14", "8", "16"}
			};
		assertEquals("[]", mh.devToString());
		for (int i = 0; i < temp.length; i++) {
			String element = elements[i];
			String innerHeap = Arrays.toString(temp[i]);
			GraphNode tempGN = new GraphNode(element, false);
			tempGN.priority = Integer.parseInt(element);
			mh.insert(tempGN);
			assertEquals(innerHeap, mh.devToString());
			assertEquals(i + 1, mh.getSize());
		}
	}
	
	@Test
	void TestpullHighestPriorityElement() {
		// remove, then insert, then remove
		MinHeap mh = new MinHeap();
		String[] elements = new String[] {"4", "1", "3", "2", "16", "9", "10", "14", "8", "0"};
		for (String element: elements) {
			GraphNode gn = new GraphNode(element, false);
			gn.priority = Integer.parseInt(element);
			mh.insert(gn);
		}		
		assertEquals(10, mh.getSize());
		GraphNode highestPriorityElement = new GraphNode("0", false);
		highestPriorityElement.priority = 0;
		assertEquals(0, mh.pullHighestPriorityElement().priority);
		assertEquals(-1, mh.getMappings().getValue(highestPriorityElement));
		assertEquals(9, mh.getSize());
		
		highestPriorityElement = new GraphNode("1", false);
		highestPriorityElement.priority = 1;
		assertEquals(1, mh.pullHighestPriorityElement().priority);
		assertEquals(-1, mh.getMappings().getValue(highestPriorityElement));
		assertEquals(8, mh.getSize());
		
		// insert high priority element
		GraphNode gn = new GraphNode("-1", false);
		gn.priority = -1;
		mh.insert(gn);
		assertEquals(9, mh.getSize());
		
		// should not allow 3 to be inserted since it already exists within the heap
		gn = new GraphNode("3", false);
		gn.priority = 3;
		mh.insert(gn);
		assertEquals(9, mh.getSize());
		
		highestPriorityElement = new GraphNode("-1", false);
		assertEquals(-1, mh.pullHighestPriorityElement().priority);
		assertEquals(-1, mh.getMappings().getValue(highestPriorityElement));
		assertEquals(8, mh.getSize());
		
	}
	
	@Test
	void TestRebalanceAndHeapifyBasic() {
		MinHeap mh = new MinHeap();
		GraphNode myGn = new GraphNode("4", false);
		myGn.priority = 4;
		mh.insert(myGn);
		String[] elements = new String[] {"1", "3", "2", "16", "9", "10", "14", "8", "0"};
		for (String element: elements) {
			GraphNode gn = new GraphNode(element, false);
			gn.priority = Integer.parseInt(element);
			mh.insert(gn);
		}
		assertEquals("[0, 1, 3, 4, 2, 9, 10, 14, 8, 16]", mh.devToString());
		myGn.priority = -1;
		mh.heapifyUp(myGn);
		assertEquals("[-1, 0, 3, 1, 2, 9, 10, 14, 8, 16]", mh.devToString());
		mh.heapifyDown(myGn);
		assertEquals("[-1, 0, 3, 1, 2, 9, 10, 14, 8, 16]", mh.devToString());
		myGn.priority = 5;
		mh.heapifyDown(myGn);
		assertEquals(10, mh.getSize());
		assertEquals("[0, 1, 3, 5, 2, 9, 10, 14, 8, 16]", mh.devToString());
	}
	
	@Test
	void TestRebalanceAndHeapifyAdvanced() {
		MinHeap mh = new MinHeap();
		String[] elements = new String[] {"0", "1", "2", "3", "4", "5", "6", "8", "7", "9", "10", "11", "12", "13"};
		for (String element: elements) {
			GraphNode gn = new GraphNode(element, false);
			gn.priority = Integer.parseInt(element);
			mh.insert(gn);
		}
		GraphNode myGn = new GraphNode("14", false);
		myGn.priority = 14;
		mh.insert(myGn);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 8, 7, 9, 10, 11, 12, 13, 14]", mh.devToString());
		assertEquals(15, mh.getCapacity());
		assertEquals(15, mh.getSize());
		myGn.priority = -1;
		mh.rebalance(myGn);
		assertEquals("[-1, 1, 0, 3, 4, 5, 2, 8, 7, 9, 10, 11, 12, 13, 6]", mh.devToString());
		myGn.priority = 15;
		mh.rebalance(myGn);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 8, 7, 9, 10, 11, 12, 13, 15]", mh.devToString());
	}
	
	@Test
	void TestHeapUpdateKey() {
		MinHeap mh = new MinHeap();
		String[] elements = new String[] {"0", "1", "2", "3", "4", "5", "6", "8", "7", "9", "10", "11", "12", "13"};
		for (String element: elements) {
			GraphNode gn = new GraphNode(element, false);
			gn.priority = Integer.parseInt(element);
			mh.insert(gn);
		}
		GraphNode myGn = new GraphNode("14", false);
		myGn.priority = 14;
		mh.insert(myGn);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 8, 7, 9, 10, 11, 12, 13, 14]", mh.devToString());
		assertEquals(15, mh.getCapacity());
		assertEquals(15, mh.getSize());
		mh.updateKey(myGn, -1);
		assertEquals("[-1, 1, 0, 3, 4, 5, 2, 8, 7, 9, 10, 11, 12, 13, 6]", mh.devToString());
		mh.updateKey(myGn, 15);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 8, 7, 9, 10, 11, 12, 13, 15]", mh.devToString());
	}
	
//	@Test
//	void TestHeapExtractMin() {
//		
//	}
	
	@Test
	void TestExtractElement() {
		
		// remove index exactly the size
		MinHeap mh1 = new MinHeap();
		GraphNode gn1 = new GraphNode("1", false);
		mh1.insert(gn1);
		assertEquals(gn1, mh1.extractElement(0));
		
		MinHeap mh = new MinHeap(0);
		String[] elements = new String[] {"0", "1", "2", "3", "4", "5", "6", "8", "7", "9", "10", "11", "12", "13", "14"};
		for (String element: elements) {
			GraphNode gn = new GraphNode(element, false);
			gn.priority = Integer.parseInt(element);
			mh.insert(gn);
		}
		assertEquals(2, mh.extractElement(2).priority);
		assertEquals(14, mh.getSize());
		assertEquals(-1, mh.getMappings().getValue(new GraphNode("2", false)));
		assertEquals("[0, 1, 5, 3, 4, 11, 6, 8, 7, 9, 10, 14, 12, 13]", mh.devToString());

		assertEquals(13, mh.extractElement(13).priority);
		assertEquals(13, mh.getSize());
		assertEquals(-1, mh.getMappings().getValue(new GraphNode("13", false)));
		assertEquals("[0, 1, 5, 3, 4, 11, 6, 8, 7, 9, 10, 14, 12]", mh.devToString());
		
		assertEquals(1, mh.extractElement(1).priority);
		assertEquals(12, mh.getSize());
		assertEquals(-1, mh.getMappings().getValue(new GraphNode("1", false)));
		assertEquals("[0, 3, 5, 7, 4, 11, 6, 8, 12, 9, 10, 14]", mh.devToString());
		
		assertEquals(12, mh.extractElement(8).priority);
		assertEquals(11, mh.getSize());
		assertEquals(-1, mh.getMappings().getValue(new GraphNode("12", false)));
		assertEquals("[0, 3, 5, 7, 4, 11, 6, 8, 14, 9, 10]", mh.devToString());
		
		// add elements
		GraphNode gn = new GraphNode("17", false);
		gn.priority = Integer.parseInt("17");
		mh.insert(gn);
		GraphNode gn2 = new GraphNode("18", false);
		gn2.priority = Integer.parseInt("18");
		mh.insert(gn2);
		assertEquals(13, mh.getSize());
		assertEquals("[0, 3, 5, 7, 4, 11, 6, 8, 14, 9, 10, 17, 18]", mh.devToString());
		
		assertEquals(18, mh.extractElement(12).priority);
		assertEquals(12, mh.getSize());
		assertEquals(-1, mh.getMappings().getValue(new GraphNode("18", false)));
		assertEquals("[0, 3, 5, 7, 4, 11, 6, 8, 14, 9, 10, 17]", mh.devToString());
		
		assertEquals(3, mh.extractElement(1).priority);
		assertEquals(11, mh.getSize());
		assertEquals(-1, mh.getMappings().getValue(new GraphNode("3", false)));
		assertEquals("[0, 4, 5, 7, 9, 11, 6, 8, 14, 17, 10]", mh.devToString());
	}
	
	@Test
	void TestExtractElementExceptionAndIsEmpty() {
		// -----------------extract-min----------------------- //
		
		// remove from empty heap
		MinHeap mh1 = new MinHeap();
		assertTrue(mh1.isEmpty());
		assertThrows(EmptyHeapException.class, ()-> {
			mh1.heapExtractMin();
		});
		
		// remove from empty heap that previously had elements
		MinHeap mh2 = new MinHeap();
		GraphNode gn = new GraphNode("1", false);
		mh2.insert(gn);
		assertEquals(gn, mh2.heapExtractMin());
		assertTrue(mh2.isEmpty());
		assertThrows (EmptyHeapException.class, ()-> {
			mh2.heapExtractMin();
		});
		
		// ------------extract-element-(graphNode)------------ //
		
//		// remove gn from empty heap
//		MinHeap mh3 = new MinHeap();
//		assertThrows(EmptyHeapException.class, ()-> {
//			mh3.extractElement(new GraphNode("5", false));			
//		});
//		
//		// remove gn that does not exist in heap
//		MinHeap mhY = new MinHeap();
//		mhY.insert(new GraphNode("1", false));
//		assertThrows(InvalidParameterException.class, ()-> {
//			mhY.extractElement(new GraphNode("5", false));			
//		});
//		
//		// remove gn with null value
//		MinHeap mh4 = new MinHeap();
//		mh4.insert(new GraphNode("1", false));
//		assertThrows(InvalidParameterException.class, ()-> {
//			mh4.extractElement(new GraphNode(null, false));			
//		});
//		
//		// remove null graphNode
//		MinHeap mhX = new MinHeap();
//		mhX.insert(new GraphNode("1", false));
//		assertThrows(InvalidParameterException.class, ()-> {
//			mhX.extractElement(null);
//		});
		
		
		// ------------extract-element-(index)--------------- //
		
		// remove element from empty heap
		MinHeap mh5 = new MinHeap();
		assertThrows(EmptyHeapException.class, ()-> {
			mh5.extractElement(0);
		});
		
		// remove index that is null that does not exist in heap
		MinHeap mh6 = new MinHeap();
		GraphNode gn6 = new GraphNode("1", false);
		mh6.insert(gn6);
		assertEquals(gn6, mh6.heapExtractMin());
		assertThrows (EmptyHeapException.class, ()-> {
			mh6.extractElement(3);
		});
		
		// remove index one more than the size
		MinHeap mh7 = new MinHeap();
		GraphNode gn7 = new GraphNode("1", false);
		mh7.insert(gn7);
		assertThrows (InvalidParameterException.class, ()-> {
			mh7.extractElement(1);
		});
	}
	
	@Test
	void TestContains() {
		MinHeap mh = new MinHeap();
		GraphNode gn1 = new GraphNode("1", false);
		GraphNode gn2 = new GraphNode("2", false);
		GraphNode gn3 = new GraphNode("3", false);
		GraphNode gn4 = new GraphNode("4", false);
		GraphNode gn5 = new GraphNode("5", false);
		mh.insert(gn1);
		mh.insert(gn2);
		mh.insert(gn3);
		mh.insert(gn4);
		mh.insert(gn5);
		assertTrue(mh.contains(gn5));
		
		GraphNode gn6 = new GraphNode("6", false);
		assertFalse(mh.contains(gn6));
		
		assertTrue(mh.contains(gn1));
		mh.heapExtractMin();
		assertFalse(mh.contains(gn1));
	}
	
	@Test
	void TestInsertElementWithSameID() {
		// should ignore inserting elements with the same ID
		MinHeap mh = new MinHeap();
		GraphNode gn1 = new GraphNode("1", false);
		gn1.priority = 1;
		GraphNode gn2 = new GraphNode("2", false);
		gn2.priority = 2;
		GraphNode gn3 = new GraphNode("3", false);
		gn3.priority = 3;
		GraphNode gn4 = new GraphNode("4", false);
		gn4.priority = 4;
		GraphNode gn5 = new GraphNode("5", false);
		gn5.priority = 5;
		mh.insert(gn1);
		mh.insert(gn2);
		mh.insert(gn3);
		mh.insert(gn4);
		mh.insert(gn5);
		assertEquals("[1, 2, 3, 4, 5]", mh.devToString());
		assertEquals("{1: 0, 2: 1, 3: 2, 4: 3, 5: 4}", mh.getMappings().toString());
		mh.insert(gn1);
		mh.insert(gn2);
		mh.insert(gn3);
		mh.insert(gn4);
		mh.insert(gn5);
		assertEquals("[1, 2, 3, 4, 5]", mh.devToString());
		assertEquals("{1: 0, 2: 1, 3: 2, 4: 3, 5: 4}", mh.getMappings().toString());
		
		GraphNode gn6 = new GraphNode("1", false);
		gn6.priority = 10;
		GraphNode gn7 = new GraphNode("2", false);
		gn7.priority = 9;
		GraphNode gn8 = new GraphNode("3", false);
		gn8.priority = 8;
		GraphNode gn9 = new GraphNode("4", false);
		gn9.priority = 7;
		GraphNode gn10 = new GraphNode("5", false);
		gn10.priority = 6;
		mh.insert(gn6);
		mh.insert(gn7);
		mh.insert(gn8);
		mh.insert(gn9);
		mh.insert(gn10);
		assertEquals("[1, 2, 3, 4, 5]", mh.devToString());
		assertEquals("{1: 0, 2: 1, 3: 2, 4: 3, 5: 4}", mh.getMappings().toString());
	}
	
	@Test
	void TestHeapifyDownOnSingleElement() {
		MinHeap mh = new MinHeap();
		GraphNode gn1 = new GraphNode("1", false);
		gn1.priority = 1;
		mh.insert(gn1);
		assertDoesNotThrow(()-> mh.heapify(gn1));
		assertDoesNotThrow(()->mh.heapifyDown(gn1));
	}
	
}
