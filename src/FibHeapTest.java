import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FibHeapTest {
	static final int STRESS_TEST_NUM = 20;
	@Test
	void test() {
		FibHeap<Integer> heap = new FibHeap<Integer>();
		heap.push(0, 0);
		assertEquals(0, heap.popMin());
	}
	
	@Test
	void testBiggerheap(){
		FibHeap<Integer> heap = new FibHeap<Integer>();
		heap.push(0, 1);
		heap.push(0, 2);
		heap.push(0, 3);
		heap.popMin();
		
		
	}
	
	@Test
	void stressTest() {
		FibHeap<Integer> heap = new FibHeap<Integer>();
		
		for(int i = 0; i < STRESS_TEST_NUM; i++) {
			int rand = (int)(Math.random() * STRESS_TEST_NUM);
			heap.push(rand,rand);
			System.out.println(rand);
			if(i % 5 == 0) {
				heap.popMin();
			}
		}
		int curr = 0;
		for(int i = 0; i< heap.size; i++) {
			int next = heap.popMin();
			assertTrue(next>=curr);
			curr = next;
		}
	}
	
	@Test
	void quickTest(){
		FibHeap<Integer> heap = new FibHeap<Integer>();
		System.out.println("\n\n\nstart");
		heap.push(1, 1);
		heap.push(11, 11);
		heap.push(15, 15);
		heap.push(6, 6);
		heap.push(19, 19);
		System.out.println(heap.root.right.data);
		assertEquals(1, heap.popMin());
		
		System.out.println(heap.root.data);
		System.out.println(heap.root.left.data);
		System.out.println(heap.root.right.data);
		
	}
}