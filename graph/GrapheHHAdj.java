package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements VarGraph {
    // Structure de liste d'adjacence : sommet -> liste des arcs sortants
    private final Map<String, List<Arc<String>>> adjList = new HashMap<>();

    @Override
    public List<Arc<String>> getSucc(String s) {
        return adjList.getOrDefault(s, new ArrayList<>());
    }

    @Override
    public void ajouterSommet(String noeud) {
        // Ajoute un sommet s'il n'existe pas déjà
        adjList.putIfAbsent(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        // Ajoute les sommets s'ils n'existent pas
        ajouterSommet(source);
        ajouterSommet(destination);

        // Vérifie si l'arc existe déjà
        List<Arc<String>> arcs = adjList.get(source);
        for (Arc<String> arc : arcs) {
            if (arc.dst().equals(destination)) {
                throw new IllegalArgumentException("Arc déjà présent : " + source + " -> " + destination);
            }
        }

        // Ajoute l'arc
        arcs.add(new Arc<>(valeur, destination));
    }
}