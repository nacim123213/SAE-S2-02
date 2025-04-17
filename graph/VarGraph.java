package graph;

// ajouterSommet ne fait rien si un sommet est deja present
// ajouterArc leve une IllegalArgumentException si l'arc est deja present
// ajouterArc ajoute les sommets s'ils ne sont pas deja presents
// oterSommet ne fait rien si le sommet n'est pas dans le graphe
// oterArc leve une IllegalArgumentException si l'arc n'est pas present
public interface VarGraph extends Graph<String> {
	void ajouterSommet(String noeud);

	void ajouterArc(String source, String destination, Integer valeur);

	// construit un graphe vide a partir d'une chaine
	// au format "A-B(5), A-C(10), B-C(3), C-D(8), E:";
	default void peupler(String str) {
		String[] arcs = str.split(",\\s*");
		for (String arc : arcs) {
			String[] elements = arc.trim().split("-");
			// extrait le noeud source et ote ":" si nécessaire dans le nom
			String src = elements[0].replaceAll(":", "");
			ajouterSommet(src);
			if (elements.length == 1)
				continue; // pas de destination
			String target = elements[1];
			if (!target.isEmpty()) {
				String dest = target.substring(0, target.indexOf('('));
				int val = Integer.parseInt(target
						.substring(target.indexOf('(') + 1,
								target.indexOf(')')));
				ajouterSommet(dest);
				ajouterArc(src, dest, val);
			}
		}
	}
}
