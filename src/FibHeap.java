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
		if(root.left==root) {
			root.left=root.right;
		}else{
			root.right.right.left = root.right;
		}
		if(root.right.priority < root.priority) {
			root = root.right;
		}
		size++;
	}

	// pops off the root
	public E popMin() {
		if(size <= 0) {
			System.out.println("We are throwing a null");
			throw new NullPointerException();
		}
		System.out.println("Level order before popmin: \n"+visualString(root, root, false,0));
		System.out.println("The root is " + root.data);
		System.out.println("The size is " + size+"\n\n");
		
		E minData = root.data;
		displaceRoot();
		mergeAll();
		size--;
		updateRoot(root,root,false);
		System.out.println("The level order after popMin is:\n" + visualString(root, root, false,0));
		System.out.println("The size is " + size+"\n\n");
		return minData;
	}
	
	private void updateRoot(Node current, Node base, boolean rootAdded) {
		if(current.priority < root.priority) root = current;
		if (current.equals(base) && rootAdded){
			return;
		}
		updateRoot(current.right, base, true);
	}

	private String visualString(Node current, Node base, boolean baseAdded, int tabs) {
		String returned = "";
		for(int i = 0; i<tabs; i++) {
			returned+="║       ";
		}
		returned += "["+current.data +","+ current.degree+"]";
		if(current.child != null) {
			returned += "═══╗\n" + visualString(current.child, current.child, false, tabs+1)+"\n";
			for(int i = 0; i<tabs; i++) {
				returned+="║       ";
			}
			returned+="║       ";
		}
		if((current.right == base && baseAdded) || base.right == base) {
			return returned+"base of [" + current.data+","+current.degree+"] is "+current.right.data;
		}
		returned += "\n";
		for(int i = 0; i<tabs+1; i++) {
			returned+="║       ";
		}
		returned+="\n";
		
		return returned + visualString(current.right, base, true, tabs);
	}
	
	
	private String visualStringLeft(Node current, Node base, boolean baseAdded, int tabs) {
		String returned = "";
		for(int i = 0; i<tabs; i++) {
			returned+="║       ";
		}
		returned += "["+current.data +","+ current.degree+"]";
		if(current.child != null) {
			returned += "═══╗\n" + visualStringLeft(current.child, current.child, false, tabs+1)+"\n";
			for(int i = 0; i<tabs; i++) {
				returned+="║       ";
			}
			returned+="║       ";
		}
		if((current.left == base && baseAdded) || base.left == base) {
			return returned;
		}
		returned += "\n";
		for(int i = 0; i<tabs+1; i++) {
			returned+="║       ";
		}
		returned+="\n";
		
		return returned + visualStringLeft(current.left, base, true, tabs);
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
		//added stuff
		root.left.right = root.child;
		nullifyRootChildren(root.child, root.child, false);
		root=root.right;
	}

	private void nullifyRootChildren(Node current, Node base, boolean baseAdded) {
		current.parent=null;
		if(current.equals(base) && baseAdded) {
			
		}
		if(base == base.right) {
			
		}
		if (current.equals(base) && baseAdded || base == base.right){
			return;
		}
		nullifyRootChildren(current.right, base, true);
		
	}

	// This is a utility method to perform merges as part of the fib heap cleanup (happens on every popMin)
	//must track size during merge
	
	private void mergeAll() {
		Object[] nodesOfDegree = new Object[(int)( Math.log(size) / Math.log(1.618)) + 1];
		mergeHelper(nodesOfDegree, root, false);
	}
	
	//this.getValue == BAD
	private void mergeHelper(Object[] nodesOfDegree, Node currentNode, boolean rootIsAdded) {
		if ((currentNode.equals(root) && rootIsAdded) || root.right.equals(root)) {
			return;
		}
		if(nodesOfDegree[currentNode.degree] != null) {
			mergeTrees((Node) nodesOfDegree[currentNode.degree], currentNode);
			//System.out.println("The level order after the last merge is:\n" + visualString(root, root, false,0)+"\n\n\n");
			mergeHelper(new Object[nodesOfDegree.length], root, false);
		}
		else {
			nodesOfDegree[currentNode.degree] = currentNode;
			mergeHelper(nodesOfDegree, currentNode.right, true);
		}
	}
	
	private void mergeTrees(Node n1, Node n2){
		if(n1.priority > n2.priority) {
			mergeOrderedTrees(n2, n1);
		}
		else {
			mergeOrderedTrees(n1, n2);
		}
	}
	
	private void mergeOrderedTrees(Node min, Node newChild) {
		newChild.right.left = newChild.left;
		newChild.left.right = newChild.right;
		if(min.child == null){
			min.child = newChild;
			newChild.right = newChild;
			newChild.left = newChild;
			newChild.parent = min;
			min.degree++;
			if(newChild.equals(root)) {
				root = min;
			}
			return;
		}
		newChild.right = min.child;
		newChild.left = min.child.left;
		min.child.left.right = newChild;
		min.child.left = newChild;
		newChild.parent = min;
		min.degree++;
		if(newChild.equals(root)) {
			root = min;
		}
	}

	// decreases the key (useful for Dijkstra's)
	public void decreaseKey() {

	}

	// This method adjusts the heap based on if a node is a loser after a popMin
	private void shuffleHeap() {
		
	}
}
