import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	
	private ArrayList<LinkedList<Edge>> adjacencyList;
	
	public Graph(Edge[] edges) {
		adjacencyList = new ArrayList<LinkedList<Edge>>();
		if (edges != null) {
			for (Edge edge : edges) {
				addEdge(edge); 
			}
		}
	}
	
	//adds an edge to the graph. Returns true if it is added, false if it is already in the graph
	public boolean addEdge(Edge e) {
		//The edge cannot be null, we cannot have self loops, and we cannot have parallel edges
	    if (e == null || e.toArray()[0] == e.toArray()[1] || containsEdge(e)) return false;
	    
	    if (adjacentSet(e.toArray()[0]) == null) {
	    		LinkedList<Edge> newList = new LinkedList<Edge>();
	    		newList.add(e);
	    		adjacencyList.add(newList);
	    		
	    } else {	
	    		adjacentSet(e.toArray()[0]).add(e);
	    }
	    return true;
	}
	
	public int size() {
		return adjacencyList.size();
	}

	//returns the degree of a vertex, based on how many vertices it goes to
	public int degree(int vertex) {
		return getAdjacencyList(vertex) == null ? 0 : getAdjacencyList(vertex).length;
	}

	//returns the list of adjacent vertices that can be reached starting from a given vertex
	public int[] getAdjacencyList(int vertex) {
		if (adjacentSet(vertex) == null) return null; 
		int[] adjacentVertices = new int[adjacentSet(vertex).size()];
		for (int i = 0; i < adjacentVertices.length; i++) { 
			adjacentVertices[i] = adjacentSet(vertex).get(i).toArray()[1]; 
		}
		return adjacentVertices;
	}
	
	//This returns the linked list of vertices adjacent to a given vertex (including the vertex passed in)
	protected LinkedList<Edge> adjacentSet(int vertex){
		for (LinkedList<Edge> adjacentSet : adjacencyList) {
			if (adjacentSet.getFirst().toArray()[0] == vertex) return adjacentSet; 
		}
		return null;
	}

	//checks if an edge e is contained in the graph
	public boolean containsEdge(Edge e) {
		if (e == null || adjacentSet(e.toArray()[0]) == null) return false; 
		for (Edge edge : adjacentSet(e.toArray()[0])) {
			if (e.equals(edge)) {
				return true;
			}
		}
		return false;
	}
}