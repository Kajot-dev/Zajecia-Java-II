import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ramka extends JFrame {
    private List<Dokument> dokumenty;
    private JPanel mojPanel;
    private List<JTextField> polaTekstoweTytuly;
    private List<JTextField> getPolaTekstoweTresci;
    public Ramka(List<Dokument> documentList) {
        super();
        this.setTitle("Aplikacja Kuby");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.dokumenty = documentList;
        this.mojPanel = new JPanel();
        this.add(this.mojPanel);
        this.wypelnijPanel();
    }
    protected void wypelnijPanel() {
        this.mojPanel.removeAll();
        this.polaTekstoweTytuly = new ArrayList<>();
        this.getPolaTekstoweTresci = new ArrayList<>();
        var layout = new GridLayout(this.dokumenty.size(), 2);
        this.mojPanel.setLayout(layout);
        for (var doc : this.dokumenty) {
            var tytul = new JTextField();
            tytul.setText(doc.getTytul());
            this.mojPanel.add(tytul);
            this.polaTekstoweTytuly.add(tytul);

            var tresc = new JTextField();
            tresc.setText(doc.getTresc());
            this.mojPanel.add(tresc);
            this.getPolaTekstoweTresci.add(tresc);
        }
        this.pack();

    }
}
