import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class almostunionfind {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        String[] commandLine = br.readLine().split(" ");
        int N = Integer.parseInt(commandLine[0]);
        int M = Integer.parseInt(commandLine[1]);

        UFDS ufds = new UFDS(N); // initialize new UFDS

        for (int i = 0; i < M; i++) {
            String[] operationLine = br.readLine().split(" ");
            int operationnum = Integer.parseInt(operationLine[0]);
            if (operationnum == 1) {
                int p = Integer.parseInt(operationLine[1]);
                int q = Integer.parseInt(operationLine[2]);
                ufds.unionSet(p, q);
            } else if (operationnum == 2) {
                int p = Integer.parseInt(operationLine[1]);
                int q = Integer.parseInt(operationLine[2]);
                ufds.move(p, q);
            }
            // ops num 3
            else {
                int p = Integer.parseInt(operationLine[1]);
                pw.println(ufds.returnSize(p));
                pw.println(ufds.returnSum(p));
            }

        }

        // pw.print(index);
        pw.close();
    }
}

class UFDS {
    int[] p;
    // int[] rank;
    int[] size;
    int[] sum;

    UFDS(int N) { // Creates new UFDS via array
        p = new int[N + 1];
        // rank = new int[N + 1];
        size = new int[N + 1];
        sum = new int[N + 1]; // To save on time we make new sum array so we dont have to do linear search
        for (int i = 0; i <= N; i++) { // <= so its 1-based-indexing
            p[i] = i;
            // rank[i] = 0;
            size[i] = 1;
            sum[i] = i;
        }
    }

    int findSet(int i) { // Find root
        if (p[i] == i) {
            return i;
        } else {
            p[i] = findSet(p[i]); // recursive until it returns root
            return p[i];
        }
    }

    boolean isSameSet(int i, int j) { // explanatory name wwwww
        return (findSet(i) == findSet(j));
    }

    void unionSet(int i, int j) { // Union
        if (!isSameSet(i, j)) {
            int x = findSet(i);
            int y = findSet(j); // union y to x
            size[x] += size[y];
            size[y] = 0;
            sum[x] += sum[y];
            sum[y] = 0;
            p[y] = x;
        } else {
            // do nothing
        }

    }

    void move(int i, int j) { // moves i to SET containing J
        if (!isSameSet(i, j)) {
            int x = findSet(i); // root containing i
            size[x]--;
            sum[x] -= i;

            int y = findSet(j); // set containing j and join i to y
            p[i] = y;
            size[y]++;
            sum[y] += i;
        }
    }

    int returnSize(int i) {
        int x = findSet(i);
        return size[x];
    }

    int returnSum(int i) {
        int x = findSet(i);
        return (sum[i]);
    }

}

/*
 * new operation: move; idea: do not destroy the parent array structure; also available at UVa 11987 - Almost Union-Find from CPbook.net
 * so we need to keep parent array structure
 */
