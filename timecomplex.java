public class timecomplex {
    public static void main(String[] args) {
        for (long i = 0; i < 100000; ++i)
            System.out.println(1);

        // And after that try this.
        for (long i = 0; i < 100000; ++i)
            System.out.println(2147483647);
    } // The longer strign takes significantly longer to print
}
