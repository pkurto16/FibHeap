
public class WeightedEdge extends Edge{
	private int weight;
	
	//represents an edge from vertex v to vertex w (represented as ints)
	public WeightedEdge(int p, int q, int weight) {
		super(p, q);
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	
}
