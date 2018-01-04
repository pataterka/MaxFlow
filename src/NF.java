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

        int source = reader.getSource();
        int sink = reader.getSink();
        Map<String, Integer> edgesCopy = reader.getEdges();
        Map<String, Integer> vertices = reader.getVertices();

        int graph[][] = reader.getGraphMatrix();
        int residualGraph[][] = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                residualGraph[i][j] = graph[j][i];
            }
        }

        int path[] = new int[V];

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





        System.out.println("Overall flow= " + OverallFlow);


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
