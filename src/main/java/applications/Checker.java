package applications;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import graph.Graph;
import graph.ShortestPath.Distances;
import maze.regular.RegularMaze;
import adaptator.GraphMaze;
import dijkstra.Dijkstra;

public class Checker {
	/**
	 * Lis tous les fichiers au format x.maze et x.dist du repertoire bench
	 * et appelle la methode check sur chacun d'eux
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
			String baseName = mazeFile.getName().replace(".maze", "");
			File distFile = new File(benchDir, baseName + ".dist");
			if (distFile.exists()) {
				check(mazeFile.getPath(), distFile.getPath());
			} else {
				System.out.println("Fichier .dist correspondant introuvable pour : " + mazeFile.getName());
			}
		});

	}

	private static boolean checkPred(RegularMaze maze, Distances<Integer> expected, Distances<Integer> computed) {
		for (int i = 0; i < maze.height() * maze.width(); ++i) {
			Integer pred = computed.pred().get(i);
			if (pred == null) {
				if (expected.pred().get(i) != null)
					return false;
			} else if (computed.dist().get(i) != expected.dist().get(pred) + 1)
				return false;
		}
		return true;
	}

	/**
	 * Vérifie la correction d'un algorithme de calcul de plus court chemin
	 * en comparant le resultat obtenu avec le resultat attendu
	 *
	 * @param mazeFile le nom du fichier contenant le labyrinthe
	 *                 (au format nom.maze)
	 * @param distFile le nom du fichier contenant les distances attendues
	 *                 (au format nom.dist)
	 **/

	public static void check(String mazeFile, String distFile) {

		RegularMaze maze = null;
		try {
			maze = RegularMaze.readMaze(mazeFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("ficher '" + mazeFile + "' manquant ou au mauvais format");
			return;
		}
		Distances<Integer> expectedDist = null;
		try {
			expectedDist = Distances.readDist(distFile);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("fichier '" + distFile + "' manquant ou au mauvais format");
			return;
		}
		Graph<Integer> graph = new GraphMaze<>(maze);
		Distances<Integer> dst = new Dijkstra<Integer>().compute(graph, maze.start());
		if (!dst.dist().equals(expectedDist.dist()) || !checkPred(maze, expectedDist, dst))
			System.out.println("echec" + " : " + mazeFile + " et " + distFile);
		else
			System.out.println("succes" + " : " + mazeFile + " et " + distFile);
	}
}
