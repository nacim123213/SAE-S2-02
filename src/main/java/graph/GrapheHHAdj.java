package graph;

import java.util.*;

public class GrapheHHAdj implements VarGraph {

    private final Map<String, List<Arc<String>>> adjacence = new HashMap<>();

    @Override
    public void ajouterSommet(String noeud) {
        adjacence.putIfAbsent(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        ajouterSommet(source);
        ajouterSommet(destination);

        List<Arc<String>> successeurs = adjacence.get(source);
        for (Arc<String> arc : successeurs) {
            if (arc.dst().equals(destination)) {
                throw new IllegalArgumentException("Arc déjà présent entre " + source + " et " + destination);
            }
        }
        successeurs.add(new Arc<>(valeur, destination));
    }

    @Override
    public List<Arc<String>> getSucc(String s) {
        return adjacence.getOrDefault(s, Collections.emptyList());
    }
}
