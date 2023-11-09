import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class LostMap {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int n = Integer.parseInt(br.readLine());

        UFDS ufds = new UFDS(n);
        PriorityQueue<Edge> pq = new PriorityQueue<>(new EdgeWeightComparator());
        boolean[][] edgeAdded = new boolean[n + 1][n + 1];

        // Set up for edges
        for (int i = 0; i < n; i++) {
            String[] edgeData = br.readLine().split(" ");

            // Making PQ with all edges that are unrepeated
            for (int j = 0; j < n; j++) {
                int source = i + 1;
                int destination = j + 1;
                if (source == destination) { // Dont need to add oneself to edge lists
                    continue;
                }
                if (!edgeAdded[i][j]) {
                    int weight = Integer.parseInt(edgeData[j]);
                    Edge edge = new Edge(source, destination, weight);
                    pq.add(edge);
                    edgeAdded[i][j] = true;
                    edgeAdded[j][i] = true;
                }
            }
        }
        //
        ArrayList<Edge> T = new ArrayList<Edge>();
        //
        while (!pq.isEmpty()) {
            Edge edgePolled = pq.poll();

            int x = ufds.findParent(edgePolled.source);
            int y = ufds.findParent(edgePolled.destination);
            if (x != y) {
                ufds.unionSet(edgePolled.source, edgePolled.destination);
                T.add(edgePolled);
            }
        }
        for (Edge z : T) {
            pw.println(z.source + "\s" + z.destination);
        }
        pw.close();
    }
}

class Edge {
    int source;
    int destination;
    int weight;

    Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

class UFDS {
    int[] parent;

    UFDS(int n) {
        this.parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i; // Set each to each parent
        }

    }

    int findParent(int source) {
        while (source != parent[source]) {
            source = parent[source];
        }
        return source;
    }

    boolean isSameSet(int a, int b) {
        return (findParent(a) == findParent(b));
    }

    void unionSet(int sourceA, int sourceB) {
        if (!isSameSet(sourceA, sourceB)) {
            int x = findParent(sourceA);
            int y = findParent(sourceB);
            parent[y] = x; // set union B into A(Owner)
            // parent[sourceB] = x; // Another Flattening
        }

    }

}

class EdgeWeightComparator implements Comparator<Edge> {
    public int compare(Edge a, Edge b) {
        if (a.weight < b.weight) {
            return -1;
        } else if (a.weight == b.weight) {
            return 0;
        } else {
            return 1;
        }
    }
}

/*
 * Do Kruskal Algorithm to make a minimum spanning tree
 * Use UFDS to keep track of parents
 * Just iterate edges that are present in MST
 */
