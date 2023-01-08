package test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Test;
import main.Entry;
import main.GraphNode;

/**
 * @author archerheffern
 * @email hefferna@brandeis.edu
 */
public class EntryTest {

	@Test
	void testConstructor() {
		Entry e = new Entry(new GraphNode("123" , false), 1);
		assertEquals("ID: 123", e.key.toString());
		assertEquals(1, e.value);
	}
	
	@Test
	void testConstructorException() {
		assertThrows(InvalidParameterException.class, ()->{
			new Entry(null, 1);
			});
	}
}
