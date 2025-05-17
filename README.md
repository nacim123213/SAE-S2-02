# SAE 2.02

## 🧑‍💻 Membres du groupe

- CHERADI Nacim
- BENMOUMENE Moussa
- RAHOU Wallid
- GHANEMI AbdelHafid
- AKLOUF Imadeddine

Projet Java Maven visant à appliquer l’algorithme de Dijkstra à des graphes génériques et à des labyrinthes fournis via une bibliothèque externe.  
Le projet comprend des tests unitaires, une vérification des résultats (`Checker`) et une animation graphique (`Animation`).

---

## 📚 Contexte pédagogique

Objectifs :

- Implémentation de l’algorithme de Dijkstra sur des graphes orientés et valués
- Adaptation aux labyrinthes non orientés et non valués via un `GraphMaze`
- Comparaison automatique des résultats (`Checker`)
- Visualisation graphique (`Animation`)
- Respect de l'architecture interface/implémentation (Graph, VarGraph, ShortestPath)


---

## ⚙️ Prérequis

- **Java JDK 21** → [https://jdk.java.net/21](https://jdk.java.net/21)
- **Apache Maven 3.9.6 ou supérieur** → [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
- **Visual Studio Code** avec l’extension :
  - `"Extension Pack for Java"` par Microsoft

---

## 📦 Dépendances intégrées

- `maze.jar` (dans `lib/`) – fourni, contient les classes liées aux labyrinthes et à l’animation
- JUnit 5 – pour les tests unitaires (`DijkstraTest`)
- Plugin Maven `exec-maven-plugin` – exécution des classes `Checker` et `Animation`

---

## 📁 Structure du projet

```
📦 sae2.02/
├── pom.xml
├── lib/
│   └── maze.jar
├── bench/
│   ├── exemple.maze
│   └── exemple.dist
├── src/
│   ├── main/java/
│   │   ├── graph/
│   │   ├── dijkstra/
│   │   ├── applications/
│   │   └── adaptator/
│   └── test/java/
│       └── dijkstra/DijkstraTest.java
```

---

## ▶️ Commandes Maven

### 🧹 Nettoyage + Compilation

```bash
mvn clean compile
```

### 🧪 Tests unitaires

```bash
mvn test
```

### 🧭 Vérification des distances avec `Checker`

```bash
mvn exec:java@run-checker
```

🔹 Vérifie que tous les `.maze` de `bench/` ont un `.dist` correspondant

### 🎨 Animation graphique

```bash
mvn exec:java@run-animation
```

🔹 Affiche une visualisation des plus courts chemins dans les labyrinthes

---

## 📌 Remarques importantes

- `maze.jar` doit être présent dans `lib/` et référencé dans le `pom.xml`
- Les fichiers `.maze` et `.dist` doivent être dans le dossier `bench/`
- Le fichier `GraphMaze.java` agit comme adaptateur entre l’interface `Graph<T>` et l’interface `Maze`

---
