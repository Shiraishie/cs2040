import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * teque
 */
public class teque {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int N = Integer.parseInt(br.readLine()); // Num of operations and potential
        // length of the Queue
        tq test = new tq();
        for (int i = 0; i < N; i++) {
            String[] commandLine = br.readLine().split(" ");
            String command = commandLine[0];
            int val = Integer.parseInt(commandLine[1]);
            if (command.equals("push_back")) {
                test.push_back(val);
            }
            if (command.equals("push_middle")) {
                test.push_middle(val);
            }
            if (command.equals("push_front")) {
                test.push_front(val);
            }
            if (command.equals("get")) {
                pw.println(test.get(val));
            }
        }
        pw.close();
    }
}

class tq {
    int[] front;
    int[] back;
    int frontEle;
    int middleEle;
    int frontsize;
    int backsize;

    tq() {
        this.front = new int[1000000];
        this.back = new int[1000000];
        this.middleEle = 500000; // should start at leftmost element available
        this.frontEle = 500000; // starts at our leftmost element[the HEAD]
        this.backsize = 0; // add this to our size to get the TAIL element
        this.frontsize = 0; // add this to our halfFront to get middle val

    }

    // O(1) since only access array and edit
    // push to the left
    void push_back(int a) { // if my back and front same size
        //
        if (frontsize == backsize) { // so this always points to our last element
            back[middleEle + backsize] = a;
            front[frontsize + frontEle] = back[middleEle];
            middleEle++; // Moves to the right
            frontsize++;
        } else {

            // backsize++; remains unchanged because we remove one from the back
            back[middleEle + backsize] = a;
            backsize++;
        }
    }

    // O(1) since only access array
    void push_front(int a) { // cases -- either same size of list or theres more at the front --> because if
                             // the back has more, then it should be automatically put into the front
        if (backsize == frontsize) { // if our list lengths are the same --> just push it to the front
            frontEle--;
            front[frontEle] = a;
            frontsize++;
        } else { // always the case where front > bac
            frontEle--;
            front[frontEle] = a; // if our front list is bigger than our back list, we remove the last element of
                                 // our frontlist and add it to backlist leftmost
            middleEle--;
            back[middleEle] = front[frontEle + frontsize];
            backsize++;

            // frontsize++;
        }
    }

    // O(1) since only access array
    void push_middle(int a) {

        if (frontsize == backsize) { // we want to keep balanced list
            front[frontEle + frontsize] = a;
            frontsize++;
            // append to middleEle of backlist
        } else {
            middleEle--;
            back[middleEle] = a;
            backsize++;

        }
    }

    public int get(int i) throws IOException {

        if (i >= frontsize) {
            return (back[i - frontsize + middleEle]);
        } else {
            return (front[frontEle + i]);
        }
    }

}

/*
 * Pseudocode
 * 1) need to be able to append to left and right(so start from middle of array
 * rather than at the front/back??)
 * 2)max operation is 10^6[1000000] size
 * 3) make sure front and back list are always balanced so its easier to add our
 * middle element
 */
// notes -- time complexity of system.out.println depends on the number of
// characters of being printed --> if we have large integer then long time
// https://stackoverflow.com/questions/21326747/system-out-println-vs-printwriter-performance-comparison
// use printwriter --> the difference in time complexity is quite big for large
// numbers