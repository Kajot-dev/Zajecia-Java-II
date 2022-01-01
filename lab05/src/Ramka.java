import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Ramka extends JFrame implements ActionListener {
    private List<Dokument> dokumenty;
    private JPanel mojPanel;
    
    private List<JTextField> polaTekstoweTytuly;
    private List<JTextField> polaTekstoweTresci;
    private List<JButton> przyciski;

    private JButton przyciskDodaj = new JButton("Dodaj");
    
    public Ramka(List<Dokument> documentList) {
        super();
        this.setTitle("Aplikacja Kuby");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.dokumenty = documentList;
        this.mojPanel = new JPanel();
        this.add(this.mojPanel);
        this.przyciskDodaj.addActionListener(this);
        this.wypelnijPanel();
    }
    protected void wypelnijPanel() {
        this.mojPanel.removeAll();
        this.polaTekstoweTytuly = new ArrayList<>();
        this.polaTekstoweTresci = new ArrayList<>();
        this.przyciski = new ArrayList<>();
        
        var layout = new GridLayout(this.dokumenty.size() + 1, 3);
        
        this.mojPanel.setLayout(layout);
        for (var doc : this.dokumenty) {
            var tytul = new JTextField();
            tytul.setText(doc.getTytul());
            this.mojPanel.add(tytul);
            this.polaTekstoweTytuly.add(tytul);

            var tresc = new JTextField();
            tresc.setText(doc.getTresc());
            this.mojPanel.add(tresc);
            this.polaTekstoweTresci.add(tresc);
            
            var przycisk = new JButton("Usun");
            przycisk.addActionListener(this);
            this.mojPanel.add(przycisk);
            this.przyciski.add(przycisk);
        }
        this.mojPanel.add(new JLabel());
        this.mojPanel.add(this.przyciskDodaj);
        this.mojPanel.add(new JLabel());

        this.pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var przycisk = e.getSource();
        if (przycisk == this.przyciskDodaj) {
            String tytul = JOptionPane.showInputDialog(null, "Nazwa dokumentu", "Nowy dokument", JOptionPane.OK_CANCEL_OPTION);
            if (tytul == null) return;
            String tresc = JOptionPane.showInputDialog(null, "Treść dokumentu", "Nowa treść", JOptionPane.OK_CANCEL_OPTION);
            if (tresc == null) return;

            var doc = new Dokument(tytul, tresc);
            this.dokumenty.add(doc);
            this.wypelnijPanel();
        } else {
            int index = this.przyciski.indexOf(przycisk);
            if (index != -1 && index < this.dokumenty.size()) {
                var pradziwyPrzycisk = (JButton)przycisk;
                pradziwyPrzycisk.removeActionListener(this);
                this.dokumenty.remove(index);
                this.wypelnijPanel();
            }
        }

    }
}
