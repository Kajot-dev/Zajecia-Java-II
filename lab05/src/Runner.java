import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        var listaDokumentow = new ArrayList<Dokument>();
        for (int i = 1; i <= 7 ; i++) {
            listaDokumentow.add(new Dokument("Dokument " + i, "Tresc " + i));
        }
        var mojaRamka = new Ramka(listaDokumentow);
        mojaRamka.setVisible(true);
    }
}
