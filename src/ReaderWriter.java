import edu.princeton.cs.algs4.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReaderWriter {
    private Scanner sc;
    private int n;
    private String sourceName;
    private int source;
    private String sinkName;
    private int sink;
    private Map<String, Integer> edges = new HashMap<>();
    private Map<String, Integer> vertices = new HashMap<>();

    private Graph graph;
    private int GraphMatrix[][];

    public ReaderWriter(String filePath) {
        try {
            sc = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find the file specified. Exiting...");
            System.exit(0);
        }

        initialize();
    }

    public int getN() {
        return n;
    }

    public Graph getGraph() {
        return graph;
    }

    public int[][] getGraphMatrix() {
        return GraphMatrix;
    }

    public int getSource() {
        return source;
    }

    public int getSink() {
        return sink;
    }

    public Map<String, Integer> getEdges() {
        return edges;
    }

    public Map<String, Integer> getVertices() {
        return vertices;
    }

    private void initialize() {
        n = sc.nextInt();
        GraphMatrix = new int [n][n];

//        graph = new Graph(n);
        sc.nextLine();


        for (int i = 0; i < n; i++) {
            if (i == 0) {
                source = i;
                sourceName = sc.next();
                vertices.put(sourceName, i);
            } else if (i == n - 1) {
                sink = i;
                sinkName = sc.next();
                vertices.put(sinkName, i);
            } else {
                vertices.put(sc.next(), i);
            }
        }

        sc.nextInt();

        while (sc.hasNext()) {

            int u = sc.nextInt();
            int v = sc.nextInt();
            int capacity = sc.nextInt();

            if (capacity == -1) {
                capacity = Integer.MAX_VALUE;
            }

            GraphMatrix[u][v] = capacity;
            GraphMatrix[v][u] = capacity;

//            String edge1 = String.join(" ", Integer.toString(u), Integer.toString(v));
//            String edge2 = String.join(" ", Integer.toString(v), Integer.toString(u));
//
//            edges.put(edge1, capacity);
//            edges.put(edge2, capacity);
//
//
//            graph.addEdge(u, v);
//            graph.addEdge(v, u);

        }
    }
}
