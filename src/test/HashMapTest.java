package test;

import static org.junit.jupiter.api.Assertions.*;


import java.security.InvalidParameterException;
import org.junit.jupiter.api.Test;
import main.HashMap;
import main.GraphNode;
import main.Map;

/**
 * @author Archer Heffern
 * @gmail hefferna@brandeis.edu
 */
public class HashMapTest {

	@Test
	void TestConstructors() {
		HashMap<GraphNode> hm1 = new HashMap<>();
		assertEquals(11, hm1.getCapacity());
		assertEquals(.0f, hm1.getLoadFactor());
		assertEquals(0, hm1.getLoadFactor());
		assertEquals(0, hm1.getSize());
		
		HashMap<GraphNode> hm2 = new HashMap<>(31);
		assertEquals(31, hm2.getCapacity());
		assertEquals(.0f, hm2.getLoadFactor());
		assertEquals(0, hm2.getLoadFactor());
		assertEquals(0, hm2.getSize());
		
		HashMap<GraphNode> hm3 = new HashMap<>(0.50f);
		assertEquals(11, hm3.getCapacity());
		assertEquals(.0f, hm3.getLoadFactor());
		assertEquals(0, hm3.getLoadFactor());
		assertEquals(0, hm3.getSize());
	}
	
	@Test
	void TestConstructorsExceptions() {
		// Capacity too small
		assertThrows(InvalidParameterException.class, ()-> {
			new HashMap<>(-1);
		});
		assertThrows(InvalidParameterException.class, ()-> {
			new HashMap<>(0);
		});
		// load factor too big or too small
		assertThrows(InvalidParameterException.class, ()-> {
			new HashMap<>(-1f);
		});
		assertThrows(InvalidParameterException.class, ()-> {
			new HashMap<>(0f);
		});
		assertThrows(InvalidParameterException.class, ()-> {
			new HashMap<>(1.1f);
		});
		assertEquals(0f, new HashMap<>(1f).getLoadFactor());
		assertEquals(1f, new HashMap<>(1f).getMaxLoadFactor());
	}
	
	@Test
	void TestdevSetBasic() {
		HashMap<GraphNode> hm = new HashMap<>(10);
		hm.devSet(new GraphNode("46", false), 46);
		assertEquals(.1f, hm.getLoadFactor());
		assertEquals(1, hm.getSize());
		hm.devSet(new GraphNode("34", false), 34);
		assertEquals(.2f, hm.getLoadFactor());
		assertEquals(2, hm.getSize());
		hm.devSet(new GraphNode("42", false), 42);
		assertEquals(.3f, hm.getLoadFactor());
		assertEquals(3, hm.getSize());
		hm.devSet(new GraphNode("23", false), 23);
		assertEquals(.4f, hm.getLoadFactor());
		assertEquals(4, hm.getSize());
		hm.devSet(new GraphNode("52", false), 52);
		assertEquals(.5f, hm.getLoadFactor());
		assertEquals(5, hm.getSize());
		hm.devSet(new GraphNode("33", false), 33);
		assertEquals(.6f, hm.getLoadFactor());
		assertEquals(6, hm.getSize());
		assertEquals(10, hm.getCapacity());
		assertEquals("[null, null, 42, 23, 34, 52, 46, 33, null, null]", hm.innerArray());
	}
	
	@Test
	void TestdevSetAdvanced() {
		// require a resize, check if new capacity is the first prime number over double the size of the current capacity aka 23 
		HashMap<GraphNode> hm = new HashMap<>(10);
		hm.devSet(new GraphNode("46", false), 46);
		assertEquals(.1f, hm.getLoadFactor());
		assertEquals(1, hm.getSize());
		hm.devSet(new GraphNode("34", false), 34);
		assertEquals(0.2f, hm.getLoadFactor());
		assertEquals(2, hm.getSize());
		hm.devSet(new GraphNode("42", false), 42);
		assertEquals(0.3f, hm.getLoadFactor());
		assertEquals(3, hm.getSize());
		hm.devSet(new GraphNode("23", false), 23);
		assertEquals(0.4f, hm.getLoadFactor());
		assertEquals(4, hm.getSize());
		hm.devSet(new GraphNode("52", false), 52);
		assertEquals(0.5f, hm.getLoadFactor());
		assertEquals(5, hm.getSize());
		hm.devSet(new GraphNode("33", false), 33);
		assertEquals(0.6f, hm.getLoadFactor());
		assertEquals(6, hm.getSize());
		// should update because has the same ID
		hm.devSet(new GraphNode("23", false), -1);
		assertEquals(0.6f, hm.getLoadFactor());
		assertEquals(6, hm.getSize());
		hm.devSet(new GraphNode("69", false), 69);
		assertEquals(0.7f, hm.getLoadFactor());
		assertEquals(7, hm.getSize());
		assertEquals("[null, null, 42, -1, 34, 52, 46, 33, null, 69]", hm.innerArray());
		// trigger rehash - new item gets inserted last
		hm.devSet(new GraphNode("2", false), 2);
		assertEquals(8/23f, hm.getLoadFactor());
		assertEquals(8, hm.getSize());
		assertEquals(23, hm.getCapacity());
		assertEquals("[34, null, null, null, 2, null, 42, 69, null, null, 46, null, null, null, -1, 52, null, null, null, null, null, null, 33]", hm.innerArray());
	}
	
	@Test
	void TestGetValueAndHasKey() {
		HashMap<GraphNode> hm = new HashMap<>(10);
		hm.devSet(new GraphNode("46", false), 46);
		hm.devSet(new GraphNode("34", false), 34);
		hm.devSet(new GraphNode("42", false), 42);
		hm.devSet(new GraphNode("23", false), 23);
		hm.devSet(new GraphNode("52", false), 52);
		hm.devSet(new GraphNode("33", false), 33);
		// [null, null, 42, 23, 34, 52, 46, 33, null, null]
		assertEquals(46, hm.getValue(new GraphNode("46", false)));
		assertTrue(hm.hasKey(new GraphNode("46", false)));
		assertEquals(52, hm.getValue(new GraphNode("52", false)));
		assertTrue(hm.hasKey(new GraphNode("52", false)));
		assertEquals(null, hm.getValue(new GraphNode("3", false)));
		assertFalse(hm.hasKey(new GraphNode("3", false)));
	}
	
	@Test
	void TestGetAdvanced() {
		Map<GraphNode> map = new HashMap<>();
		GraphNode gn1 = new GraphNode("4", false);
		gn1.priority = 4;
		map.set(new GraphNode("4", false), gn1.priority);
		assertEquals(4, map.getValue(gn1));
		
		GraphNode gn2 = new GraphNode("1", false);
		gn2.priority = 1;
		map.set(new GraphNode("1", false), gn2.priority);
		assertEquals(1, map.getValue(gn2));
		
		GraphNode gn3 = new GraphNode("3", false);
		gn3.priority = 3;
		map.set(new GraphNode("3", false), gn3.priority);
		assertEquals(3, map.getValue(gn3));
		
		GraphNode gn4 = new GraphNode("2", false);
		gn4.priority = 2;
		map.set(new GraphNode("2", false), gn4.priority);
		assertEquals(2, map.getValue(gn4));
		
		GraphNode gn5 = new GraphNode("16", false);
		gn5.priority = 16;
		map.set(new GraphNode("16", false), gn5.priority);
		assertEquals(16, map.getValue(gn5));
		
		GraphNode gn6 = new GraphNode("9", false);
		gn6.priority = 9;
		map.set(new GraphNode("9", false), gn6.priority);
		assertEquals(9, map.getValue(gn6));
		
		GraphNode gn7 = new GraphNode("10", false);
		gn7.priority = 10;
		map.set(new GraphNode("10", false), gn7.priority);
		assertEquals(10, map.getValue(gn7));
		
		GraphNode gn8 = new GraphNode("14", false);
		gn8.priority = 14;
		map.set(new GraphNode("14", false), gn8.priority);
		assertEquals(14, map.getValue(gn8));
		
		GraphNode gn9 = new GraphNode("8", false);
		gn9.priority = 8;
		map.set(new GraphNode("8", false), gn9.priority);
		assertEquals(8, map.getValue(gn9)); // returns null!
		
		GraphNode gn10 = new GraphNode("0", false);
		gn10.priority = 0;
		map.set(new GraphNode("0", false), gn10.priority);
		assertEquals(0, map.getValue(gn10));
	}
	
	@Test
	void TestToString() {
		HashMap<GraphNode> hm = new HashMap<>(10);
		hm.devSet(new GraphNode("46", false), 46);
		hm.devSet(new GraphNode("34", false), 34);
		hm.devSet(new GraphNode("42", false), 42);
		hm.devSet(new GraphNode("23", false), 23);
		hm.devSet(new GraphNode("52", false), 52);
		hm.devSet(new GraphNode("33", false), 33);
		hm.devSet(new GraphNode("23", false), -1);
		hm.devSet(new GraphNode("69", false), 69);
		assertEquals("[null, null, 42, -1, 34, 52, 46, 33, null, 69]", hm.innerArray());
		// trigger rehash - new item gets inserted last
		hm.devSet(new GraphNode("2", false), 2);
		assertEquals("[34, null, null, null, 2, null, 42, 69, null, null, 46, null, null, null, -1, 52, null, null, null, null, null, null, 33]", hm.innerArray());
		assertEquals("{34: 34, 2: 2, 42: 42, 69: 69, 46: 46, 23: -1, 52: 52, 33: 33}", hm.toString());
	}

	// Karen Tests
		
		@Test 
		void testInsert() { 
			HashMap<GraphNode> map = new HashMap<>();
			GraphNode node1 = new GraphNode("0", false);
			GraphNode node3 = new GraphNode("1", false);
			GraphNode node4 = new GraphNode("2", false);
			GraphNode node5 = new GraphNode("3", false);
			GraphNode node6 = new GraphNode("4", false);
			map.devSet(node1, 0);
			map.devSet(node3, 1);
			map.devSet(node4, 2);
			map.devSet(node5, 3);
			map.devSet(node6, 4);
			assertEquals("{0: 0, 1: 1, 2: 2, 3: 3, 4: 4}", map.toString());
			GraphNode node7 = new GraphNode("5", false);
			GraphNode node8 = new GraphNode("6", false);
			GraphNode node9 = new GraphNode("7", false);
			GraphNode node10 = new GraphNode("8", false);
			GraphNode node11 = new GraphNode("9", false);
			map.devSet(node7, 5);
			map.devSet(node8, 6);
			map.devSet(node9, 7);
			assertEquals(8, map.getSize());
			map.set(node10, 8);
			assertEquals(23, map.getCapacity());
			map.set(node11, 9);
			assertEquals("{0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9}", map.toString());
	}

}
