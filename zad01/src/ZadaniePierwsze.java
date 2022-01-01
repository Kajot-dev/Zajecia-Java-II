public class ZadaniePierwsze {
    private static int nwd1(int a, int b) {
        int c; //do zamiany liczb
        while (b > 0) {
            c = b;
            b = a % b;
            a = c;
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(nwd1(5, 35));
        System.out.println(nwd1(4, 47));
        System.out.println(nwd1(45, 60));
        System.out.println(nwd1(525, 2310));
    }
}
