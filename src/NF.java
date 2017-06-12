import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;

import java.util.*;

public class NF {
    private int graph[][];
    private int V;
    private int OverallFlow;

    public NF(int graph[][], int V) {
        OverallFlow = 0;
        this.graph = graph;
        this.V = V;

    }

    public static void main(String[] args) {
        ReaderWriter reader = new ReaderWriter("/Users/Pati/Downloads/algdes-labs-master/flow/data/rail.txt");
        NF nf = new NF(reader.getGraphMatrix(), reader.getN());
        nf.NetworkFlow(reader);
    }

    public void NetworkFlow(ReaderWriter reader) {

//        Graph graph = reader.getGraph();
        int source = reader.getSource();
        int sink = reader.getSink();
//        Map<String, Integer> edges = reader.getEdges();
        Map<String, Integer> edgesCopy = reader.getEdges();
//        Map<String, Integer> minCut = new HashMap<>();
        Map<String, Integer> vertices = reader.getVertices();

        int graph[][] = reader.getGraphMatrix();
        int residualGraph[][] = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                residualGraph[i][j] = graph[j][i];
            }
        }

//        Graph residualGraph = reader.getGraph();
//        BreadthFirstPaths bfs = new BreadthFirstPaths(residualGraph, source);
        int path[] = new int[V];
        //boolean visited[] = new boolean[V];

        while (bfs(residualGraph, source, sink, path)) {
            int minCapacity = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = path[v]) {
                int u = path[v];
                minCapacity = Math.min(minCapacity, residualGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (int v = sink; v != source; v = path[v]) {
                int u = path[v];
                residualGraph[u][v] -= minCapacity;
                residualGraph[v][u] += minCapacity;
            }

            // Add path flow to overall flow
            OverallFlow += minCapacity;
        }



//        List<String> e = new ArrayList<>();
//
//        while (bfs.hasPathTo(sink)) {
//            int minCapacity = Integer.MAX_VALUE;
//            String path[];
//            e.clear();
//
//            path = bfs.pathTo(sink).toString().split(" ");
//
//            String u = path[0];
//            for (int i = 1; i < path.length; i++) {
//                String v = path[i];
//                e.add(String.join(" ", u, v));
//                u = v;
//            }
////            String minC = null;
//
//            for (int i = 0; i < e.size(); i++) {
//                int capacity = edgesCopy.get(e.get(i));
//                if (capacity < minCapacity) {
//                    minCapacity = capacity;
////                    minC = e.get(i);
//                }
//            }
////            minCut.put(minC, edgesCopy.get(minC));
//
//            residualGraph = new Graph(reader.getN());
//
//            for (int i = 0; i < e.size(); i++) {
//                String bla[] = e.get(i).split(" ");
//                String reverse = String.join(" ", bla[1], bla[0]);
//
//                //int newCapacity = edgesCopy.get(e.get(i)) - minCapacity;
//                edgesCopy.replace(e.get(i), edgesCopy.get(e.get(i)) - minCapacity);
//                edgesCopy.replace(reverse, edgesCopy.get(e.get(i)) + minCapacity);
//
//            }
//            for (String key : edgesCopy.keySet()) {
//                String e2[];
//                e2 = key.split(" ");
//                if (edgesCopy.get(key) != 0) {
//                    residualGraph.addEdge(Integer.parseInt(e2[0]), Integer.parseInt(e2[1]));
//                }
//            }
//            bfs = new BreadthFirstPaths(residualGraph, source);

//            OverallFlow = OverallFlow + minCapacity;
//
//
//            System.out.println(bfs.pathTo(sink) + "minimum capacity= " + minCapacity);


        System.out.println("Overall flow= " + OverallFlow);

//        Set<Integer> reachable = new HashSet<>();
//
//        for (String v : vertices.keySet()) {
//            if (bfs.hasPathTo(vertices.get(v))){
//                reachable.add(vertices.get(v));
//            }
//        }
//
//        bfs = new BreadthFirstPaths(residualGraph, sink);
//
//        for (String v : vertices.keySet()){
//            if (!bfs.hasPathTo(vertices.get(v))){
//                reachable.add(vertices.get(v));
//
//            }
//        }
//        System.out.println(reachable);


//        int x = 0;
//
//        for (String s : minCut.keySet()) {
//            x = x + minCut.get(s);
//            System.out.println(s + " " + minCut.get(s));
//
//        }
//        System.out.println(x);

        boolean visited[V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (visited[i] == true && visited[j] == false && graph[i][j] != 0) {
                    System.out.println(i + " " + j + " " + graph[i][j]);


                }
            }
        }
    }

    boolean bfs(int residualGraph[][], int s, int t, int parent[]) {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && residualGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return visited[t] == true;
    }

    void dfs(int residualGraph[][], int s, boolean visited[]) {
        visited[s] = true;
        for (int i = 0; i < V; i++) {
            if (residualGraph[s][i] != 0 && !visited[i]) {
                dfs(residualGraph, i, visited);
            }
        }
    }
}
