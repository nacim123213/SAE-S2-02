package dijkstra;

import graph.Graph;
import graph.ShortestPath;
//import graph.ShortestPath.Distances;
import java.util.*;

public class Dijkstra<T> implements ShortestPath<T> {

	@Override
	public Distances<T> compute(Graph<T> g, T src, Animator<T> animator) {
		if (g == null || src == null) {
			throw new IllegalArgumentException("Le graphe ou le sommet source est null");
		}

		Map<T, Integer> dist = new HashMap<>();
		Map<T, T> pred = new HashMap<>();
		Set<T> visited = new HashSet<>();

		// 1) Initialisation
		dist.put(src, 0);
		pred.put(src, null);
		animator.accept(src, 0); // <-- distance 0 de la source

		// File à priorité minimale sur dist.get(...)
		PriorityQueue<T> queue = new PriorityQueue<>(Comparator.comparing(dist::get));
		queue.add(src);

		while (!queue.isEmpty()) {
			T u = queue.poll();
			if (visited.contains(u))
				continue;
			visited.add(u);

			// 2) Le plus court chemin vers u est désormais définitif
			int du = dist.get(u);
			animator.accept(u, du); // <-- on notifie l’animation

			// 3) Relaxation des arcs sortants
			for (Graph.Arc<T> arc : g.getSucc(u)) {
				T v = arc.dst();
				int w = arc.val();
				if (w < 0)
					throw new IllegalArgumentException("Poids négatif sur un arc");

				int nd = du + w;
				if (!dist.containsKey(v) || nd < dist.get(v)) {
					dist.put(v, nd);
					pred.put(v, u);
					// mettre à jour la priorité
					queue.remove(v);
					queue.add(v);
				}
			}
		}

		return new Distances<>(dist, pred);
	}
}
