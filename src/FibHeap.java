import java.util.ArrayList;

//We have to track the size when we merge (not when we add)
public class FibHeap<E> {

	public class Node {
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
			size++;
			return;
		}
		root.right = new Node(root, root.right, data, priority);
		root.right.right.left = root.right;
		if(root.left==root) {
			root.left=root.right;
		}
		size++;
	}

	// pops off the root
	public E popMin() {
		if(size <= 0) {
			System.out.println("We are throwing a null");
			throw new NullPointerException();
		}
//		System.out.println("Rights:"
//		+root.data+"->"
//		+root.right.data+"->"
//		+root.right.right.data+"->"
//		+root.right.right.right.data+"->"
//		+root.right.right.right.right.data+"->"
//		+root.right.right.right.right.right.data);
//		System.out.println("Lefts:"
//				+root.left.left.left.left.left.data+"<-"
//				+root.left.left.left.left.data+"<-"
//				+root.left.left.left.data+"<-"
//				+root.left.left.data+"<-"
//				+root.left.data+"<-"
//				+root.data);
		try {
			System.out.println(printRights(root, false));
		}
		catch(Exception e) {
			System.out.println("ERROR (rights)!!!!");
		}
		
		try {
			System.out.println(printLefts(root, false));
		}
		catch(Exception e) {
			System.out.println("ERROR (lefts)!!!!");
		}
		
		E minData = root.data;
		displaceRoot();
		mergeAll();
		size--;
		return minData;
	}
	
	private String printLefts(Node current, boolean rootAdded) {
		if(current==root && rootAdded) {
			return current.data+"";
		}
		return printLefts(current.left,true)+ "<-" + current.data;
	}
	
	private String printRights(Node current, boolean rootAdded) {
		if(current==root && rootAdded) {
			return ""+current.data;
		}
		return current.data+"->"+printRights(current.right,true);
	}
	private void displaceRoot() {
		if(root.child == null) {
			root.left.right = root.right;
			root.right.left = root.left;
			root = root.right;
			System.out.println("When the root is displaced, it is now " + root.data);
			return;
		}
		root.left.right = root.child;
		root.right.left = root.child.left;
		root.child.left.right = root.right;
		root.child.left = root.left;
		root.child.parent = null;
	}

	

	// This is a utility method to perform merges as part of the fib heap cleanup (happens on every popMin)
	//must track size during merge
	
	private void mergeAll() {
		Object[] nodesOfDegree = new Object[(int)( Math.log(size) / Math.log(1.618)) + 1];
		System.out.println(size);
		System.out.println("Arr Size:" + nodesOfDegree.length);
		mergeHelper(nodesOfDegree,root, false);
	}
	
	//this.getValue == BAD
	private void mergeHelper(Object[] nodesOfDegree, Node currentNode, boolean rootIsAdded) {
		if ((currentNode.equals(root) && rootIsAdded) || root.right==root) {
			return;
		}
		if(nodesOfDegree[currentNode.degree] != null) {
			mergeTrees((Node) nodesOfDegree[currentNode.degree], currentNode);
			mergeHelper(new Object[nodesOfDegree.length], root , false);
		}
		else {
			nodesOfDegree[currentNode.degree] = currentNode;
			mergeHelper(nodesOfDegree, currentNode.right, true);
		}
	}
	
	private void mergeTrees(Node n1, Node n2){
		if(n1.priority >= n2.priority) {
			mergeOrderedTrees(n2, n1);
		}
		else {
			mergeOrderedTrees(n1, n2);
		}
	}
	
	private void mergeOrderedTrees(Node min, Node newChild) {
		if(newChild == root) {
			root = root.right;
			newChild.left.right.left = newChild.left;
			newChild.left.left.right = newChild.right;
			if(min.child == null){
				min.child = newChild.left;
				newChild.left.right = newChild.left;
				newChild.left.left = newChild.left;
				newChild.left.parent = min;
				min.degree++;
				return;
			}
			newChild.left.right = min.child;
			newChild.left.left = min.child.left;
			min.child.left.right = newChild.left;
			min.child.left = newChild.left;
			newChild.left.parent = min;
		}
		else {
			newChild.right.left = newChild.left;
			newChild.left.right = newChild.right;
			if(min.child == null){
				min.child = newChild;
				newChild.right = newChild;
				newChild.left = newChild;
				newChild.parent = min;
				min.degree++;
				return;
			}
			newChild.right = min.child;
			newChild.left = min.child.left;
			min.child.left.right = newChild;
			min.child.left = newChild;
			newChild.parent = min;
		}
		min.degree++;
	}

	// decreases the key (useful for Dijkstra's)
	public void decreaseKey() {

	}

	// This method adjusts the heap based on if a node is a loser after a popMin
	private void shuffleHeap() {
		
	}
}
