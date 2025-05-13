package graph;

import java.util.*;

/**
 * Représentation d'un graphe orienté utilisant une liste d'adjacence.
 */
public class GrapheHHAdj implements VarGraph {

    /**
     * Map représentant les sommets du graphe et leur liste d'arcs sortants.
     * La clé est le sommet source, et la valeur est une liste d'arcs partant de ce sommet.
     */
    private final Map<String, List<Arc<String>>> adjacence = new HashMap<>();

    /**
     * Ajoute un sommet au graphe s'il n'est pas déjà présent.
     *
     * @param noeud Le nom du sommet à ajouter.
     */
    @Override
    public void ajouterSommet(String noeud) {
        adjacence.putIfAbsent(noeud, new ArrayList<>());
    }

    /**
     * Ajoute un arc orienté avec une valeur entre deux sommets du graphe.
     * Si les sommets source ou destination ne sont pas présents, ils sont créés.
     * Lève une exception si un arc identique existe déjà.
     *
     * @param source Le sommet source.
     * @param destination Le sommet destination.
     * @param valeur La valeur associée à l'arc.
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        // Assure l'existence des sommets source et destination
        ajouterSommet(source);
        ajouterSommet(destination);

        List<Arc<String>> successeurs = adjacence.get(source);
        // Vérifie l'absence de l'arc pour éviter les doublons
        for (Arc<String> arc : successeurs) {
            if (arc.dst().equals(destination)) {
                throw new IllegalArgumentException("Arc déjà présent entre " + source + " et " + destination);
            }
        }
        // Ajoute l'arc au graphe
        successeurs.add(new Arc<>(valeur, destination));
    }

    /**
     * Retourne la liste des arcs successeurs du sommet donné.
     *
     * @param s Le sommet dont on veut obtenir les successeurs.
     * @return Liste des arcs sortants du sommet, ou une liste vide si le sommet n'a aucun successeur.
     */
    @Override
    public List<Arc<String>> getSucc(String s) {
        return adjacence.getOrDefault(s, Collections.emptyList());
    }
}