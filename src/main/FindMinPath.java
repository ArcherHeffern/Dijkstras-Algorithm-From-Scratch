package main;

import java.security.InvalidParameterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Given a graph, will use dijkstra's algorithm to find the shortest path from point a to b
 * @author archerheffern
 * @email hefferna@brandeis.edu
 */

// should not allow GraphNodes with negative priorities
public class FindMinPath {
	
	private final String outputFileName;
	private File outputFile;
	private final String[] bannedOutputFilesDev = new String[] {"node_edge_weights.txt", "node_ids.txt", "test_edge_weights.txt", "test_ids.txt"};
	
	
	/**
	 * Runs dijkstras algorithm
	 * @param args idk
	 * @throws FileNotFoundException - if opening file results in an error
	 */
	public static void main(String[] args) throws FileNotFoundException {
		GraphWrapper gw = new GraphWrapper();
		FindMinPath fmp = new FindMinPath();
		fmp.dijkstra(gw.getHome());
	}
	/**
	 * Creates new FindMinPath instance with default output file of answer.txt
	 * WARNING: This constructor will clear all data out of any files named answer.txt within the directory
	 */
	public FindMinPath() {
		this("answer.txt", false);
	}
	
	/**
	 * Creates new FindMinPath instance with default output file of answer.txt
	 * WARNING: This constructor will clear all data out of any files named answer.txt within the directory
	 * @param dev boolean - if true will not allow certain output file names to be used since those are the same files being
	 * used as input for other parts of the program
	 */
	public FindMinPath(boolean dev) {
		this("answer.txt", dev);
	}
	
	/**
	 * Creates new FindMinPath instance with custom output file
	 * WARNING: This constructor will clear all data out of any files sharing the same name within the same directory
	 * @param output - output file name
	 */
	public FindMinPath(String output) {
		this(output, false);
	}
	
	/**
	 * Creates new FindMinPath instance with custom output file
	 * WARNING: This constructor will clear all data out of any files sharing the same name within the same directory
	 * @param output - output file name
	 * @param dev boolean - if true will not allow certain output file names to be used since those are the same files being
	 * used as input for other parts of the program
	 */
	public FindMinPath(String output, boolean dev) {
		if (dev) {
			for (String name: bannedOutputFilesDev) {
				if (name.equals(output)) throw new InvalidParameterException("Output file name cannot be " + output);
			}
		}
		if (output == "") throw new InvalidParameterException("Output file cannot be an empty string");
		this.outputFileName = output;
		outputFile = new File(output);
		clearFile();
	}
	
	/**
	 * Gets outputFileName
	 * @return String Name of file this class prints output to
	 */
	public String getOutputFileName() {
		return outputFileName;
	}
	
	/**
	 * Removes all content from a file
	 */
	private void clearFile() {
			try{
			FileWriter fw = new FileWriter(outputFileName, false);
			PrintWriter pw = new PrintWriter(fw, false);
			pw.flush();
			pw.close();
			}catch(Exception exception){

		        throw new RuntimeException("Unexpected error when clearing file of data");

		    }
	}

	/**
	 * Finds the shortest path from home to destination using dijkstras algorithm. 
	 * Traverses by using a GraphNodes North, South, East, and West references to other 
	 * GraphNodes. The destination is the GraphNode with its destination field set to true
	 * @param home The starting point of this algorithm
	 * @throws FileNotFoundException If there is an error attempting to open a provided file
	 */
	public void dijkstra(GraphNode home) throws FileNotFoundException {
		if (home == null) throw new InvalidParameterException("home cannot be null");
		
		PriorityQueue Q = new MinHeap();
		home.priority = 0;
		Q.insert(home);
		boolean foundGoal = false;
		GraphNode answer = null;
		
		while (!foundGoal && !Q.isEmpty()) {
			
			GraphNode curr = Q.pullHighestPriorityElement();
			if (curr.isGoalNode()) {
				answer = curr;
				foundGoal = true;
			} else {
				// for each neighbor
				if (curr.hasNorth()) processNeighbor(curr, curr.getNorth(), curr.getNorthWeight(), Q, "North");
				if (curr.hasSouth()) processNeighbor(curr, curr.getSouth(), curr.getSouthWeight(), Q, "South");
				if (curr.hasEast()) processNeighbor(curr, curr.getEast(), curr.getEastWeight(), Q, "East");
				if (curr.hasWest()) processNeighbor(curr, curr.getWest(), curr.getWestWeight(), Q, "West");
			}
		}
		clearFile();
		if (answer == null) return;
		PrintStream ps = new PrintStream(outputFile);
		ps.print(getString(answer, home));
		ps.close();
	}

	private void processNeighbor(GraphNode curr, GraphNode neighbor, int neighborWeight, PriorityQueue Q, String direction) {
		int x = curr.priority + neighborWeight;
		if (!Q.contained(neighbor)) {
			neighbor.priority = x;
			neighbor.previousNode = curr;
			neighbor.previousDirection = direction;
			Q.insert(neighbor);
		} else {
			if (x < neighbor.priority) {
				Q.updateKey(neighbor, x);
				neighbor.previousDirection = direction;
				neighbor.previousNode = curr;
			}				
		}
		
	}
	
	/**
	 * BackTracks through GraphNodes via its previousDirection field until home node is reached
	 * and gets string representation of all nodes passed through.
	 * @param g End point
	 * @param home Start point
	 * @return Path from Home to End
	 */
	public String getString(GraphNode g, GraphNode home) {
		String output = "";
		while (g != home) {
			output = g.previousDirection + "\n" + output;
			g = g.previousNode;
		} return output;
	}
}
