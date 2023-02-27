import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class DijkstraTest {

	private static final boolean DEBUG_MODE = false;
	Random rand = new Random(0);
	int max = 15000;
	WeightedGraph testGraphStress = makeNewRandomGraph(max);
	WeightedGraph testGraph;
	WeightedGraph testGraph2;
	WeightedGraph testGraph3;
	WeightedEdge[] edges = { new WeightedEdge(1, 2, 2), new WeightedEdge(1, 4, 1), new WeightedEdge(4, 5, 1),
			new WeightedEdge(5, 9, 3), new WeightedEdge(9, 10, 4), new WeightedEdge(10, 11, 2),
			new WeightedEdge(11, 12, 4), new WeightedEdge(2, 3, 3), new WeightedEdge(3, 7, 1),
			new WeightedEdge(3, 8, 4), new WeightedEdge(8, 12, 7), new WeightedEdge(4, 6, 2), new WeightedEdge(6, 7, 1),
			new WeightedEdge(9, 7, 2), new WeightedEdge(7, 12, 8), new WeightedEdge(8, 12, 7) };
	WeightedEdge[] edges2 = { new WeightedEdge(3, 8, 4), new WeightedEdge(3, 5, 5), new WeightedEdge(5, 8, 1),
			new WeightedEdge(8, 5, 7), new WeightedEdge(5, 4, 6), new WeightedEdge(5, 9, 1), new WeightedEdge(4, 9, 3),
			new WeightedEdge(8, 10, 20), new WeightedEdge(5, 10, 8), new WeightedEdge(9, 10, 4)

	};
	WeightedEdge[] edges3 = { new WeightedEdge(1, 2, 1), new WeightedEdge(2, 1, 1), new WeightedEdge(1, 3, 300) };
	
	WeightedEdge[] edgesStress = { new WeightedEdge(1, 2, 2), new WeightedEdge(1, 3, 3), new WeightedEdge(1, 4, 1),
			new WeightedEdge(1, 5, 3), new WeightedEdge(2, 3, 1), new WeightedEdge(2, 4, 3), new WeightedEdge(2, 5, 2),
			new WeightedEdge(2, 6, 1), new WeightedEdge(3, 4, 2), new WeightedEdge(3, 5, 2), new WeightedEdge(3, 6, 2),
			new WeightedEdge(3, 7, 1), new WeightedEdge(4, 5, 1), new WeightedEdge(4, 6, 3), new WeightedEdge(4, 7, 2),
			new WeightedEdge(4, 8, 3), new WeightedEdge(5, 6, 3), new WeightedEdge(5, 7, 2), new WeightedEdge(5, 8, 1),
			new WeightedEdge(5, 9, 2), new WeightedEdge(6, 7, 1), new WeightedEdge(6, 8, 2), new WeightedEdge(6, 9, 3),
			new WeightedEdge(6, 10, 2), new WeightedEdge(7, 8, 2), new WeightedEdge(7, 9, 1),
			new WeightedEdge(7, 10, 3), new WeightedEdge(7, 11, 2), new WeightedEdge(8, 9, 2),
			new WeightedEdge(8, 10, 1), new WeightedEdge(8, 11, 3), new WeightedEdge(8, 12, 2),
			new WeightedEdge(9, 10, 3), new WeightedEdge(9, 11, 2), new WeightedEdge(9, 12, 1),
			new WeightedEdge(9, 13, 2), new WeightedEdge(10, 11, 1), new WeightedEdge(10, 12, 3),
			new WeightedEdge(10, 13, 2), new WeightedEdge(10, 14, 3), new WeightedEdge(11, 12, 2),
			new WeightedEdge(11, 13, 1), new WeightedEdge(11, 14, 2), new WeightedEdge(11, 15, 3),
			new WeightedEdge(12, 13, 3), new WeightedEdge(12, 14, 2), new WeightedEdge(12, 15, 1),
			new WeightedEdge(12, 16, 2), new WeightedEdge(13, 14, 1), new WeightedEdge(13, 15, 2),
			new WeightedEdge(13, 16, 3), new WeightedEdge(13, 17, 2), new WeightedEdge(14, 15, 3),
			new WeightedEdge(14, 16, 2), new WeightedEdge(14, 17, 1), new WeightedEdge(14, 18, 2),
			new WeightedEdge(15, 16, 1), new WeightedEdge(15, 17, 3), new WeightedEdge(15, 18, 2),
			new WeightedEdge(15, 19, 3), new WeightedEdge(16, 17, 2), new WeightedEdge(16, 18, 1),
			new WeightedEdge(16, 19, 2), new WeightedEdge(16, 20, 3), new WeightedEdge(17, 18, 3),
			new WeightedEdge(17, 19, 2), new WeightedEdge(25, 22, 3), new WeightedEdge(22, 27, 4),
			new WeightedEdge(27, 24, 2), new WeightedEdge(24, 23, 1), new WeightedEdge(23, 20, 3),
			new WeightedEdge(20, 21, 2), new WeightedEdge(21, 18, 4), new WeightedEdge(18, 19, 1),
			new WeightedEdge(19, 16, 3), new WeightedEdge(16, 17, 2), new WeightedEdge(17, 34, 4),
			new WeightedEdge(34, 31, 2), new WeightedEdge(31, 30, 1), new WeightedEdge(30, 29, 3),
			new WeightedEdge(29, 26, 2), new WeightedEdge(26, 33, 4), new WeightedEdge(33, 38, 1),
			new WeightedEdge(38, 39, 2), new WeightedEdge(39, 37, 3), new WeightedEdge(37, 36, 4),
			new WeightedEdge(36, 35, 1), new WeightedEdge(35, 32, 3), new WeightedEdge(32, 40, 2),
			new WeightedEdge(40, 28, 4), new WeightedEdge(28, 25, 1), new WeightedEdge(22, 25, 3),
			new WeightedEdge(27, 22, 4), new WeightedEdge(24, 27, 2), new WeightedEdge(23, 24, 1),
			new WeightedEdge(20, 23, 3), new WeightedEdge(21, 20, 2), new WeightedEdge(18, 21, 4),
			new WeightedEdge(19, 18, 1), new WeightedEdge(16, 19, 3), new WeightedEdge(17, 16, 2),
			new WeightedEdge(34, 17, 4), new WeightedEdge(31, 34, 2), new WeightedEdge(30, 31, 1),
			new WeightedEdge(29, 30, 3), new WeightedEdge(26, 29, 2), new WeightedEdge(33, 26, 4),
			new WeightedEdge(38, 33, 1), new WeightedEdge(39, 38, 2), new WeightedEdge(37, 39, 3),
			new WeightedEdge(36, 37, 4), new WeightedEdge(35, 36, 1), new WeightedEdge(32, 35, 3),
			new WeightedEdge(40, 32, 2), new WeightedEdge(28, 40, 4), new WeightedEdge(25, 28, 1),
			new WeightedEdge(22, 24, 3), new WeightedEdge(27, 23, 4), new WeightedEdge(24, 20, 2),
			new WeightedEdge(23, 21, 1), new WeightedEdge(20, 18, 3), new WeightedEdge(21, 19, 2),
			new WeightedEdge(18, 16, 4), new WeightedEdge(19, 17, 1), new WeightedEdge(16, 34, 3),
			new WeightedEdge(17, 31, 2), new WeightedEdge(34, 30, 4), new WeightedEdge(31, 29, 1),
			new WeightedEdge(30, 26, 3), new WeightedEdge(29, 33, 2), new WeightedEdge(26, 38, 4),
			new WeightedEdge(33, 39, 1), new WeightedEdge(38, 37, 3), new WeightedEdge(39, 36, 2),
			new WeightedEdge(36, 35, 1), new WeightedEdge(35, 32, 3), new WeightedEdge(32, 40, 2),
			new WeightedEdge(40, 28, 4), new WeightedEdge(28, 25, 1), new WeightedEdge(22, 25, 3),
			new WeightedEdge(27, 22, 4), new WeightedEdge(24, 27, 2), new WeightedEdge(23, 24, 1),
			new WeightedEdge(20, 23, 3), new WeightedEdge(21, 20, 2), new WeightedEdge(18, 21, 4),
			new WeightedEdge(19, 18, 1), new WeightedEdge(16, 19, 3), new WeightedEdge(17, 16, 2),
			new WeightedEdge(34, 17, 4), new WeightedEdge(31, 34, 2), new WeightedEdge(30, 31, 1),
			new WeightedEdge(29, 30, 3), new WeightedEdge(26, 29, 2), new WeightedEdge(33, 26, 4),
			new WeightedEdge(38, 33, 1), new WeightedEdge(39, 38, 2), new WeightedEdge(37, 39, 3),
			new WeightedEdge(36, 37, 4), new WeightedEdge(35, 36, 1), new WeightedEdge(32, 35, 3),
			new WeightedEdge(40, 32, 2), new WeightedEdge(28, 40, 4), new WeightedEdge(25, 28, 1),
			new WeightedEdge(22, 24, 3), new WeightedEdge(27, 23, 4), new WeightedEdge(24, 20, 2),
			new WeightedEdge(23, 21, 1), new WeightedEdge(20, 18, 3), new WeightedEdge(21, 19, 2),
			new WeightedEdge(18, 16, 4), new WeightedEdge(19, 17, 1), new WeightedEdge(16, 34, 3),
			new WeightedEdge(17, 31, 2), new WeightedEdge(34, 30, 4), new WeightedEdge(31, 29, 1),
			new WeightedEdge(30, 26, 3), new WeightedEdge(29, 33, 2), new WeightedEdge(26, 38, 4),
			new WeightedEdge(33, 39, 1), new WeightedEdge(38, 37, 3), new WeightedEdge(39, 36, 2),
			new WeightedEdge(37, 35, 4), new WeightedEdge(35, 40, 3), new WeightedEdge(40, 26, 2),
			new WeightedEdge(26, 19, 4), new WeightedEdge(19, 23, 3), new WeightedEdge(23, 34, 1),
			new WeightedEdge(34, 21, 2), new WeightedEdge(21, 27, 4), new WeightedEdge(27, 18, 1),
			new WeightedEdge(18, 36, 3), new WeightedEdge(36, 30, 2), new WeightedEdge(30, 39, 4),
			new WeightedEdge(39, 22, 3), new WeightedEdge(22, 37, 1), new WeightedEdge(37, 24, 2),
			new WeightedEdge(24, 31, 4), new WeightedEdge(31, 16, 3), new WeightedEdge(16, 29, 1),
			new WeightedEdge(29, 25, 2), new WeightedEdge(12, 15, 3), new WeightedEdge(15, 18, 2),
			new WeightedEdge(16, 19, 4), new WeightedEdge(17, 20, 2), new WeightedEdge(19, 22, 3),
			new WeightedEdge(20, 23, 1), new WeightedEdge(21, 24, 3), new WeightedEdge(22, 25, 4),
			new WeightedEdge(23, 26, 2), new WeightedEdge(25, 28, 2), new WeightedEdge(26, 29, 3),
			new WeightedEdge(27, 30, 1), new WeightedEdge(28, 31, 4), new WeightedEdge(29, 32, 3),
			new WeightedEdge(30, 33, 2), new WeightedEdge(32, 35, 1), new WeightedEdge(33, 36, 2),
			new WeightedEdge(34, 37, 3), new WeightedEdge(35, 38, 4), new WeightedEdge(36, 39, 2),
			new WeightedEdge(38, 40, 3), new WeightedEdge(12, 16, 1), new WeightedEdge(15, 17, 3),
			new WeightedEdge(16, 18, 2), new WeightedEdge(17, 19, 4), new WeightedEdge(18, 20, 1),
			new WeightedEdge(19, 21, 3), new WeightedEdge(20, 22, 2), new WeightedEdge(21, 23, 1),
			new WeightedEdge(22, 24, 4), new WeightedEdge(23, 25, 3), new WeightedEdge(24, 26, 1),
			new WeightedEdge(25, 27, 2), new WeightedEdge(26, 28, 4), new WeightedEdge(27, 29, 2),
			new WeightedEdge(28, 30, 1), new WeightedEdge(29, 31, 3), new WeightedEdge(30, 32, 2),
			new WeightedEdge(31, 33, 1), new WeightedEdge(32, 34, 3), new WeightedEdge(33, 35, 2),
			new WeightedEdge(34, 36, 1), new WeightedEdge(35, 37, 4), new WeightedEdge(36, 38, 3),
			new WeightedEdge(37, 39, 1), new WeightedEdge(38, 40, 2) };
	
	WeightedEdge[] edgesRandom = {

			new WeightedEdge(1, 2, 5), new WeightedEdge(1, 4, 8), new WeightedEdge(1, 10, 6), new WeightedEdge(2, 3, 2),
			new WeightedEdge(2, 7, 7), new WeightedEdge(3, 4, 9), new WeightedEdge(3, 9, 3), new WeightedEdge(3, 11, 5),
			new WeightedEdge(4, 5, 1), new WeightedEdge(4, 6, 7), new WeightedEdge(5, 10, 4), new WeightedEdge(6, 8, 2),
			new WeightedEdge(7, 8, 4), new WeightedEdge(7, 12, 7), new WeightedEdge(8, 9, 1),
			new WeightedEdge(9, 10, 3), new WeightedEdge(9, 12, 6), new WeightedEdge(10, 11, 5),
			new WeightedEdge(11, 12, 4), new WeightedEdge(13, 14, 8), new WeightedEdge(14, 15, 7),
			new WeightedEdge(15, 16, 9), new WeightedEdge(16, 17, 2), new WeightedEdge(17, 18, 6),
			new WeightedEdge(18, 19, 1), new WeightedEdge(19, 20, 3), new WeightedEdge(20, 21, 5),
			new WeightedEdge(21, 22, 4), new WeightedEdge(22, 23, 7), new WeightedEdge(23, 24, 8),
			new WeightedEdge(24, 25, 9), new WeightedEdge(25, 26, 2), new WeightedEdge(26, 27, 6),
			new WeightedEdge(27, 28, 1), new WeightedEdge(28, 29, 3), new WeightedEdge(29, 30, 5),
			new WeightedEdge(30, 31, 4), new WeightedEdge(31, 32, 7), new WeightedEdge(32, 33, 8),
			new WeightedEdge(33, 34, 9), new WeightedEdge(34, 35, 2), new WeightedEdge(35, 36, 6),
			new WeightedEdge(36, 37, 1), new WeightedEdge(37, 38, 3), new WeightedEdge(38, 39, 5),
			new WeightedEdge(39, 40, 4), new WeightedEdge(40, 41, 7), new WeightedEdge(41, 42, 8),
			new WeightedEdge(42, 43, 9), new WeightedEdge(43, 44, 13), new WeightedEdge(44, 45, 2),
			new WeightedEdge(45, 46, 6), new WeightedEdge(46, 47, 1), new WeightedEdge(47, 48, 3),
			new WeightedEdge(48, 49, 5), new WeightedEdge(49, 50, 4), new WeightedEdge(50, 51, 7),
			new WeightedEdge(51, 52, 8), new WeightedEdge(52, 53, 9), new WeightedEdge(53, 54, 2),
			new WeightedEdge(54, 55, 6), new WeightedEdge(55, 56, 1), new WeightedEdge(56, 57, 3),
			new WeightedEdge(57, 58, 5), new WeightedEdge(58, 59, 4), new WeightedEdge(59, 60, 7),
			new WeightedEdge(60, 61, 8), new WeightedEdge(61, 62, 9), new WeightedEdge(62, 63, 2),
			new WeightedEdge(63, 64, 6), new WeightedEdge(64, 65, 1), new WeightedEdge(65, 66, 3),
			new WeightedEdge(66, 67, 5), new WeightedEdge(67, 68, 4), new WeightedEdge(68, 69, 7),
			new WeightedEdge(69, 70, 8), new WeightedEdge(70, 71, 9), new WeightedEdge(71, 72, 2),
			new WeightedEdge(72, 73, 6), new WeightedEdge(73, 74, 1), new WeightedEdge(74, 75, 3),
			new WeightedEdge(75, 76, 5), new WeightedEdge(76, 77, 4), new WeightedEdge(77, 78, 7),
			new WeightedEdge(78, 79, 8), new WeightedEdge(79, 80, 9), new WeightedEdge(80, 81, 2),
			new WeightedEdge(81, 82, 6), new WeightedEdge(82, 83, 1), new WeightedEdge(83, 84, 3),
			new WeightedEdge(84, 85, 5), new WeightedEdge(85, 86, 4), new WeightedEdge(86, 87, 7),
			new WeightedEdge(87, 88, 8), new WeightedEdge(88, 89, 9), new WeightedEdge(89, 90, 2),
			new WeightedEdge(90, 91, 6), new WeightedEdge(91, 92, 1), new WeightedEdge(92, 93, 3),
			new WeightedEdge(93, 94, 5), new WeightedEdge(94, 95, 4), new WeightedEdge(95, 96, 7),
			new WeightedEdge(96, 97, 8), new WeightedEdge(97, 98, 9), new WeightedEdge(98, 99, 5),
			new WeightedEdge(99, 100, 2) };
//	@Test
//	
//	void randomTest(){
//		testGraph = new WeightedGraph(edgesRandom);
//		System.out.println(testGraph.shortestPath(1, 12));
//	}
	@Test
	void stressTest(){
		System.out.println("Path is " + testGraphStress.pathTaken(1, max));
		testGraphStress.pathTaken(1, max);
	}
	
	private WeightedGraph makeNewRandomGraph(int max) {
		WeightedGraph g = new WeightedGraph(null);
		int i = 0;
		while(i<max) {
			g.addEdge(new WeightedEdge(i,i+1,(int)((1+Math.sqrt(max)/3)*Math.random())));
			if(Math.random()>0.4) {
				g.addEdge(new WeightedEdge(i+1,i,(int)((1+Math.sqrt(max)/3)*Math.random())));
			}
			
			for(int j = 0; j<(int)(1+Math.sqrt(max)/6); j++) {
				int randIndex = i+(int)(max/50*Math.random());
				g.addEdge(new WeightedEdge(i,randIndex,(int)((1+Math.sqrt(max)/3)*Math.random())));
				if(Math.random()>0.4) {
					g.addEdge(new WeightedEdge(randIndex,i,(int)((1+Math.sqrt(max)/3)*Math.random())));
				}
			}
			i++;
		}
		return g;
	}
	
//	@Test
//	void test1to12() {
//		if (DEBUG_MODE) {
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════");
//			System.out.println("1->12 TEST");
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════\n");
//			testGraph = new WeightedGraph(edges);
//		} else {
//			testGraph = new WeightedGraph(edges);
//		}
//		testGraph3 = new WeightedGraph(edges3);
//		assertEquals(300, testGraph3.shortestPath(1, 3));
//		System.out.println(testGraph3.pathTaken(1, 3));
//		System.out.println("Test");
//
//	}
//
//	@Test
//	void testCarrollGraph() {
//		WeightedGraph testGraph1;
//		WeightedEdge[] edges1 = { new WeightedEdge(8, 7, 3), new WeightedEdge(0, 1, 1), new WeightedEdge(1, 2, 2),
//				new WeightedEdge(1, 3, 4), new WeightedEdge(3, 2, 1), new WeightedEdge(3, 7, 2),
//				new WeightedEdge(3, 4, 1), new WeightedEdge(4, 5, 2), new WeightedEdge(5, 6, 3),
//				new WeightedEdge(6, 9, 1), new WeightedEdge(3, 7, 2), new WeightedEdge(7, 9, 4) };
//		testGraph1 = new WeightedGraph(edges1);
//		System.out.println(testGraph1.pathTaken(0, 9));
//		System.out.println(testGraph1.pathTaken(1, 9));
//	}
//
//	@Test
//	void test3to12() {
//		if (DEBUG_MODE) {
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════");
//			System.out.println("3->12 TEST");
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════\n");
//			testGraph = new WeightedGraph(edges);
//		} else {
//			testGraph = new WeightedGraph(edges);
//		}
//		assertEquals(9, testGraph.shortestPath(3, 12));
//	}
//
//	@Test
//	void test9to12() {
//		if (DEBUG_MODE) {
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════");
//			System.out.println("9->12 TEST");
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════\n");
//			testGraph = new WeightedGraph(edges);
//		} else {
//			testGraph = new WeightedGraph(edges);
//		}
//		assertEquals(10, testGraph.shortestPath(9, 12));
//	}
//
//	@Test
//	void test1to7() {
//		if (DEBUG_MODE) {
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════");
//			System.out.println("1->7 TEST");
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════\n");
//			testGraph = new WeightedGraph(edges);
//		} else {
//			testGraph = new WeightedGraph(edges);
//		}
//		assertEquals(4, testGraph.shortestPath(1, 7));
//
//	}
//
//	@Test
//	void test4to7() {
//		if (DEBUG_MODE) {
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════");
//			System.out.println("4->7 TEST");
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════\n");
//			testGraph = new WeightedGraph(edges);
//		} else {
//			testGraph = new WeightedGraph(edges);
//		}
//		assertEquals(3, testGraph.shortestPath(4, 7));
//	}
//
//	@Test
//	void test1to4() {
//		if (DEBUG_MODE) {
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════");
//			System.out.println("1->4 TEST");
//			System.out.println(
//					"═══════════════════════════════════════════════════════════════════════════════════════════════════\n");
//			testGraph = new WeightedGraph(edges);
//		} else {
//			testGraph = new WeightedGraph(edges);
//		}
//		assertEquals(1, testGraph.shortestPath(1, 4));
//	}

}
