import java.util.Hashtable;

/**
 * test
 */
public class test {

    public static void main(String[] args) {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        hashtable.put(-1, 3);
        hashtable.put(-2, 4);
        System.out.println(hashtable.get(-3));
        System.out.println(33 % 11);
        // System.out.println(fooing(8));
    }

    private static int fooing(int n) {
        if (n <= 0) {
            return n;
        }
        int ans = fooing(n / 2);
        while (n > 0) {
            ans = ans + n;
            n = n / 2;
        }
        return ans;
    }
}

/*
 * 
 * Queens = array
 * for i in B;
 * Queens.add(A[B[i]]) // get list of my queens
 * 
 * for j in A;
 * for k in Queens; our queens would be 20 since we have 20 domains and fixed so
 * O(1)
 * if A[j] < Queens[k]
 * hashtable.put(<value,Queen>);
 * 
 * if cannot retrieve(null) return -1
 * 
 */