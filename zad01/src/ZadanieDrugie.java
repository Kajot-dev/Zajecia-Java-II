import java.util.ArrayList;
import java.util.List;

public class ZadanieDrugie {
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

    private static List<Integer> czescWspolna(List<Integer> listaA, List<Integer> listaB) {
        List<Integer> out = new ArrayList<>();
        for (int i : listaA) {
            int index = listaB.indexOf(i);
            if (index != -1) {
                out.add(i);
                listaB.remove(index);
            }
        }
        return out;
    }

    private static int nwd2 (int a, int b) {
        var listaA = czynnikiPierwsze(a);
        var listaB = czynnikiPierwsze(b);
        var czescWspolna = czescWspolna(listaA, listaB);
        return czescWspolna.stream().reduce(1, (subtotal, el) -> subtotal * el);
    }

    public static void main(String[] args) {
        System.out.println(nwd2(5, 35));
        System.out.println(nwd2(4, 47));
        System.out.println(nwd2(45, 60));
        System.out.println(nwd2(525, 2310));
    }
}
