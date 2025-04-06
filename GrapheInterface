package graph;

import java.util.List;

/**
 * Interface représentant un graphe.
 * 
 * Elle n'impose qu'une unique méthode permettant de programmer un plus court chemin.
 *
 * @param <T> Identifiant des sommets. Le type doit disposer d'une méthode hashcode.
 */
public interface Graph<T> {
	/**
	 * Un arc du graphe (désigné par sa valuation et le sommet destination).
	 *
	 * @param <T> Identifiant des sommets. Le type T doit être "hachable".
	 */
	public record Arc<T>(int val, T dst) {
	}

	/**
	 * Donne la liste des arcs sortants d'un sommet.
	 * 
	 * @param s Le sommet.
	 * @return La liste des arcs sortants de {@code s}.
	 */
	List<Arc<T>> getSucc(T s);
}