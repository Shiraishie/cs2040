import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class weakvertices {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int N = Integer.parseInt(br.readLine()); // First N

        int[][] adjMatrix = new int[N][N];
        int[] hasTriangle = new int[N]; // DAT
        // Makes my adjMatrix
        while (N != -1) {
            for (int i = 0; i < N; i++) {
                String[] line = br.readLine().split(" ");
                hasTriangle[i] = 0;
                for (int j = 0; j < N; j++) {
                    adjMatrix[i][j] = Integer.parseInt(line[j]);
                }
            }

            for (int i = 0; i < N; i++) {
                if (hasTriangle[i] == 1) {
                    continue; // Go on to next loop if its already set to 1
                }
                for (int j = 0; j < N; j++) {
                    // int a = adjMatrix[i][j]; // checks for edge (a,b) is present; //basically
                    // loops to appropriate j
                    if (adjMatrix[i][j] == 0) {
                        continue; // go to next loop if its 0, until we visit a number 1
                    }
                    for (int k = 0; k < N; k++) { // adjMatrix[j][k] checks for an edge (b,x?)
                        if (adjMatrix[i][j] == 1 && adjMatrix[j][k] == 1 && adjMatrix[k][i] == 1) { // if my
                            if (k != i && i != j) {
                                hasTriangle[k] = 1;
                                hasTriangle[j] = 1;
                                hasTriangle[i] = 1;
                                break;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < N; i++) {
                if (hasTriangle[i] == 0) {
                    pw.println(i);
                }
            }
            N = Integer.parseInt(br.readLine());
        }

        pw.close();
    }
}

/*
 * PseudoCode
 * make an adjMatrix
 * hasTriangle array[] with binary, 1 if has triangle, 0 if no triangle.
 * Initialize it to 0
 * 
 * For example
 * Loop through 1, set 1 as a, check 2 which is b and go through each, check 1
 * --> so a=b have edge, then continue going thru 2 till we hit 7, check all
 * neighbours of 7 -- if 1 not inside no triangle
 * 
 * How to write out my code
 * 1) Loop through each index using a for loop from 0 to 8 by column == checks
 * for (a,b) edge
 * 2) Nested for loop to check neighbouring index to checks for (b,c) edge
 * //will always be true
 * 3) Another nested for loop checks for (a,c)
 * 
 * If we have -- then set array[a,b,c] all as 1;
 */