//Note: Discussed with Max and Peter about the most efficient way to write the percolate method
public class PriorityQueue<E>{
	int size;
	E[] heapData;
	int[] heapPriority;
	
	public PriorityQueue(){
		heapData = (E[]) new Object[1];
		heapPriority = new int[1];
	}
	
	//Utility method that adjusts the data and priority arrays at a given index
	private void setTwoArrays(E data, int priority, int index) {
		heapData[index] = data;
		heapPriority[index] = priority;
	}
	
	//Utility method that swaps the elements at two indices in both arrays
	private void swapIndices(int index1, int index2) {
		E data = heapData[index1];
		int priority = heapPriority[index1];
		setTwoArrays(heapData[index2], heapPriority[index2], index1);
		setTwoArrays(data, priority, index2);
	}
	
	//Utility method used for testing
	protected int[] getPriorityList() {
		return heapPriority;
	}
	
	//Pushing a new element onto the heap
	public boolean push(E data, int priority) {
		orderedAdd(data, priority);
		heapify(data, priority);
		return true;
	}
	
	//Adds the next element in order of the ArrayList, before heapifying
	private void orderedAdd(E data, int priority) {
		if (size == heapData.length && size == heapPriority.length) {
			increaseLength();
		}
		setTwoArrays(data, priority, size);
		size++;
	}

	private void heapify(E data, int priority) {
		heapify(size - 1, data, priority);
	}
	
	//Heapify helper method
	private void heapify(int index, E data, int priority) {
		int parentIndex = (index - 1) / 2;
		if (heapPriority[index] >= heapPriority[parentIndex] || index == 0) return; 
		
		swapIndices(parentIndex, index);		
		heapify(parentIndex, heapData[parentIndex], heapPriority[parentIndex]);
	}
	
	private void increaseLength() {
		E[] newDataArray = (E[]) new Object[heapData.length * 2 + 1];
		int[] newPriorityArray = new int[heapPriority.length * 2 + 1];
		for (int i = 0; i < size; i++) { 
			newDataArray[i] = heapData[i];
			newPriorityArray[i] = heapPriority[i];
		}
		heapData = newDataArray;
		heapPriority = newPriorityArray;
	}
	
	//returns the data of the root
	public E peek() {
		if (size == 0) return null;
		return heapData[0];
	}
	
	public int peekPriority() {
		if (size == 0) return -1;
		return heapPriority[0];
	}
	
	public E poll() {
		if (size == 0) return null;
		E deletedData = heapData[0];
		replaceRoot();
		percolate(0);
		return deletedData;
	}
	
	//replaces the root with the bottom-most element
	private void replaceRoot() {
		setTwoArrays(heapData[size - 1], heapPriority[size - 1], 0);
		size--;
	}
	
	private void percolate(int index) {
		int minIndex = index;
		//Finding the smallest index among the parent and its l/r children
		//We short-circuit here in case the left or right children to not exist
		if (2 * index + 1 <= size - 1 && heapPriority[2 * index + 1] < heapPriority[minIndex]) minIndex = 2 * index + 1;
		if (2 * index + 2 <= size - 1 && heapPriority[2 * index + 2] < heapPriority[minIndex]) minIndex = 2 * index + 2;
		//we want to swap the parent with the min to keep the structure of the heap
		if (minIndex != index) {
			swapIndices(index, minIndex);
			percolate(minIndex);
		}
	}
	
	public static int[] heapSort(int[] nums) {
		PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>();
		int[] sortedArray = new int[nums.length];
		for (int num : nums) {
			pQueue.push(num, num);
		}
		for (int i = 0; i < nums.length; i++) {
			sortedArray[i] = pQueue.poll();
		}
		return sortedArray;
	}
}