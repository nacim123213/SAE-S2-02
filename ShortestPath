package graph;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

@FunctionalInterface
public interface ShortestPath<T> {
	/**
	 * Permet de personnaliser l'animation de l'algorithme.
	 * 
	 * @param <T> Identifiant des sommets. Le type doit être "hachable".
	 */
	@FunctionalInterface
	public static interface Animator<T> {
		/**
		 * Méthode invoquée chaque fois qu'une distance d'un sommet au point de départ
		 * est
		 * définitivement connue.
		 * 
		 * @param s    Le sommet.
		 * @param dist Sa distance au point de départ.
		 */
		void accept(T s, int dist);
	}

	/**
	 * Résultat d'un calcul de plus court chemin (à partir d'un sommet {@code src}).
	 * 
	 * {@code dist} associe à chaque sommet accessible, la distance minimale qui le
	 * sépare de {@code src}. {@code pred} associe à chaque sommet accessible
	 * {@code s}, le sommet qui le précède dans un des chemins de longueur minimale
	 * partant de {@code src} et conduisant à {@code s}.
	 * 
	 * Les sommets non accessibles à partir de {@code src} sont absents de
	 * {@code dist} et {@code pred}. {@code src} est associée à 0 dans {@code dist},
	 * et à {@code null} dans {@code pred}.
	 * 
	 * @param <T> Identifiant des sommets. Le type T doit être "hachable".
	 */
	public record Distances<T>(Map<T, Integer> dist, Map<T, T> pred) implements Serializable {

		public static <T> void writeDist(Distances<T> dist, String name) throws IOException {
			try (FileOutputStream f = new FileOutputStream(name);
					ObjectOutputStream out = new ObjectOutputStream(f)) {
				out.writeObject(dist);
				out.flush();
			}
		}

		@SuppressWarnings("unchecked")
		public static Distances<Integer> readDist(String name) throws IOException, ClassNotFoundException {
			try (FileInputStream f = new FileInputStream(name);
					ObjectInputStream in = new ObjectInputStream(f)) {
				return (Distances<Integer>) in.readObject();
			}
		}
	}

	/**
	 * Calcul des plus courts chemins.
	 * 
	 * @param <T>      Identifiant des sommets. Le type doit être "hachable".
	 * @param g        Le graphe pour lequel le calcul est demandé.
	 * @param src      Le sommet de {@code g} à partir duquel les plus courts
	 *                 chemins
	 *                 sont demandés.
	 * @param animator L'animateur du parcours. Il est invoqué chaque fois qu'une
	 *                 distance est connue.
	 * @return Une instance de {@code Resultat<T>} contenant tous les résultats.
	 */
	Distances<T> compute(Graph<T> g, T src, Animator<T> animator);

	/**
	 * Calcul des plus courts chemins sans animation.
	 */
	default Distances<T> compute(Graph<T> g, T src) {
		return compute(g, src, (n, d) -> {
		});
	}
}
