import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZadanieTrzecie {
    //z zadania 2
    private static List<Integer> czynnikiPierwsze(int liczba) {
        List<Integer> lista = new ArrayList<>();
        //wszystkie czynniki pierwsze są niewiększe niż pierwiastek z danej liczby
        for (int i = 2; i*i <= liczba; i++) {
            while (liczba % i == 0) {
                lista.add(i);
                liczba /= i;
            }
            //jeżeli wszystkie czynniki są mniejsze lub równe pierwiastkowi z liczby
            if (liczba == 1) return lista;
        }
        lista.add(liczba); //jeżeli ostatni czynnik jest większy niż pierwiastek z liczby
        return lista;
    }

    private static int nww(int a, int b) {
        var listaA = czynnikiPierwsze(a);
        var listaB = czynnikiPierwsze(b);
        var distinctList = Stream.concat(listaA.stream(),listaB.stream()).distinct().collect(Collectors.toList());
        int out = 1;
        for (int i : distinctList) {
            int czA = Collections.frequency(listaA, i);
            int czB = Collections.frequency(listaB, i);
            out *= Math.pow(i, Math.max(czA, czB));
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(nww(42, 56));
        System.out.println(nww(4, 10));
        System.out.println(nww(15, 240));
    }
}
