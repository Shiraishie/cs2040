import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

public class workstation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String[] line = br.readLine().split(" ");
        Integer N = Integer.parseInt(line[0]);

        int workstationIdleTime = Integer.parseInt(line[1]);

        PriorityQueue<workers> arrivalWorkers = new PriorityQueue<workers>(N, new workerComparator());
        PriorityQueue<Integer> workstations = new PriorityQueue<Integer>();

        for (int i = 0; i < N; i++) { // Sort in increasing order
            String[] workerLine = br.readLine().split(" ");
            int arrivalTime = Integer.parseInt(workerLine[0]);
            int stayingTime = Integer.parseInt(workerLine[1]);
            arrivalWorkers.add(new workers(arrivalTime, stayingTime));
        }

        int unlock = 0;

        while (!arrivalWorkers.isEmpty()) {
            workers nextWorker = arrivalWorkers.poll();
            while (!workstations.isEmpty() && workstations.peek() + workstationIdleTime < nextWorker.getArrival()) {
                // if our grace period < our workers arrival time, we poll this off
                workstations.poll(); // Remove it since we will never use it
            }
            // else it implies that arrivaltime <= workstation GRACE PERIOD
            // end time >= my arrival time
            if (!workstations.isEmpty()) {
                if (nextWorker.getArrival() < workstations.peek()) { // if less than my peek, we need to unlock
                    unlock++;
                } else { // else we remove them
                    workstations.poll();
                }
            } else { // if empty, we unlock
                unlock++;
            }

            workstations.add(nextWorker.getEnd()); // add nextWorker instead of doing direct replacement
        }
        pw.print(N - unlock);
        pw.close();
    }

}

/*
 * Just add our worker in
 * then we check EVERY
 */
class workers {
    int arrivalTime;
    int stayingTime;

    workers(int arrivalTime, int stayingTime) {
        this.arrivalTime = arrivalTime;
        this.stayingTime = stayingTime;
    }

    int getArrival() {
        return this.arrivalTime;
    }

    int getEnd() {
        return (this.arrivalTime + this.stayingTime);
    }
}

class workerComparator implements Comparator<workers> {
    public int compare(workers s1, workers s2) {
        if (s1.getArrival() < s2.getArrival()) {
            return -1;
        }
        if (s1.getArrival() == s2.getArrival()) {
            return 0;
        } else {
            return 1;
        }

    }
}

    // //Alternative for above using Boolean -- Easier to understand


    // while (!workstations.isEmpty() && workstations.peek() + workstationIdleTime < nextWorker.getArrival()) {
    //             workstations.poll(); // Remove it since we will never use it
    //     }
    // if (!workstations.isEmpty()){
    //     boolean isBelowUpperLimit = workstations.peek() + workstationIdleTime >= nextWorker.getArrival();    
    //     boolean isAboveLowerLimit = nextWorker.getArrival()>= workstations.peek();
    //     if (isBelowUpperLimit && isAboveLowerLimit){
    //         workstations.poll();
    //     }
    //     else{ //implies my peek < nextWorker.GetArrival
    //         unlock++;
    //     }
    // } 
    // else {
    //     unlock++;
    // }

