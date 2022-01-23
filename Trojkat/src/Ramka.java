import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Ramka extends JFrame {

    private JTextField poleA = new JTextField("", 10);
    private JTextField poleB = new JTextField("", 10);
    private JTextField poleC = new JTextField("", 10);
    private JButton przycisk = new JButton("Oblicz brakującą wartość");
    private JPanel cz1 = new JPanel();
    private JPanel cz1Pola = new JPanel();
    private AnimationPanel cz2 = new AnimationPanel();

    private AnimationPanel.Trojkat aktualnyTrojkat;

    public static void main(String[] args) {
        new Ramka();
    }

    public Ramka() {
        this.setTitle("Trojkat");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.add(this.cz1, BorderLayout.NORTH);
        this.add(this.cz2, BorderLayout.CENTER);
        this.cz1.setLayout(new BoxLayout(this.cz1, BoxLayout.Y_AXIS));
        this.cz1.add(this.cz1Pola);
        this.cz1Pola.add(this.poleA);
        this.cz1Pola.add(this.poleB);
        this.cz1Pola.add(this.poleC);
        var tempPanel = new JPanel();
        tempPanel.add(this.przycisk);
        this.cz1.add(tempPanel);
        this.przycisk.addActionListener(this.obliczanie);
        this.setVisible(true);
    }

    private void obliczTrzeciBok() throws IllegalArgumentException {
        var aText = this.poleA.getText();
        var bText = this.poleB.getText();
        var cText = this.poleC.getText();

        JTextField pustePole;

        try {
            pustePole = Stream.of(this.poleA, this.poleB, this.poleC).filter(p -> p.getText().isBlank()).findFirst().orElseThrow();
        } catch (NoSuchElementException e2) {
            throw new IllegalArgumentException("Zostaw jedno pole puste!");
        }

        if (Stream.of(aText, bText, cText).filter(s -> !s.isBlank()).count() < 2) {
            throw new IllegalArgumentException("Wprowadź brakujące dane!");
        }


        try {
            var lista = Stream.of(aText, bText, cText).filter(s -> !s.isBlank()).mapToDouble(Double::parseDouble).toArray();
            double wynik;
            if (pustePole == this.poleC) {
                //dwie przyprostokątne
                wynik = Math.sqrt(Math.pow(lista[0], 2d) + Math.pow(lista[1], 2d));
                this.aktualnyTrojkat = new AnimationPanel.Trojkat(lista[0], lista[1], wynik);
            } else {
                if (lista[0] >= lista[1]) {
                    throw new IllegalArgumentException("Przypostokątna musi być mniejsza od przeciwprostokątnej!");
                }


                //ostatnia to liczba z c - przeciwprostokątna
                wynik = Math.sqrt(Math.pow(lista[1], 2d) - Math.pow(lista[0], 2d));

                if (pustePole == poleA) {
                    this.aktualnyTrojkat = new AnimationPanel.Trojkat(wynik, lista[0], lista[1]);
                } else {
                    this.aktualnyTrojkat = new AnimationPanel.Trojkat(lista[0], wynik, lista[1]);
                }
            }

            var nb = NumberFormat.getInstance();
            nb.setMaximumFractionDigits(3);

            pustePole.setText(nb.format(wynik).replace(",", "."));

        } catch (NumberFormatException e3) {
            throw new IllegalArgumentException("Wprowadź wyłacznie liczby!");
        }
    }

    private ActionListener obliczanie = e -> {
        try {
            this.obliczTrzeciBok();
            this.cz2.narysujTrojkat(this.aktualnyTrojkat);

        } catch (IllegalArgumentException e2) {
            JOptionPane.showMessageDialog(this, e2.getMessage(), "UWAGA", JOptionPane.WARNING_MESSAGE);
        }
    };
}
