import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class dominos {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int testCase = Integer.parseInt(br.readLine());
        for (int k = 0; k < testCase; k++) {
            String[] line = br.readLine().split(" ");
            int n = Integer.parseInt(line[0]);// domino num
            int m = Integer.parseInt(line[0]);// lines in test case

            // initialization
            int[] visited = new int[n + 1];
            int[] visited2 = new int[n + 1];
            LinkedList<Integer>[] adjList = new LinkedList[n + 1];
            // adjList setup
            for (int i = 1; i <= n; i++) { // 1-based indexing
                visited[i] = 0;
                visited2[i] = 0;
                adjList[i] = new LinkedList<Integer>();
            }

            for (int j = 0; j < m; j++) {
                String[] line1 = br.readLine().split(" ");
                int source = Integer.parseInt(line1[0]);
                int y = Integer.parseInt(line1[1]);
                adjList[source].add(y);
            }

            Stack<Integer> topoStack = new Stack<>();

            for (int s = 1; s <= n; s++) {
                if (visited[s] == 0) {
                    topoDFS(topoStack, s, visited, adjList); // topoDFS from 1st domino and for all
                }
            }

            LinkedList<Integer>[] tranpose = getTranspose(adjList);
            int SCC = 0; // Total number of SCC's
            Queue<Integer> q = new LinkedList<>();
            // Kosaraju Algo
            while (!topoStack.isEmpty()) {
                int v = topoStack.pop();
                if (visited2[v] == 0) {
                    DFS(v, visited2, tranpose);
                    q.add(v); // Adds all SCC's into a queue
                    SCC++;
                }
            }
            pw.println(SCC);
        }
        pw.close();

    }

    static void topoDFS(Stack<Integer> topoStack, int node, int[] visited, LinkedList<Integer>[] adjList) {
        visited[node] = 1;
        int adjListsize = adjList[node].size();
        for (int i = 0; i < adjListsize; i++) {
            int edge = adjList[node].get(i);
            if (visited[edge] == 0) {
                topoDFS(topoStack, edge, visited, adjList);
            }
        }
        topoStack.push(node);
    }

    static void DFS(int node, int[] visited, LinkedList<Integer>[] adjList) {
        visited[node] = 1;
        int adjListsize = adjList[node].size();
        for (int i = 0; i < adjListsize; i++) {
            int edge = adjList[node].get(i);
            if (visited[edge] == 0) {
                DFS(edge, visited, adjList);
            }
        }
    }

    static LinkedList<Integer>[] getTranspose(LinkedList<Integer>[] adjList) {
        int adjListSize = adjList.length;
        LinkedList<Integer>[] transpose = new LinkedList[adjListSize];
        for (int i = 1; i < adjListSize; i++) {
            transpose[i] = new LinkedList<Integer>();
        }

        for (int i = 1; i < adjListSize; i++) {
            LinkedList<Integer> check = adjList[i];
            for (int j = 0; j < check.size(); j++) {
                int y = check.get(j);
                transpose[y].add(i);
            }
        }
        return transpose;
    }
}

/*
 * Kosaraju Algorithm
 * DFS to get topoSort
 * Transpose Graph
 * DFS on modified graph according to topo
 */