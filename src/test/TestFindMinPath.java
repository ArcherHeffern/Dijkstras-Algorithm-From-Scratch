package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import main.GraphWrapper;
import main.FindMinPath;
import main.GraphNode;

/**
 * Tests for FindMinPath class
 * @author archerheffern
 * @email hefferna@brandeis.edu
 */

public class TestFindMinPath {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	private final InputStream originalIn = System.in;
	
	/**
	 * Changes input and output streams to new printStreams
	 */
	@BeforeEach
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	/**
	 * Returns input and output streams to their original streams
	 */
	@AfterEach
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	    System.setIn(originalIn);
	}
	
	/**
	 * Takes a string and pipes it to the System.in stream
	 * @param str The string being fed to the input stream
	 */
	public void userInput(String str) {
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
		System.setIn(in);
	}
	
	@Test
	void TestConstructorBasic() throws FileNotFoundException {
		FindMinPath fmp1 = new FindMinPath();
		assertEquals("answer.txt", fmp1.getOutputFileName());
		File answerFile1 = new File("answer.txt");
		assertEquals(0, answerFile1.length());
		// check the file is empty
		
		FindMinPath fmp2 = new FindMinPath("answer1.txt");
		assertEquals("answer1.txt", fmp2.getOutputFileName());
		File answerFile2 = new File("answer.txt");
		assertEquals(0, answerFile2.length());
		// check the file is empty
		
		assertThrows(InvalidParameterException.class, ()-> {
			new FindMinPath("");
		});
		
		assertThrows(InvalidParameterException.class, ()-> {
			new FindMinPath("node_edge_weights.txt", true);
		});
		
		assertThrows(InvalidParameterException.class, ()-> {
			new FindMinPath("node_ids.txt", true);
		});
		
		assertThrows(InvalidParameterException.class, ()-> {
			new FindMinPath("test_edge_weights.txt", true);
		});
		
		assertThrows(InvalidParameterException.class, ()-> {
			new FindMinPath("test_ids.txt", true);
		});
	}
	
	@Test
	void backtrackingTest() {
		GraphNode gn1 = new GraphNode("1", false);
		GraphNode gn2 = new GraphNode("2", false);
		GraphNode gn3 = new GraphNode("3", false);
		GraphNode gn4 = new GraphNode("4", false);
		GraphNode gn5 = new GraphNode("5", false);
		GraphNode gn6 = new GraphNode("6", false);
		GraphNode gn7 = new GraphNode("7", false);
		gn1.previousDirection = "";
		gn1.previousNode = null; 
		gn2.previousDirection = "South";
		gn2.previousNode = gn1; 
		gn3.previousDirection = "East";
		gn3.previousNode = gn2; 
		gn4.previousDirection = "South";
		gn4.previousNode = gn3; 
		gn5.previousDirection = "South";
		gn5.previousNode = gn4; 
		gn6.previousDirection = "West";
		gn6.previousNode = gn5; 
		gn7.previousDirection = "North";
		gn7.previousNode = gn6;
		
		FindMinPath fmp = new FindMinPath();
		assertEquals("South\nEast\nSouth\nSouth\nWest\nNorth\n", fmp.getString(gn7, gn1));
	}
	
	@Test
	void BasicTest() throws FileNotFoundException {
		userInput("test_ids.txt\ntest_edge_weights.txt\n0 0 1 1");
		GraphWrapper gw = new GraphWrapper(true);
		FindMinPath fmp = new FindMinPath(true);
		fmp.dijkstra(gw.getHome());
		String output = "";
		Scanner s = new Scanner(new File(fmp.getOutputFileName()));
		while (s.hasNextLine()) output += s.nextLine() + "\n";
		assertEquals("South\nEast\n", output);
		
//		 twice to ensure the file gets cleared
		userInput("test_ids.txt\ntest_edge_weights.txt\n0 0 1 1");
		GraphWrapper gw2 = new GraphWrapper(true);
		FindMinPath fmp2 = new FindMinPath(true);
		fmp2.dijkstra(gw2.getHome());
		String output2 = "";
		Scanner s2 = new Scanner(new File(fmp2.getOutputFileName()));
		while (s2.hasNextLine()) output2 += s2.nextLine() + "\n";
		assertEquals("South\nEast\n", output2);
	}
	
	@Test
	void MediumTest1() throws FileNotFoundException {
			
			GraphNode home = new GraphNode("0", false);
			GraphNode GN1 = new GraphNode("1", false);
			GraphNode GN2 = new GraphNode("2", false);
			GraphNode GN3 = new GraphNode("3", false);
			GraphNode GN4 = new GraphNode("4", false);
			GraphNode GN5 = new GraphNode("5", true);
			home.priority = 0;
			home.setSouth(GN1, 1);
			GN1.setEast(GN2, 2);
			GN1.setNorth(home, 1);
			GN2.setWest(GN1, 2);
			GN2.setSouth(GN5, 4);
			GN2.setEast(GN3, 1);
			GN3.setWest(GN2, 1);
			GN3.setSouth(GN4, 1);
			GN4.setNorth(GN3, 1);
			GN4.setWest(GN5, 1);
			GN5.setNorth(GN2, 4);
			GN5.setEast(GN4, 1);

			FindMinPath fmp = new FindMinPath(true);
			fmp.dijkstra(home);
			
			String output = "";
			Scanner s = new Scanner(new File(fmp.getOutputFileName()));
			while (s.hasNextLine()) output += s.nextLine() + "\n";
			assertEquals("South\nEast\nEast\nSouth\nWest\n", output);
	}

	@Test
	void MediumTest2() throws FileNotFoundException {
			
			GraphNode home = new GraphNode("0", false);
			GraphNode GN1 = new GraphNode("1", false);
			GraphNode GN2 = new GraphNode("2", false);
			GraphNode GN3 = new GraphNode("3", false);
			GraphNode GN4 = new GraphNode("4", true);
			GraphNode GN5 = new GraphNode("5", false);
			home.priority = 0;
			home.setSouth(GN1, 1);
			home.setEast(GN5, 8);
			GN1.setEast(GN2, 2);
			GN1.setNorth(home, 1);
			GN2.setWest(GN1, 2);
			GN2.setSouth(GN5, 4);
			GN2.setEast(GN3, 1);
			GN3.setWest(GN2, 1);
			GN3.setSouth(GN4, 1);
			GN4.setNorth(GN3, 1);
			GN4.setWest(GN5, 1);
			GN5.setWest(home, 8);
			GN5.setNorth(GN2, 4);
			GN5.setEast(GN4, 1);

			FindMinPath fmp = new FindMinPath(true);
			fmp.dijkstra(home);
			
			String output = "";
			Scanner s = new Scanner(new File(fmp.getOutputFileName()));
			while (s.hasNextLine()) output += s.nextLine() + "\n";
			assertEquals("South\nEast\nEast\nSouth\n", output);
	}
	
	@Test
	void EdgeCaseTest1() throws FileNotFoundException {
		GraphNode home = new GraphNode("0", true);
		FindMinPath fmp = new FindMinPath(true);
		fmp.dijkstra(home);
		String output = "";
		Scanner s = new Scanner(new File(fmp.getOutputFileName()));
		while (s.hasNextLine()) output += s.nextLine() + "\n";
		assertEquals("", output);
	}
	
	@Test
	void DestinationDoesNotExistTest() throws FileNotFoundException {
		GraphNode home = new GraphNode("0", false);
		FindMinPath fmp = new FindMinPath(true);
		fmp.dijkstra(home);
		String output = "";
		Scanner s = new Scanner(new File(fmp.getOutputFileName()));
		while (s.hasNextLine()) output += s.nextLine() + "\n";
		assertEquals("", output);
	}

	@Test
	void TestLargeFile() throws FileNotFoundException {
		FindMinPath fmp = new FindMinPath(true);
		fmp.dijkstra(new GraphWrapper().getHome());
		String output = "";
		Scanner s = new Scanner(new File(fmp.getOutputFileName()));
		while (s.hasNextLine()) output += s.nextLine() + "\n";
		assertEquals("South\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "North\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "West\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "North\n"
				+ "East\n"
				+ "North\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "South\n"
				+ "South\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "South\n"
				+ "East\n"
				+ "East\n"
				+ "East\n"
				+ "East\n", output);
	}
}

