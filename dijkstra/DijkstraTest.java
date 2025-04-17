package dijkstra;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import graph.Graph;
import graph.GrapheHHAdj;
import graph.ShortestPath.Distances;
import graph.VarGraph;
import org.junit.jupiter.api.Test;

class DijkstraTest {
	private static final String FROM = "A";
	private static final String TO = "F";
	private static final int EXPECTED_DIST = 5;
	private static final List<String> EXPECTED_PATH = List.of("F", "E", "B", "D", "A"); // in pred order

	@Test
	void test() {
		VarGraph g = new GrapheHHAdj();
		g.peupler("A-B(6), A-C(1), A-D(2), B-E(1), C-E(4), D-B(1), E-F(1)");
		tester(g);
	}

	void tester(Graph g) {
		Distances<String> dst = new Dijkstra<String>().compute(g, FROM);
		assertEquals(EXPECTED_DIST, dst.dist().get(TO));
		String c = EXPECTED_PATH.get(0);
		for (String s : EXPECTED_PATH) {
			assertEquals(s, c);
			c = dst.pred().get(c);
		}
		assertNull(c);
	}
}
