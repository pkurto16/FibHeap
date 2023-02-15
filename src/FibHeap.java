public class FibHeap<E> {
	E[][] heapData; //this tracks some data E
	int[][] heapProperties; //this tracks the properties of the heap: lossCount and priority
	
	int size;
	//We are storing an extra variable instead of just repointing a pointer
	//Ask Mr. Carroll if this is fine
	int rootIndex;

	public FibHeap() {
		size = 0;
		heapData = (E[][]) new Object[0][0];
		heapProperties = new int[0][0];
		
	}

	public boolean push(E data, int priority) {
		if (heapData.length == size) {
			doubleLength();
		}
		heapData[size][0] = data;
		heapProperties[size][0] = 10 * priority;
		return true;
	}

	//Utility method to double the length of the arrays that represent the heap
	private void doubleLength() {
		E[][] newHeapData = (E[][]) new Object[heapData.length * 2 + 1][];
		int[][] newHeapProperties = new int[heapData.length * 2 + 1][];
		for (int i = 0; i < size; i++) { 
			newHeapData[i] = heapData[i];
			newHeapProperties[i] = heapProperties[i];
		}
		heapData = newHeapData;
		heapProperties = newHeapProperties;
	}

	
	//pops off the root
	public E popMin(){
		
		)
		return heapData[rootIndex][0];
	}
	
	//This is a utility method to 
	private void mergeAll(){
		
	}
	
	//decreases the key (useful for Dijkstra's)
	public void decreaseKey() {
		
	}
	//This method adjusts the heap based on if a node is a loser after a popMin
	private void shuffleHeap(){
		
	}
}
