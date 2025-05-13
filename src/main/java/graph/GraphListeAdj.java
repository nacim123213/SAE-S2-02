package graph;

import java.util.*;

/**
 * Implémentation générique d'un graphe orienté utilisant une liste d'adjacence.
 * Chaque sommet peut être de n'importe quel type T.
 */
public class GraphListeAdj<T> implements Graph<T> {

    /**
     * Structure de données mappant chaque sommet à sa liste d'arcs sortants.
     */
    private final Map<T, List<Arc<T>>> adj;

    /**
     * Constructeur initialisant la map d'adjacence.
     */
    public GraphListeAdj() {
        adj = new HashMap<>();
    }

    /**
     * Ajoute un sommet au graphe s'il n'existe pas déjà.
     *
     * @param sommet Le sommet à ajouter.
     */
    public void ajouterSommet(T sommet) {
        adj.putIfAbsent(sommet, new ArrayList<>());
    }

    /**
     * Ajoute un arc orienté avec une valeur entre deux sommets.
     * Crée les sommets s'ils n'existent pas déjà.
     *
     * @param src Le sommet source.
     * @param dst Le sommet destination.
     * @param val La valeur associée à l'arc.
     */
    public void ajouterArc(T src, T dst, int val) {
        if (!adj.containsKey(src)) {
            ajouterSommet(src);
        }
        if (!adj.containsKey(dst)) {
            ajouterSommet(dst);
        }
        adj.get(src).add(new Arc<>(val, dst));
    }

    /**
     * Retourne la liste des arcs sortants du sommet donné.
     *
     * @param s Le sommet pour lequel on veut les successeurs.
     * @return Liste des arcs sortants, ou une liste vide si aucun.
     */
    @Override
    public List<Arc<T>> getSucc(T s) {
        return adj.getOrDefault(s, List.of());
    }
}