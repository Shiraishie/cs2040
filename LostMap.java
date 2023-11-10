import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class lostmap {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int n = Integer.parseInt(br.readLine());

        UFDS ufds = new UFDS(n);
        ArrayList<Edge> lst = new ArrayList<>();
        // Set up for edges O(N^2)
        for (int i = 0; i < n; i++) {
            String[] edgeData = br.readLine().split(" ");
            int source = i + 1;
            for (int j = 0; j < n; j++) {
                int destination = j + 1;
                if (source == destination) { // Dont need to add oneself to edge lists
                    continue;
                }
                int weight = Integer.parseInt(edgeData[j]);
                Edge edge = new Edge(source, destination, weight);
                lst.add(edge);
            }
        }
        //
        Collections.sort(lst, new EdgeWeightComparator());
        // O(N * ackerman(N))
        for (Edge e : lst) {
            if (!ufds.isSameSet(e.source, e.destination)) {
                ufds.unionSet(e.source, e.destination);
                pw.println(Integer.toString(e.source) + " " +
                        Integer.toString(e.destination));
            }
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

    int findParent(int i) {
        if (i == parent[i]) {
            return i;
        } else {
            parent[i] = findParent(parent[i]); //keep note of this line bruh
            return parent[i];
        }
    }

    boolean isSameSet(int a, int b) {
        return (findParent(a) == findParent(b));
    }

    void unionSet(int sourceA, int sourceB) {
        if (!isSameSet(sourceA, sourceB)) {
            parent[findParent(sourceB)] = findParent(sourceA);
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
 * Did a hybrid(While PQ Loop instead of for loop)
 * Kruskal starts from smallest edge weight whereas Prims -- u can start from
 * a
 * root node that u like
 * Use UFDS to keep track of parents
 * Just iterate edges that are present in MST
 *
 * // *
 * // * PseudoCode submission
 * //
 */
