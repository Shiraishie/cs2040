import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.io.*;
import java.util.*;

public class millionairemadness {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        String[] line = br.readLine().split(" ");
        int M = Integer.parseInt(line[0]);
        int N = Integer.parseInt(line[1]);
        int[][] data = new int[M][N];
        PriorityQueue<dataPoint> pq = new PriorityQueue<>(new heightComparator());
        int[][] visited = new int[M][N];
        // data grid
        for (int i = 0; i < M; i++) {
            String[] dataLine = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                data[i][j] = Integer.parseInt(dataLine[j]);
                visited[i][j] = 0;
            }
        }
        // BFS for minimax path using Prims Algorithmn
        // Direction Vector
        int[] dRow = { 0, 0, 1, -1 };
        int[] dCol = { 1, -1, 0, 0 };
        //
        dataPoint first = new dataPoint(data[0][0], 0, 0, 0);
        pq.add(first);
        int ladder = 0;
        //
        while (!pq.isEmpty()) {

            dataPoint pData = pq.poll(); // Data Point polled from Queue

            if (pData.row == M - 1 && pData.column == N - 1) {
                ladder = Math.max(pData.heightDifference, ladder);
                break;// Stop the loop once we reach end point
            }

            visited[pData.row][pData.column] = 1; // Mark as visited
            ladder = Math.max(pData.heightDifference, ladder);

            for (int i = 0; i < 4; i++) {
                // direction
                int x = pData.row + dRow[i];
                int y = pData.column + dCol[i];
                //
                if (x >= 0 && x < M && y >= 0 && y < N && visited[x][y] == 0) {
                    dataPoint nextPoint = new dataPoint(data[x][y], x, y, data[x][y] - pData.height);
                    pq.add(nextPoint);
                }
            }
        }
        pw.println(ladder);
        pw.close();

    }
}

class dataPoint {
    int height;
    int row;
    int column;
    int heightDifference;

    dataPoint(int height, int row, int column, int heightDifference) {
        this.height = height;
        this.row = row;
        this.column = column;
        this.heightDifference = heightDifference; // Height Difference from its predecessor
    }
}

class heightComparator implements Comparator<dataPoint> {
    public int compare(dataPoint a, dataPoint b) {
        if (a.heightDifference < b.heightDifference) {
            return -1;
        } else if (a.heightDifference == b.heightDifference) {
            return 0;
        } else {
            return 1;
        }
    }
}

/*
 * Use BFS to travel until we reach end vertex
 * Make a path of our vertex
 */
