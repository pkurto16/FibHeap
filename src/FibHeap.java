//We have to track the size when we merge (not when we add)
public class FibHeap<E> {

	private class Node {
		E data;
		int priority;
		int lossCount = 0;
		int degree = 0;
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
		
		public Node(Node parent, E data, int priority) {
			this.data = data;
			this.priority = priority;
			this.left = this;
			this.right = this;
			this.parent = parent;

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
		if(size <= 0) {
			throw new NullPointerException();
		}
		E minData = root.data;
		int maxDegree = getMaxDegree(root, 0);
		displaceRoot();
		mergeAll(maxDegree);
		return minData;
	}
	
	private int getMaxDegree(Node currentNode, int max){
		if (currentNode.equals(root)){
			return max;
		}
		
		if(currentNode.degree > max) {
			max = currentNode.degree;
		}
		return getMaxDegree(currentNode.right, max);
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
	
	private void mergeAll(int maxDegree) {
		Node[] nodesOfDegree =  (Node[]) new Object[maxDegree + 1];
		mergeHelper(nodesOfDegree,root);
	}
	
	private void mergeHelper(Node[] nodesOfDegree, Node currentNode) {
		if (currentNode.equals(root)) {
			return;
		}
		if(nodesOfDegree[currentNode.degree] != null) {
			mergeTrees(nodesOfDegree[currentNode.degree], currentNode);
			mergeHelper((Node[]) new Object[nodesOfDegree.length], root);
		}
		else {
			nodesOfDegree[currentNode.degree] = currentNode;
			mergeHelper(nodesOfDegree, currentNode.right);
		}
	}
	
	private void mergeTrees(Node n1, Node n2){
		if(n1.priority >= n2.priority) {
			mergeOrderedTrees(n1, n2);
		}
		else {
			mergeOrderedTrees(n2, n1);
		}
	}
	
	private void mergeOrderedTrees(Node min, Node newChild) {
		if(newChild == root) {
			root = root.right;
		}
		newChild.right.left = newChild.left;
		newChild.left.right = newChild.right;
		if(min.child==null){
			min.child = newChild;
			newChild.right = newChild;
			newChild.left = newChild;
			newChild.parent = min;
			min.degree++;
		}
		newChild.right = min.child;
		newChild.left = min.child.left;
		min.child.left.right = newChild;
		min.child.left = newChild;
		newChild.parent = min;
		min.degree++;
	}

	// decreases the key (useful for Dijkstra's)
	public void decreaseKey() {

	}

	// This method adjusts the heap based on if a node is a loser after a popMin
	private void shuffleHeap() {
		
	}
}
