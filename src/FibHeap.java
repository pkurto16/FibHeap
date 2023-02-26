//We have to track the size when we merge (not when we add)
public class FibHeap<E> {
	Node found = null;
	
	public class Node {
		E data;
		int priority;
		boolean loser = false;
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
		if(decrease_key(data, priority)) {
			return;
		}
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
//		System.out.println("Level order after push: \n"+visualString(root, root, false,0)+"\n\n");
	}

	// pops off the root
	public E popMin() {
		if(size <= 0) {
			System.out.println("We are throwing a null");
			throw new NullPointerException();
		}
//		System.out.println("Level order before popmin: \n"+visualString(root, root, false,0));
//		System.out.println("The root is " + root.data);
//		System.out.println("The size is " + size+"\n\n");
		
		E minData = root.data;
		displaceRoot();
		mergeAll();
		size--;
		updateRoot(root,root,false);
//		System.out.println("The level order after popMin is:\n" + visualString(root, root, false,0)+"\n\n");
//		System.out.println("The size is " + size+"\n\n");
		return minData;
	}
	
	private void updateRoot(Node current, Node base, boolean rootAdded) {
		if(current.priority < root.priority) root = current;
		if (current.equals(base) && rootAdded){
			return;
		}
		updateRoot(current.right, base, true);
	}
	public String visualString() {
		return visualString(root,root,false,0);
	}
	private String visualString(Node current, Node base, boolean baseAdded, int tabs) {
		String returned = "";
		for(int i = 0; i<tabs; i++) {
			returned+="║                             ";
		}
		returned += "["+current.priority;
		returned+= current.left==null ? ", NL" : ", L:"+current.left.priority;
		returned+= current.right==null ? ", NR" : ", R:"+current.right.priority;
		returned+= current.parent==null ? ", NP" : ", P:"+current.parent.priority;
		returned+= current.child==null ? ", NC" : ", C:"+current.child.priority;
		returned += ", D:"+ current.degree+"]";
		if(current.child != null) {
			returned += "═══╗\n" + visualString(current.child, current.child, false, tabs+1)+"\n";
			for(int i = 0; i<tabs; i++) {
				returned+="║                             ";
			}
			returned+="║                             ";
		}
		if((current.right == base && baseAdded) || base.right == base) {
			return returned;
		}
		returned += "\n";
		for(int i = 0; i<tabs+1; i++) {
			returned+="║                             ";
		}
		returned+="\n";
		
		return returned + visualString(current.right, base, true, tabs);
	}
	
	private void displaceRoot() {
		if(root.child == null) {
			root.left.right = root.right;
			root.right.left = root.left;
			root = root.right;
			//System.out.println("When the root is displaced, it is now " + root.data);
			return;
		}
		root.left.right = root.child;
		root.right.left = root.child.left;
		if(root.right!=root && root.left!=root) {
			root.child.left.right = root.right;
			root.child.left = root.left;
		}
		else {
			fixRootChildren(root.child, root.child, false, root.child);
			return;
		}
		
		//added stuff
		root.left.right = root.child;
		nullifyRootChildren(root.child, root.child, false);
		root=root.right;
	}

	private void nullifyRootChildren(Node current, Node base, boolean baseAdded) {
		current.parent=null;
		if (current.equals(base) && baseAdded || base == base.right){
			return;
		}
		nullifyRootChildren(current.right, base, true);
		
	}
	
	private void fixRootChildren(Node current, Node base, boolean baseAdded, Node min) {
		current.parent=null;
		if (current.equals(base) && baseAdded || base == base.right){
			if(current.priority<min.priority) {
				root = current;
			}
			else {
				root = min;
			}
			
			return;
		}
		if(current.priority<min.priority) {
			fixRootChildren(current.right, base, true, current);
		}
		else {
			fixRootChildren(current.right, base, true, min);
		}
		
	}

	// This is a utility method to perform merges as part of the fib heap cleanup (happens on every popMin)
	//must track size during merge
	
	private void mergeAll() {
		Object[] nodesOfDegree = new Object[(int)( Math.log(size) / Math.log(1.618)) + 1];
		mergeHelper(nodesOfDegree, root, false);
	}
	

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
	public E peek() {
		return root.data;
	}
	public int peekPriority() {
		return root.priority;
	}
	
	public Node find(E k) {
		    found = null;
		    find(k, root);
		    return found;
	}
	// Search operation
	  private void find(E key, Node c) {
	    if (found != null || c == null)
	      return;
	    else {
	      Node temp = c;
	      do {
	        if (key == temp.data)
	          found = temp;
	        else {
	          Node k = temp.child;
	          find(key, k);
	          temp = temp.right;
	        }
	      } while (temp != c && found == null);
	    }
	  }

	  public boolean decrease_key(E key, int nval) {
	    Node x = find(key);
	    return decrease_key(x, nval);
	  }

	  // Decrease key operation
	  private boolean decrease_key(Node x, int k) {
	    if (k > x.priority)
	      return false;
	    x.priority = k;
	    Node y = x.parent;
	    if (y != null && x.priority < y.priority) {
	      cut(x, y);
	      cascading_cut(y);
	    }
	    if (x.priority < root.priority) {
	    	root = x;
	    }
	    return true;
	  }

	  // Cut operation
	  private void cut(Node x, Node y) {
	    x.right.left=x.left;
	    x.left.right=x.right;

	    y.degree--;

	    x.right=null;
	    x.left=null;
	    push(x.data,x.priority);
	    x.parent=null;
	    x.loser = false;
	  }

	  private void cascading_cut(Node y) {
	    Node z = y.parent;
	    if (z != null) {
	      if (y.loser == false)
	        y.loser = true;
	      else {
	        cut(y, z);
	        cascading_cut(z);
	      }
	    }
	  }
}
