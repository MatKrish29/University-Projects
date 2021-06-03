// Full Name : Krishnamohan Maturankan
// UOW ID : w1790145
// IIT ID : 2019618

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FordFulkersonMaxFlow {
	private static int nodeCount;
	private static int[][] graph;
	private static final int source = 0;
	private static int sink;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("Ford-Fulkerson Max Flow: " + FordFulkerson(ParseFileAndGetGraph()));
		long end = System.currentTimeMillis();
		System.out.println("Elapsed Time: " + (end - start) + " milliseconds"); // Time elapsed to run the algorithm
	}

	private static int[][] ParseFileAndGetGraph() {
		try {
			File file = new File("Network.txt");
			Scanner reader = new Scanner(file);
			boolean totalNodesFound = false;
			// Reading the file and getting nodes and capacity
			while (reader.hasNext()) {
				// reading total node count only in the first line
				if (!totalNodesFound) {
					nodeCount = reader.nextInt();
					System.out.println("Total Nodes: " + nodeCount);
					graph = new int[nodeCount][nodeCount];
					sink = nodeCount - 1;
					totalNodesFound = true;
				}
				int node1 = reader.nextInt();
				int node2 = reader.nextInt();
				int capacity = reader.nextInt();
				System.out.println(node1 + " -> " + node2 + " = " + capacity);
				// Creating the network in the multi-dimensional array
				graph[node1][node2] = capacity;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return graph;
	}

	private static float FordFulkerson(int[][] graph) {
		// Making sure there is no error
		if (source == sink) {
			return 0;
		}

		// Filled by BFS to store path
		int[] parent = new int[nodeCount];
		int max_flow = 0;
		System.out.println();

		// While a path exists from source to sink loop
		while (hasAugmentingPath(parent)) {
			// to store path flow
			int path_flow = Integer.MAX_VALUE;
			StringBuilder path = new StringBuilder();
			path.append(sink);

			// Find maximum flow of path filled by bfs
			for (int j = sink; j != source; j = parent[j]) {
				int i = parent[j];
				path_flow = Math.min(path_flow, graph[i][j]);
				// Plotting the paths
				path.insert(0, " -> ");
				path.insert(0, i);
			}
			path.append(" [Flow = ").append(path_flow).append("]");

			// Printing the paths
			System.out.println(Arrays.toString(parent));
			System.out.println(path);

			for (int j = sink; j != source; j = parent[j]) {
				int i = parent[j];
				graph[i][j] -= path_flow; // Update forward flow
				graph[j][i] += path_flow; // Update backward flow
			}
			// Add path flow to max flow
			max_flow += path_flow;
		}
		System.out.println();
		return max_flow;
	}

	// Breadth First Search
	private static boolean hasAugmentingPath(int[] parent) {
		Queue<Integer> queue = new LinkedList<>(); // queue-like
		boolean[] visited = new boolean[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			visited[i] = false;
		}
		// Visit source
		queue.add(source);
		visited[source] = true;
		parent[source] = -1;
		// Loop through all vertices
		while (!queue.isEmpty()) {
			int i = queue.poll();
			// Check neighbours of parent i to get the child notes
			for (int j=0; j<nodeCount; j++) {
				// If not visited and positive value then visit
				if ((!visited[j]) && (graph[i][j] > 0)) {
					queue.add(j);
					visited[j] = true;
					parent[j] = i;
				}
			}
		}
		// Return boolean that tells us if we ended up at the destination
		// If there is no more path it returns false
		return visited[sink];
	}

	private static void deleteEdge(int node1, int node2) {
		graph[node1][node2] = 0;
	}
}