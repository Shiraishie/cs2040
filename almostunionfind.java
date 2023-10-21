import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/*
worst tha lol -- can use kattio cause it looks neater and dont have to do s!=null part
idea behind this

for findSet - lets say we have a tree 4,5,6. where 4 is the root --> if we move(4 to 1) such that 4 is pointing 1, then we make the 4 of parent array(rep array) a dummy --> so our data for 5 and 6 are stored at 4 but our data for 4 is stored at 1
1) next[5] and next[6] = 4 but next[4] = 1
2) find the representative[aka root node] of OUR POINTER aka the NEXT of I. since next[4] = 1, then root[1] = 1. next[5],next[6] = 4, root[4] = 4 --> draw out the parent array structure. the root refers to our ORIGINAL array structure(disregarding our next pointer)
our representative root node contains our data
3) everytime we run findSet -- try to flatten to reduce time complex

for unionset
1) change the REPRESENTATIVE OF EACH set -- we must do p to q because q to p exceeds time complexity for some reason
2) representative[p] = representative[q] -- we can do this via FINDSET
alter sum count etc as we like
3) Remember to do flattening -- next[p] = representative[q]

for move
1) alter only our next pointer

*/
public class almostunionfind {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        String s = br.readLine(); // reads [5,7]

        while (s != null) {
            String[] commandLine = s.split(" ");
            int N = Integer.parseInt(commandLine[0]);
            int M = Integer.parseInt(commandLine[1]);
            UFDS ufds = new UFDS(N);
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
                } else {
                    int p = Integer.parseInt(operationLine[1]);
                    pw.println(ufds.returnSize(p));
                    pw.println(ufds.returnSum(p));
                    // System.out.println(ufds.returnSize(p));
                    // System.out.println(ufds.returnSum(p));
                }
            }
            s = br.readLine();
        }
        pw.close();
        br.close();
    }
}

class UFDS {
    int[] representative;
    int[] next;
    int[] size;
    long[] sum;

    UFDS(int N) { // Creates new UFDS via array
        representative = new int[N + 1]; // find what node is pointing to WHAT
        size = new int[N + 1];
        sum = new long[N + 1];
        next = new int[N + 1]; // original array structure
        for (int i = 0; i <= N; i++) { // <= so its 1-based-indexing
            representative[i] = i;
            size[i] = 1;
            sum[i] = i;
            next[i] = i;
        }
    }

    int findRepresentative(int i) {
        while (representative[i] != i) {
            i = representative[i]; // gets root representative. which occurs when nexiI = rep[i]
        }
        return i;
    }

    // our findset has a flattening property -- try calling it everytime we can
    int findSet(int i) {
        int nextofi = next[i]; // Finds what our i is pointing to
        // int representativeofNext = findRepresentative(nextofi); // finds which array
        // structure the parent of i belongsto --> the array structure represents which
        // set itbelongs to
        // if my root of my set is pointing to someone else, we set that root as our
        // dummy, and are actual root will be pointing to a different array structure

        int representativeofnext = findRepresentative(nextofi);
        // zomgggggggggggg
        next[i] = representativeofnext; // this is a line to optimize -- similar to flattening in a ufds to make time
        // complexity faster so we dont hve to keep traversing the tree fdgsdfgsdf
        //
        return representativeofnext;
    }

    boolean isSameSet(int i, int j) {
        return (findSet(i) == findSet(j)); // does flattening for i and j
    }

    void unionSet(int i, int j) { // we should change our representatives of
        // array structure only, change
        // representative of j to i

        if (!isSameSet(i, j)) {
            int x = findSet(i); // does next[i] = rep[x]
            int y = findSet(j); // does next[j] = rep[y]
            representative[x] = y; // union of our representatives apparently need to do x to y cause y to x doesnt
                                   // work???
            next[i] = y; // optimiztion --> flattening...
            // property changeeeeeee
            next[j] = y;
            sum[y] += sum[x];
            size[y] += size[x];
            size[x] = 0;
            sum[x] = 0;

        }
    }

    void move(int i, int j) {

        if (!isSameSet(i, j)) {
            int x = findSet(i); // becomes a dummy and i points to x
            int y = findSet(j); // j points to y by flattening..

            sum[x] -= i;
            sum[y] += i;

            size[x]--;
            size[y]++;

            next[i] = y; // moving --> similar to flattening and joining
        }

    }

    int returnSize(int i) {
        return (size[findSet(i)]);
    }

    long returnSum(int i) {
        return (sum[findSet(i)]);
    }

}




kattio version
public class almostunionfind {
    public static void main(String[] args) {

        Kattio io = new Kattio(System.in, System.out);

        while (io.hasMoreTokens()) {

            int n = io.getInt();
            int m = io.getInt();
            UFDStest ufds = new UFDStest(n);

            for (int i = 0; i < m; i++) {

                int operationnum = io.getInt();

                if (operationnum == 1) {
                    int p = io.getInt();
                    int q = io.getInt();
                    ufds.unionSet(p, q);
                } else if (operationnum == 2) {
                    int p = io.getInt();
                    int q = io.getInt();
                    ufds.move(p, q);
                }
                // ops num 3
                else {
                    int p = io.getInt();
                    io.println(ufds.returnSize(p));
                    io.println(ufds.returnSum(p));
                }
            }
            // io.flush();

        }
        // io.flush();
        io.close();
    }
}
