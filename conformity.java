import java.io.*;
import java.util.*;

public class conformity {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int N = Integer.parseInt(br.readLine());

        HashMap<String, Integer> hm = new HashMap<>(N);
        for (int i = 0; i < N; i++) { // O(N) Time
            String[] coursesArray = br.readLine().split(" ");
            Arrays.sort(coursesArray);
            String courses = String.join("", coursesArray); // joins our strings together
            if (!hm.containsKey(courses)) {
                hm.put(courses, 1);
            } else {
                int newVal = hm.get(courses) + 1;
                hm.put(courses, newVal);
            }
        }
        long counter = 0;
        long max = 1;
        Collection<Integer> values = hm.values();

        for (Integer x : values) {
            if (x > max) {
                max = x;
                counter = x;
            } else {
                if (x == max) {
                    counter += x;
                }
            }
        }
        pw.print(counter);
        pw.close();
    }
}
