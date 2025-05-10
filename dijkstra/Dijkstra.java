package dijkstra;

import graph.Graph;
import graph.Graph.Arc;
import graph.ShortestPath;
//import graph.ShortestPath.Distances;
import java.util.*;

public class Dijkstra<T> implements ShortestPath<T> {

	@Override
	public Distances<T> compute(Graph<T> g, T src, Animator<T> animator) throws IllegalArgumentException {
		if (g == null || src == null) {
			throw new IllegalArgumentException("Le graphe ou le sommet source est null");
		}

		Map<T, Integer> dist = new HashMap<>(); // distances minimales depuis src
		Map<T, T> pred = new HashMap<>(); // prédécesseurs sur le chemin le plus court
		PriorityQueue<T> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get)); // file de priorité

		dist.put(src, 0);
		pred.put(src, null);
		queue.add(src);

		while (!queue.isEmpty()) {
			T u = queue.poll();
			int d = dist.get(u);
			animator.accept(u, d); // notifier l'animateur

			for (Arc<T> arc : g.getSucc(u)) {
				T v = arc.dst();
				int poids = arc.val();
				int nvDist = d + poids;

				if (!dist.containsKey(v) || nvDist < dist.get(v)) {
					dist.put(v, nvDist);
					pred.put(v, u);
					queue.remove(v); // enlever l'ancien (si présent)
					queue.add(v); // ajouter avec la nouvelle priorité
				}
			}
		}

		return new Distances<>(dist, pred);
	}
}
