import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class islands3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        String[] rc = br.readLine().split(" ");
        int r = Integer.parseInt(rc[0]);
        int c = Integer.parseInt(rc[1]);
        char[][] data = new char[r][c];
        int[][] visited = new int[r][c];
        Queue<Cell> queue = new LinkedList<>();
        //
        for (int i = 0; i < r; i++) {
            String line = br.readLine();
            for (int j = 0; j < c; j++) {
                data[i][j] = line.charAt(j);
                visited[i][j] = 0;
            }
        }
        // Direction Vectors
        int[] dRow = { 0, 0, -1, 1 };
        int[] dCol = { 1, -1, 0, 0 };
        //
        int count = 0;
        //
        for (int i = 0; i < r; i++) { // visits all cells in a grid,
            for (int j = 0; j < c; j++) {
                if (data[i][j] == 'W' && visited[i][j] == 0) {
                    visited[i][j] = 1;
                    continue;
                }
                if (data[i][j] == 'L' && visited[i][j] == 0) { // Cell is 'L' and unvisited, add to queue
                    Cell cell = new Cell(i, j, data[i][j]);
                    queue.add(cell);
                    count++;
                    visited[i][j] = 1;
                }
                // initialize BFS when we hit 'L'
                while (!queue.isEmpty()) {
                    Cell pop = queue.poll();
                    int cr = pop.r; // r value
                    int cc = pop.c; // c value
                    if (pop.letter == 'W') { // Continue while loop(no further search since its W)
                        continue;
                    }
                    for (int k = 0; k < 4; k++) {
                        int nr = cr + dRow[k];
                        int nc = cc + dCol[k];
                        if (nr >= 0 && nr < r && nc >= 0 && nc < c && visited[nr][nc] == 0) { // check if cell is valid
                                                                                              // and unvisited
                            visited[nr][nc] = 1;
                            queue.add(new Cell(nr, nc, data[nr][nc]));
                        }
                    }
                }
            }
        }
        pw.println(count);
        pw.close();
    }
}

class Cell {
    int r;
    int c;
    char letter;

    Cell(int r, int c, char letter) {
        this.r = r;
        this.c = c;
        this.letter = letter;
    }
}
