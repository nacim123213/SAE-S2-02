package applications;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import graph.Graph;
import graph.ShortestPath.Distances;
import maze.panel.MazeView;
import maze.regular.RegularMaze;
import adaptator.GraphMaze;
import dijkstra.Dijkstra;

public class Animation {
	public static void animation(String mazeFile) {
		RegularMaze maze = null;
		try {
			maze = RegularMaze.readMaze(mazeFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("ficher '" + mazeFile + "' manquant ou au mauvais format");
			return;
		}
		Graph<Integer> graph = new GraphMaze<>(maze);
		MazeView<Integer> view = MazeView.view(maze, mazeFile);
		int start = 0;
		int end = maze.width() * maze.height() - 1;
		view.highlight(start, Color.CYAN);
		view.highlight(end, Color.PINK);
		view.repaint();
		Distances<Integer> dst = new Dijkstra<Integer>().compute(graph, start,
				(pos, dist) -> {
					view.annotate(pos, Integer.toString(dist));
					view.repaint();
					pause(100);
				});
		if (dst.dist().get(end) == null)
			System.out.println("La sortie n'est pas acessible depuis l'entree.");
		else {
			int current = end;
			while (current != start) {
				int pred = dst.pred().get(current);
				view.addPath(List.of(current, pred), Color.BLUE);
				current = pred;
			}
			view.highlight(start, Color.CYAN);
			view.highlight(end, Color.PINK);
			view.repaint();
		}
	}

	/**
	 * Lit tous les fichiers au format x.maze du repertoire bench
	 * et appelle la methode animation sur chacun d'eux
	 **/
	public static void main(String[] args) {
		if (args.length > 0)
			throw new IllegalArgumentException("aucun argument attendu");
		File benchDir = new File("bench");
		if (!benchDir.exists() || !benchDir.isDirectory()) {
			System.out.println("Le répertoire 'bench' est introuvable ou n'est pas un répertoire.");
			return;
		}
		File[] mazeFiles = benchDir.listFiles((dir, name) -> name.endsWith(".maze"));
		if (mazeFiles == null || mazeFiles.length == 0) {
			System.out.println("Aucun fichier .maze trouvé dans le répertoire 'bench'.");
			return;
		}
		Arrays.stream(mazeFiles).forEach(mazeFile -> {
			animation(mazeFile.getPath());
		});
	}

	/**
	 * * Pause l'exécution du programme pendant une durée spécifiée.
	 *
	 * @param duration la durée de la pause en millisecondes
	 */
	private static void pause(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
