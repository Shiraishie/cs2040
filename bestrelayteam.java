import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class bestrelayteam {

    // Improvements that can be done -- Use of bufferedreader instead of scanner

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        ArrayList<runner> Runners = new ArrayList<runner>(num); // Runners is an array of objects containing type runner
                                                                // class of size num

        // Create array of our different runners
        for (int i = 0; i < num; i++) {
            String name = sc.next();
            double firstlegtiming = sc.nextDouble();
            double otherlegtiming = sc.nextDouble();
            runner newrunner = new runner(name, firstlegtiming, otherlegtiming);
            Runners.add(i, newrunner);
            ;
        }
        // Sort using Collections class in ascending order of otherleg
        Collections.sort(Runners, new sortbyotherlegtiming());

        // Use double for loop to find the minimum possible timing by iterating throught
        // the smallest
        double currentmin = Runners.get(0).firstlegtiming + Runners.get(1).otherlegtiming
                + Runners.get(2).otherlegtiming
                + Runners.get(3).otherlegtiming;

        // actual racers -- create an initial list
        ArrayList<runner> actualracers = new ArrayList<runner>();
        actualracers.add(Runners.get(0));
        actualracers.add(Runners.get(1));
        actualracers.add(Runners.get(2));
        actualracers.add(Runners.get(3));

        for (int j = 0; j < num; j++) {

            ArrayList<runner> racers = new ArrayList<runner>(4); // Creates list of empty racers

            double timing = Runners.get(j).firstlegtiming;
            // Try improving time complexity here
            racers.add(Runners.get(j)); // Adds first runner
            for (int k = 0; k < num; k++) {
                if (k == j) {
                    // skip cause we dont want to add same person
                } else {
                    if (racers.size() == 4) {
                        break;
                    } else {
                        timing += Runners.get(k).otherlegtiming;
                        racers.add(Runners.get(k));
                    }
                }
            }
            // Here we want to check if the timing < minimum
            if (timing < currentmin) {
                currentmin = timing;
                actualracers = racers;
            }
            // System.out.println(racers);
        }
        System.out.println(String.format("%.2f", currentmin));
        // System.out.println(actualracers);
        for (int i = 0; i < 4; i++) {
            System.out.println(actualracers.get(i).name);
        }
    }
}

class runner {
    // Attributes of runner class
    String name;
    double firstlegtiming;
    double otherlegtiming;

    // Constructor to create runner class with specific attributes
    runner(String name, double firstlegtiming, double otherlegtiming) {
        this.name = name;
        this.firstlegtiming = firstlegtiming;
        this.otherlegtiming = otherlegtiming;
    }
}

// Sorting them by timing so its easier to add to racers
class sortbyotherlegtiming implements Comparator<runner> {
    public int compare(runner first, runner second) {
        return Double.compare(first.otherlegtiming, second.otherlegtiming);
    }
}
