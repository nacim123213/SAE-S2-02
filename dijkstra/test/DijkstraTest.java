package dijkstra.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import graph.Graph;
import graph.GrapheHHAdj;
import graph.ShortestPath.Distances;
import graph.VarGraph;
import org.junit.jupiter.api.Test;

import dijkstra.Dijkstra;

class DijkstraTest {
	private static final String GRAPH1 = "A-B(6), A-C(1), A-D(2), B-E(1), C-E(4), D-B(1), E-F(1)";
	private static final String GRAPH_NEG = "A-B(6), A-C(1), A-D(2), B-E(-3), C-E(4), D-B(1), E-F(1)"; // B-E negatif !
	private static final String FROM = "A";
	private static final String TO = "F";
	private static final int EXPECTED_DIST = 5;
	private static final List<String> EXPECTED_PATH = List.of("F", "E", "B", "D", "A"); // in pred order
	private static final Dijkstra<String> dijkstra = new Dijkstra<>();

	@Test
	void test() {
		VarGraph g = new GrapheHHAdj();
		g.peupler(GRAPH1);
		tester(g);
	}

	void tester(Graph<String> g) {
		Distances<String> dst = dijkstra.compute(g, FROM);
		assertEquals(EXPECTED_DIST, dst.dist().get(TO));
		String c = EXPECTED_PATH.get(0);
		for (String s : EXPECTED_PATH) {
			assertEquals(s, c);
			c = dst.pred().get(c);
		}
		assertNull(c);
	}

	@Test
	void pasDeValuationNegative() {
		VarGraph g = new GrapheHHAdj();
		g.peupler(GRAPH_NEG);
		assertThrows(IllegalArgumentException.class,
				() -> dijkstra.compute(g, FROM));
	}

	@Test
	void utilisationDuResultat() {
		VarGraph g = new GrapheHHAdj();
		g.peupler(GRAPH1);
		Distances<String> dst = dijkstra.compute(g, FROM);
		System.out.println("Graphe : " + g);
		System.out.println("Distances de A : " + dst.dist());
		System.out.println("Predecesseurs : " + dst.pred());
		System.out.println("Distance de " + FROM + " à " + TO + " : " + dst.dist().get(TO));
		System.out.print("Chemin de " + FROM + " à " + TO + " : ");
		String sommet = TO;
		Deque<String> pile = new ArrayDeque<>();
		while (sommet != null) {
			pile.push(sommet);
			sommet = dst.pred().get(sommet);
		}
		while (!pile.isEmpty()) {
			System.out.print(pile.pop() + " ");
		}
		System.out.println();
	}
}
