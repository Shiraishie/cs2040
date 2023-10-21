import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

//BruteForce Code

//Time complexity issue?!?!?! --> O(N) big N 
public class joinstrings {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String aN = br.readLine(); // Int
        int N = Integer.parseInt(aN); // Num of strings
        String[] output = new String[N + 1];
        for (int i = 1; i <= N; i++) {
            String name = br.readLine();
            output[i] = name;
        }
        // O(N)
        int returnindx = 1;
        while (N != 1) { // Fix this part???
            String ops = br.readLine();
            String[] opsString = ops.split(" "); // Stores value like 3 2 as 3,2 as a string
            int Sa = Integer.parseInt(opsString[0]); // Index of Sa
            int Sb = Integer.parseInt(opsString[1]); // Index of SB
            output[Sa] = output[Sa].join(output[Sb]);
            returnindx = Sa;
            N--;

        }
        // O(N)
        System.out.println(output[returnindx]);
        // System.out.println(hashtable);
    }
}

/*
 * 3241
 * 123
 * if index already exist, just add the num to the back
 * create stack??
 * 3,2 then 4,1 into different table
 * then 3,4 same first index so 3,2,4
 * then 4,1 same first index
 */
