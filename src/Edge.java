public class Edge {
	private int vertex1;
	private int vertex2;
	
	public Edge(int p, int q) {
		vertex1 = p;
		vertex2 = q;
	}

	public int getFirstIndex() {
		return vertex1;
	}
	
	public int getSecondIndex() {
		return vertex2;
	}
	
	//returns true if the edge is incident to a vertex v
	public boolean incidentTo(int v){
		return v == vertex1 || v == vertex2;
	}

	//returns an array of length 2 representing the edge
	public int[] toArray() {
		return new int[] {vertex1, vertex2};
	}

	// returns true if the edge e is a parallel edge
	public boolean equals(Edge e) {
		return e.toArray()[0] == vertex1 && e.toArray()[1] == vertex2;
	}
}