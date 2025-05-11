package adaptator;

import graph.Graph;
import graph.Graph.Arc;
import maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Adaptateur permettant de traiter un Maze<C> comme un Graph<C> pour
 * l'algorithme de Dijkstra.
 * Les poids des arcs sont uniformément fixés à 1.
 *
 * @param <C> Type des cellules du labyrinthe.
 */
public class GraphMaze<C> implements Graph<C> {
    private final Maze<C> maze;

    /**
     * Construit un adaptateur Graph à partir d'un Maze.
     *
     * @param maze Le labyrinthe à adapter.
     */
    public GraphMaze(Maze<C> maze) {
        this.maze = maze;
    }

    /**
     * Retourne la liste des arcs sortants d'une cellule, avec un poids unitaire.
     *
     * @param s La cellule source.
     * @return Liste des arcs (valence=1, destination) accessibles depuis s.
     */
    @Override
    public List<Arc<C>> getSucc(C s) {
        Set<C> open = maze.openedNeighbours(s);
        List<Arc<C>> succ = new ArrayList<>(open.size());
        for (C n : open) {
            succ.add(new Arc<>(1, n));
        }
        return succ;
    }
}
