import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class coconut {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Hand> totalhands = new ArrayList();
        String[] input = br.readLine().split(" ");
        int s = Integer.parseInt(input[0]);
        int n = Integer.parseInt(input[1]);

        int nextplayer = 0;

        for (int i = 1; i <= n; i++) {
            totalhands.add(new Hand(i, 0));
        }

        while (totalhands.size() != 1) {
            nextplayer = ((nextplayer - 1 + s) % totalhands.size()); // Index of player we stop at
            Hand nextplayerstate = totalhands.get(nextplayer);
            if (nextplayerstate.state != 2) {
                if (nextplayerstate.state == 0) { // If we hit a folded, we add a fist right beside it belonging to same
                                                  // player
                    totalhands.add(nextplayer + 1, new Hand(nextplayerstate.PlayerNumber, 1));
                } else {
                    nextplayer++;
                }
                nextplayerstate.state++;
            } else {
                totalhands.remove(nextplayerstate); // Dont change the nextplayer because we reduce list size by 1, so
                                                    // it automatically starts with the next

            }
        }
        System.out.println(totalhands.get(0).PlayerNumber);
    }
}

// P1,p2,p3 and [1,]
class Hand {
    int PlayerNumber;
    int state;

    Hand(int PlayerNumber, int State) {
        this.PlayerNumber = PlayerNumber;
        this.state = State;
    }
}