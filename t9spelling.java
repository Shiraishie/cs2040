import java.io.*;

import java.util.ArrayList;

//Using Arraylist method //Slightly faster time complexity

public class t9spelling {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        String aN = br.readLine();
        int n = Integer.parseInt(aN);

        ArrayList<String> lst = new ArrayList<>();
        lst.add("2");
        lst.add("22");
        lst.add("222");
        lst.add("3");
        lst.add("33");
        lst.add("333");
        lst.add("4");
        lst.add("44");
        lst.add("444");
        lst.add("5");
        lst.add("55");
        lst.add("555");
        lst.add("6");
        lst.add("66");
        lst.add("666");
        lst.add("7");
        lst.add("77");
        lst.add("777");
        lst.add("7777");
        lst.add("8");
        lst.add("88");
        lst.add("888");
        lst.add("9");
        lst.add("99");
        lst.add("999");
        lst.add("9999");
        lst.add("0");
        // Create something like a dictionary//
        // pw.println(lst);

        for (int i = 0; i < n; i++) {

            String Case = br.readLine();
            // read each char
            StringBuilder outputString = new StringBuilder();
            outputString.append("Case #").append(String.valueOf(i + 1)).append(": ");

            int initial = Case.charAt(0) - 97;
            // To get first digit
            if (initial == -65) {
                outputString.append("0");
            } else {
                outputString.append(lst.get(initial));
            }
            for (int k = 1; k < Case.length(); k++) {
                int letter = Case.charAt(k) - 97; // Automatically gives ASCI value for case.charAT(k)
                int prev = Case.charAt(k - 1) - 97;

                // Set special cases for 0 here
                if (letter == -65) {
                    if (prev == -65) {
                        outputString.append(" ");
                    }
                    outputString.append(lst.get(26));
                } else {
                    // Set for normal cases
                    if (prev == -65) {
                        outputString.append(lst.get(letter));
                    } else {
                        if (lst.get(prev).charAt(0) == lst.get(letter).charAt(0)) {
                            outputString.append(" ");
                        }
                        outputString.append(lst.get(letter));
                        // pw.println(letter);
                    }
                }
            }
            pw.println(outputString);

        }
        pw.close();
    }
}
