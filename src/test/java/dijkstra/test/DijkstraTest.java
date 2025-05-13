package dijkstra.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import graph.Graph;
import graph.GrapheHHAdj;
import graph.ShortestPath.Distances;
import graph.VarGraph;
import org.junit.jupiter.api.Test;

import dijkstra.Dijkstra;

/**
 * Classe de test pour l'algorithme de Dijkstra.
 */
class DijkstraTest {

    // Représentation textuelle d'un graphe sans poids négatif
    private static final String GRAPH1 = "A-B(6), A-C(1), A-D(2), B-E(1), C-E(4), D-B(1), E-F(1)";
    // Graphe avec un arc à poids négatif (B-E)
    private static final String GRAPH_NEG = "A-B(6), A-C(1), A-D(2), B-E(-3), C-E(4), D-B(1), E-F(1)";
    
    private static final String FROM = "A";
    private static final String TO = "F";
    private static final int EXPECTED_DIST = 5;
    // Chemin attendu sous forme de prédécesseurs
    private static final List<String> EXPECTED_PATH = List.of("F", "E", "B", "D", "A");

    private static final Dijkstra<String> dijkstra = new Dijkstra<>();

    /**
     * Test principal : création d'un graphe valide et vérification du résultat.
     */
    @Test
    void test() {
        VarGraph g = new GrapheHHAdj();
        g.peupler(GRAPH1);
        tester(g);
    }

    /**
     * Teste la validité des distances et prédécesseurs retournés par Dijkstra.
     *
     * @param g Le graphe sur lequel effectuer le test.
     */
    void tester(Graph<String> g) {
        Distances<String> dst = dijkstra.compute(g, FROM);
        // Vérifie que la distance minimale trouvée est correcte
        assertEquals(EXPECTED_DIST, dst.dist().get(TO));

        // Vérifie que les prédécesseurs reconstituent le bon chemin
        String c = EXPECTED_PATH.get(0);
        for (String s : EXPECTED_PATH) {
            assertEquals(s, c);
            c = dst.pred().get(c);
        }
        assertNull(c); // fin du chemin
    }

    /**
     * Vérifie que l'algorithme lève une exception en présence d'un arc à poids négatif.
     */
    @Test
    void pasDeValuationNegative() {
        VarGraph g = new GrapheHHAdj();
        g.peupler(GRAPH_NEG);
        assertThrows(IllegalArgumentException.class,
                () -> dijkstra.compute(g, FROM));
    }

    /**
     * Exemple d'utilisation de l'algorithme de Dijkstra avec affichage du résultat.
     */
    @Test
    void utilisationDuResultat() {
        VarGraph g = new GrapheHHAdj();
        g.peupler(GRAPH1);
        Distances<String> dst = dijkstra.compute(g, FROM);

        // Affiche les informations utiles
        System.out.println("Graphe : " + g);
        System.out.println("Distances de A : " + dst.dist());
        System.out.println("Predecesseurs : " + dst.pred());
        System.out.println("Distance de " + FROM + " à " + TO + " : " + dst.dist().get(TO));

        // Reconstruit et affiche le chemin de FROM à TO
        System.out.print("Chemin de " + FROM + " à " + TO + " : ");
        String sommet = TO;
        Deque<String> pile = new ArrayDeque<>();
        while (sommet != null) {
            pile.push(sommet);
            sommet = dst.pred().get(sommet);
        }
        while (!pile.isEmpty()) {
            System.out.print(pile.pop() + " ");
        }
        System.out.println();
    }
}