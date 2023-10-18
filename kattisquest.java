import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class kattisquest {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int N = Integer.parseInt(br.readLine());

        TreeMap<Long, PriorityQueue<Long>> map = new TreeMap<>();

        for (int i = 0; i < N; i++) { // Add first
            String[] commandLine = br.readLine().split(" ");
            String command = commandLine[0];

            if (command.equals("add")) {
                long energy = Integer.parseInt(commandLine[1]);
                long gold = Integer.parseInt(commandLine[2]);
                if (map.containsKey(energy)) { // if contains key, add to my pq
                    map.get(energy).add(gold);
                } else { // else create new pq with maxheap property
                    PriorityQueue<Long> pq = new PriorityQueue<Long>(Collections.reverseOrder(null));
                    pq.add(gold);
                    map.put(energy, pq);
                }

            } else if (command.equals("query")) {
                long query = Integer.parseInt(commandLine[1]); // Query integer given
                long earnedGold = 0;

                while (map.floorEntry(query) != null) {
                    long goldFromQuest = map.floorEntry(query).getValue().poll(); // pools
                    long energyLost = map.floorKey(query);
                    earnedGold += goldFromQuest;
                    if (map.floorEntry(query).getValue().size() == 0) {
                        map.remove(map.floorKey(query));
                    }
                    query -= energyLost;
                    // map.remove(map.floorKey(query));
                }
                pw.println(earnedGold);
            }

        }
        // long test = 7;
        // long tester = map.floorEntry(test).getValue().poll();
        // if (map.floorEntry(test).getValue().size() == 0) {
        // pw.print('k');
        // map.remove(map.floorEntry(test));
        // } else {
        // pw.print('n');
        // }

        pw.close();
    }
}

/*
/*
 * PseudoCode
 * Use a Treemap with <Integer, PQ> where Integer represents the energy and PQ
 * represents the energy in descending over --> Can be done with
 * collections.reverseorder
 * When we query --> Use FloorEntry to get Integer, PQ value --> Gold earned is
 * polled from our PQ(Since highest $$ is polled)
 * QueryEnergy - EnergyUsed and do a while loop until our query returns null
 */
*/

Cleaner Code below    
public class kattisquest {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int N = Integer.parseInt(br.readLine());

        TreeMap<Long, PriorityQueue<Long>> map = new TreeMap<>();

        for (int i = 0; i < N; i++) { // Add first
            String[] commandLine = br.readLine().split(" ");
            String command = commandLine[0];

            if (command.equals("add")) {

                long energy = Integer.parseInt(commandLine[1]);
                long gold = Integer.parseInt(commandLine[2]);

                if (map.containsKey(energy)) {
                    map.get(energy).add(gold); // adds to pq if key found
                }

                else {
                    PriorityQueue<Long> pq = new PriorityQueue<Long>(Collections.reverseOrder(null)); // pq with maxheap
                                                                                                      // property
                    pq.add(gold);
                    map.put(energy, pq);
                }

            }

            else if (command.equals("query")) {

                long query = Integer.parseInt(commandLine[1]); // Query integer given
                long earnedGold = 0;
                Entry<Long, PriorityQueue<Long>> entry = map.floorEntry(query);
                while (entry != null) {
                    long goldFromQuest = entry.getValue().poll(); // pools
                    long energyLost = entry.getKey();
                    earnedGold += goldFromQuest;

                    if (entry.getValue().size() == 0) {
                        map.remove(entry.getKey());
                    }

                    query -= energyLost;
                    entry = map.floorEntry(query);

                }
                pw.println(earnedGold);
            }
        }
        pw.close();
    }
}
