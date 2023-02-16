//We have to track the size when we merge (not when we add)
public class FibHeap<E> {

	private class Node {
		E data;
		int priority;
		int lossCount = 0;
		int size = 0;
		Node parent;
		Node left;
		Node right;
		Node child;

		public Node(E data, int priority) {
			this.data = data;
			this.priority = priority;
			this.left = this;
			this.right = this;

		}

		public Node(Node left, Node right, E data, int priority) {
			this.left = left;
			this.right = right;
			this.data = data;
			this.priority = priority;
		}

		public Node(Node parent, Node left, Node right, E data, int priority) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.data = data;
			this.priority = priority;
		}
	}

	Node root;
	int size;

	public FibHeap() {
		size = 0;
	}

	public void push(E data, int priority) {
		if(size == 0) {
			root = new Node(data, priority);
			return;
		}
		root.left = new Node(root.left, root, data, priority);
	}

	// pops off the root
	public E popMin() {
		if(size<=0) {
			throw new NullPointerException();
		}
		E minData = root.data;
		displaceRoot();
		mergeAll();
		return minData;
	}

	private void displaceRoot() {
		if(root.child == null) {
			root.left.right = root.right;
			root.right.left = root.left;
			root = root.right;
			return;
		}
		root.left.right = root.child;
		root.right.left = root.child.left;
		root.child.left.right = root.right;
		root.child.left = root.left;
		root.child.parent = null;
		root = root.right;
	}

	// This is a utility method to perform merges as part of the fib heap cleanup (happens on every popMin)
	//must track size during merge
	private void mergeAll() {
		Node currentNode = root;
		while (currentNode.right != currentNode){
			
		}
		
		
		for (int i = 1; i < root.size; i++){
			currentNode = currentNode.right;
			if (currentNode.size > maxDegree) maxDegree = currentNode.size;
		}
		int[] degreeArray = new int[maxDegree];
		mergeAll(root, degreeArray);
	}
	
	private void mergeAll() {
		int maxDegree = getMaxDegree;
		union
	}
	
	// decreases the key (useful for Dijkstra's)
	public void decreaseKey() {

	}

	// This method adjusts the heap based on if a node is a loser after a popMin
	private void shuffleHeap() {

	}
}
