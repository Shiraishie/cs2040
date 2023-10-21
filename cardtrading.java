import java.io.*;
import java.util.*;

//Tolentino

public class cardtrading {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] aN = br.readLine().split(" ");
        int N = Integer.parseInt(aN[0]);
        int T = Integer.parseInt(aN[1]);
        int K = Integer.parseInt(aN[2]);

        int[] arr = new int[100001]; // array access is o1
        String[] numtoadd = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            int indx = Integer.parseInt(numtoadd[i]);
            arr[indx]++;
        }

        ArrayList<card> cardList = new ArrayList<card>();
        for (int i = 1; i < T + 1; i++) {
            String[] BuyandSell = br.readLine().split(" ");
            int buyPrice = Integer.parseInt(BuyandSell[0]);
            int sellPrice = Integer.parseInt(BuyandSell[1]);
            int totalValue = ((2 - arr[i]) * buyPrice) + (arr[i] * sellPrice);
            cardList.add(new card(i, arr[i], buyPrice, sellPrice, totalValue));
        }

        cardList.sort(new sortTotal());
        long profit = 0;

        for (int i = 0; i < K; i++) {
            profit -= cardList.get(i).costToCombo;
        }

        for (int i = K; i < T; i++) {
            profit += cardList.get(i).costToSell;
        }
        System.out.println(profit);
    }
}

class card {
    int cardnum; // index gives number
    int total; //
    int costToCombo; // Sets to how much to buy ALL cards NEED
    int costToSell; // Sets to how much to sell ALL cards we have
    int totalValue;

    public card(int cardnum, int total, int buyprice, int sellprice, int totalValue) {
        this.cardnum = cardnum;
        this.total = total;
        this.costToCombo = (2 - total) * buyprice;
        this.costToSell = total * sellprice;
        this.totalValue = totalValue;
    }
}

class sortTotal implements Comparator<card> {
    public int compare(card a, card b) {
        return Integer.compare(a.totalValue, b.totalValue);
    }
}

/*
 * if (a.totalValue < b.totalValue) { // If my A has lower total value, bring it
 * to the front
 * return -1;
 * } else if (a.totalValue > b.totalValue) {
 * return 1;
 * } else { // If they have the same value, the one with the smaller buy value
 * at the front
 * if (a.costToCombo < b.costToCombo) {
 * return -1;
 * } else if (a.costToCombo > b.costToCombo) { // if A.buy < b.Buy
 * return 1;
 * } else {
 * return 0; // if they have same value
 * }
 * }
 * }
 */
/*
 * 
 * Test cases i need to check
 * input = 10000
 * input = 60
 * input = 1
 * what if there are total 4 types of cards in the deck? but we only have 3 on
 * our hand
 */

/*
 * 
 * find total value of card ==> draw out and you will see those of lower total
 * value, its better to keep and sell those with higher total value
 * if smth cost alot but very little to sell --> u might as well not consider it
 * and get other cards so just sell it
 * if smth cost v little to buy but sells for alot --> might as well sell it to
 * get other cards and complete the deck
 * 
 * 
 */
