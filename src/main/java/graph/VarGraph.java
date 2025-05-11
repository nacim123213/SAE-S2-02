package graph;

// ajouterSommet ne fait rien si un sommet est deja present
// ajouterArc leve une IllegalArgumentException si l'arc est deja present
// ajouterArc ajoute les sommets s'ils ne sont pas deja presents
public interface VarGraph extends Graph<String> {
	void ajouterSommet(String noeud);

	void ajouterArc(String source, String destination, Integer valeur);

	// construit un graphe vide a partir d'une chaine
	// au format "A-B(5), A-C(10), B-C(3), C-D(8), E:";
	default void peupler(String str) {
		String[] arcs = str.split(",\\s*");
		for (String arc : arcs) {
			arc = arc.trim();
			// Expression régulière pour extraire les parties source, destination et
			// valuation
			java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("([^-]+)-([^(]+)\\((-?\\d+)\\)")
					.matcher(arc);
			if (matcher.matches()) {
				String src = matcher.group(1).trim();
				String dest = matcher.group(2).trim();
				int val = Integer.parseInt(matcher.group(3).trim());
				ajouterSommet(src);
				ajouterSommet(dest);
				ajouterArc(src, dest, val);
			} else {
				throw new IllegalArgumentException("Format d'arc invalide : " + arc);
			}
		}
	}
}
