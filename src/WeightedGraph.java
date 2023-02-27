import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class WeightedGraph extends Graph {
	//We will track the sink node whenever shortestPath is called
	Node endNode;
	
	//Private node class to track parents
	private class Node {
		int vertex;
		Node parent;
		private Node(int vertex, Node parent) {
			this.vertex = vertex;
			this.parent = parent;
		}
	}
	
	public WeightedGraph(WeightedEdge[] edges) {
		super(edges);
	}
	
	public int shortestPath(int v, int w) {
		//nodeQueue is stores a node (vertex, parent), and a priority (distance to the vertex)
		FibHeap<Node> nodeQueue = new FibHeap<Node>();
		Node currentNode = new Node(v, null);
		ArrayList<Integer> visited = new ArrayList<Integer>();
		int polledDistance = 0;
		visited.add(v);
		while (currentNode.vertex != w) {
			LinkedList<Edge> childEdges = adjacentSet(currentNode.vertex);
			//Want to ensure we are not at a dead end
			if (childEdges != null) {
				for (Edge edge : childEdges) {
					Node childNode = new Node(edge.getSecondIndex(), currentNode);
					//if(!nodeQueue.decrease_key(childNode, polledDistance + ((WeightedEdge) edge).getWeight())) {
						nodeQueue.push(childNode, polledDistance + ((WeightedEdge) edge).getWeight());
					//}
					
				}
			}
			//Get rid of elements in the Queue that have already been visited
			while (visited.contains(nodeQueue.peek().vertex)) {
				nodeQueue.popMin();
			}
			//Update variables for the next iteration
			polledDistance = nodeQueue.peekPriority();
			currentNode = nodeQueue.popMin();
			visited.add(currentNode.vertex);
		}		
		//Set the sink node
		endNode = currentNode;
		return polledDistance;
	}
	
	//pathTaken returns an ArrayList of vertices that have been visited along a path
	public ArrayList<Integer> pathTaken(int v, int w){
		ArrayList<Integer> path = new ArrayList<Integer>();
		//Calling this method will set the proper endNode
		shortestPath(v, w);
		Node currentNode = endNode;
		path.add(currentNode.vertex);
		while (currentNode.parent != null) {
			currentNode = currentNode.parent;
			path.add(currentNode.vertex);
		}
		Collections.reverse(path);
		return path;
	}
}
	