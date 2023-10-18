import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class joinstringswithLL {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Node[] PseudoLL = new Node[N + 1]; // Used an array because it has O(1) access time vs actual LL

        for (int i = 1; i <= N; i++) { // 1 based indexing O(N)
            String w = br.readLine();
            PseudoLL[i] = new Node(w);
        }

        int counter = 1;
        for (int i = 0; i < (N - 1); i++) {
            String[] ops = br.readLine().split(" ");
            int first = Integer.parseInt(ops[0]); // Sa
            int second = Integer.parseInt(ops[1]); // Sb

            PseudoLL[first].setNext(PseudoLL[second]);
            PseudoLL[first].setTail(PseudoLL[second]);
            counter = first;
        }

        Node fNode = PseudoLL[counter];

        while (fNode.next != null) {
            System.out.print(fNode.word);
            fNode = fNode.next;
        }
        System.out.print(fNode.word);
    }
}

class Node {
    String word;
    Node next;
    Node tail;

    Node(String word) {
        this.word = word;
        this.next = null; // Initially not pointing to anything
        this.tail = null;
    }

    public void setNext(Node next) {
        if (this.next == null) { // if not pointing to anything
            this.next = next;

        } else { // if my next points to smth, set my tail to point to next instead
            this.tail.next = next;
        }
    }

    public void setTail(Node next) {
        if (next.tail != null) {
            this.tail = next.tail; // set my tail to be the next node tail
        } else {
            this.tail = next;
        }

    }
}
