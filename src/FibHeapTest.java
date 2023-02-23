import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FibHeapTest {
	static final int STRESS_TEST_NUM = 9;
//	@Test
//	void test() {
//		FibHeap<Integer> heap = new FibHeap<Integer>();
//		System.out.println("MICRO TEST:");
//		heap.push(0, 0);
//		assertEquals(0, heap.popMin());
//	}
//	
//	@Test
//	void testBiggerheap(){
//		FibHeap<Integer> heap = new FibHeap<Integer>();
//		System.out.println("\n\n\nMINI TEST:");
//		heap.push(0, 1);
//		heap.push(0, 2);
//		heap.push(0, 3);
//		heap.popMin();
//		
//	}
//	
//	@Test
//	void stressTest() {
//		System.out.println(STRESS_TEST_NUM);
//		FibHeap<Integer> heap = new FibHeap<Integer>();
//		System.out.println("\n\n\nSTRESS TEST:");
//		for(int i = 0; i < STRESS_TEST_NUM; i++) {
//			int rand = (int)(1 + Math.random() * STRESS_TEST_NUM);
//			heap.push(rand, rand);
//			System.out.println("random number is " + rand);
//			if(i % 5 == 0) {
//				//System.out.println(heap.popMin());
//			}
//		}
//		int curr = 0;
//		for(int i = 0; i< heap.size; i++) {
//			int next = heap.popMin();
//			assertTrue(next >= curr);
//			curr = next;
//		}
//	}
//	
//	@Test
//	void quickTest(){
//		FibHeap<Integer> heap = new FibHeap<Integer>();
//		System.out.println("\n\n\nQUICK TEST:");
//		heap.push(1, 1);
//		heap.push(11, 11);
//		heap.push(15, 15);
//		heap.push(6, 6);
//		heap.push(19, 19);
//		assertEquals(1, heap.popMin());
//		System.out.println("The new root is " + heap.root.data);
//		
//	}
//	@Test
//	void peterTest(){
//		FibHeap<Integer> heap = new FibHeap<Integer>();
//		System.out.println("\n\n\nPeter TEST:");
//		heap.push(8, 8);
//		assertEquals(8, heap.popMin());
//		assertEquals(0, heap.size);
//		heap.push(20, 20);
//		heap.push(13, 13);
//		heap.push(4, 4);
//		heap.push(1, 1);
//		heap.push(12, 12);
//		assertEquals(1, heap.popMin());
//		assertEquals(4, heap.size);
//		heap.push(3, 3);
//		heap.push(16, 16);
//		heap.push(10, 10);
//		heap.push(13, 13);
//		heap.push(1, 1);
//		assertEquals(1, heap.popMin());
//		assertEquals(8, heap.size);
//		heap.push(11, 11);
//		heap.push(20, 20);
//		heap.push(6, 6);
//		heap.push(12, 12);
//		heap.push(17, 17);
//		assertEquals(3, heap.popMin());
//		assertEquals(12, heap.size);
//	}
	@Test
	void finalWrongTest(){
		FibHeap<Integer> heap = new FibHeap<Integer>();
		System.out.println("\n\n\nFinal Wrong Test:");
		heap.push(3, 3);
		heap.push(5, 5);
		heap.push(4, 4);
		heap.push(2, 2);
		heap.push(4, 4);
		heap.push(3, 3);
		heap.push(2, 2);
		heap.push(5, 5);
		heap.push(9, 9);
		int curr = 0;
		for(int i = 0; i< 2;i++){//heap.size; i++) {
			int next = heap.popMin();
			assertTrue(next >= curr);
			curr = next;
		}
		for(int i = 0; i< 7;i++){//heap.size; i++) {
			int next = heap.popMin();
			assertTrue(next >= curr);
			curr = next;
		}
	}
}
