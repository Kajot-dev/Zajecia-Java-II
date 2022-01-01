import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ramka extends JFrame {
    private List<Dokument> dokumenty;
    private final JPanel mojPanel;
    private static final File SAVE_DIR = new File("documents");
    
    private final List<JTextField> polaTekstoweTytuly = new ArrayList<>();
    private final List<JTextField> polaTekstoweTresci = new ArrayList<>();
    private final List<JButton> przyciskiUsun = new ArrayList<>();
    private final List<JButton> przyciskiZmien = new ArrayList<>();
    private final List<BasicArrowButton> przyciskiGora = new ArrayList<>();
    private final List<BasicArrowButton> przyciskiDol = new ArrayList<>();

    private JButton przyciskDodaj = new JButton("Dodaj");
    private JButton przyciskZapisz = new JButton("Zapisz");
    private JButton przyciskWczytaj = new JButton("Wczytaj");

    static {
        if (!SAVE_DIR.exists() && !SAVE_DIR.mkdir()) {
            System.err.println("Could not create documents folder!");
            System.exit(-1);
        }
    }
    
    public Ramka(List<Dokument> documentList) {
        super();
        this.setTitle("Aplikacja Kuby");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.dokumenty = documentList;
        this.mojPanel = new JPanel();
        this.add(this.mojPanel);
        this.przyciskDodaj.addActionListener(this.dodajListener);
        this.przyciskWczytaj.addActionListener(this.wczytajListener);
        this.przyciskZapisz.addActionListener(this.zapiszListener);
        this.wypelnijPanel();
    }
    protected void wypelnijPanel() {
        this.mojPanel.removeAll();
        this.polaTekstoweTytuly.clear();
        this.polaTekstoweTresci.clear();
        this.przyciskiUsun.clear();
        this.przyciskiZmien.clear();
        this.przyciskiGora.clear();
        this.przyciskiDol.clear();
        
        var layout = new GridLayout(this.dokumenty.size() + 1, 5);
        
        this.mojPanel.setLayout(layout);
        for (var doc : this.dokumenty) {
            var gora = new BasicArrowButton(SwingConstants.NORTH);
            var dol = new BasicArrowButton(SwingConstants.SOUTH);
            gora.addActionListener(this.changePosListener);
            dol.addActionListener(this.changePosListener);
            this.mojPanel.add(gora);
            this.mojPanel.add(dol);
            this.przyciskiGora.add(gora);
            this.przyciskiDol.add(dol);

            var tytul = new JTextField();
            tytul.setText(doc.getTytul());
            this.mojPanel.add(tytul);
            this.polaTekstoweTytuly.add(tytul);

            var tresc = new JTextField();
            tresc.setText(doc.getTresc());
            this.mojPanel.add(tresc);
            this.polaTekstoweTresci.add(tresc);
            
            var przyciskUsun = new JButton("Usun");
            przyciskUsun.addActionListener(this.usunListener);
            this.mojPanel.add(przyciskUsun);
            this.przyciskiUsun.add(przyciskUsun);

            var przyciskZmien = new JButton("Zmien");
            przyciskZmien.addActionListener(this.zmienListener);
            this.mojPanel.add(przyciskZmien);
            this.przyciskiZmien.add(przyciskZmien);

        }
        this.mojPanel.add(new JLabel());
        this.mojPanel.add(this.przyciskDodaj);
        this.mojPanel.add(this.przyciskWczytaj);
        this.mojPanel.add(this.przyciskZapisz);
        this.mojPanel.add(new JLabel());

        this.pack();

    }

    private ActionListener dodajListener = (e) -> {
        String tytul = JOptionPane.showInputDialog(null, "Nazwa dokumentu", "Nowy dokument", JOptionPane.OK_CANCEL_OPTION);
        if (tytul == null) return;
        String tresc = JOptionPane.showInputDialog(null, "Treœæ dokumentu", "Nowa treœæ", JOptionPane.OK_CANCEL_OPTION);
        if (tresc == null) return;

        var doc = new Dokument(tytul, tresc);
        this.dokumenty.add(doc);
        this.wypelnijPanel();
    };

    private ActionListener wczytajListener = (e) -> {
        var saveFile = new File(Ramka.SAVE_DIR, "plain_documents.txt");
        try (
                var stream = new BufferedInputStream(new FileInputStream(saveFile));
                var sc = new Scanner(stream)
        ) {
            var buff = new ArrayList<String>();
            var target = new ArrayList<Dokument>();
            while (sc.hasNextLine()) {
                var line = sc.nextLine();
                if (line.isBlank()) continue;
                buff.add(line);
                if (buff.size() == 2) {
                    var doc = Dokument.fromBuffer(buff);
                    target.add(doc);
                    buff.clear();
                }
            }
            this.dokumenty = target;
            this.wypelnijPanel();
            JOptionPane.showMessageDialog(null, "Wczytano dokumenty!");
        } catch (FileNotFoundException fileNotFoundException) {
            JOptionPane.showMessageDialog(null, "Nie znaleziono zapisanych dokumentów!");
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "B³ad wczytywania dokumentów!");
            ioException.printStackTrace();
        }
    };

    private ActionListener zapiszListener = (e) -> {
        var file = new File(Ramka.SAVE_DIR, "plain_documents.txt");
        try (
                var writer = new BufferedWriter(new FileWriter(file))
        ) {
            for (var doc : this.dokumenty) {
                writer.write(doc.save());
                writer.write('\n');
            }
            JOptionPane.showMessageDialog(null, "Zapisano dokumenty!");
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "B³ad zapisywania dokumentów!");
            ioException.printStackTrace();
        }
    };

    private ActionListener changePosListener = (e) -> {
        var przycisk = e.getSource();
        int index = this.przyciskiGora.indexOf(przycisk);
        if (index != -1 && index < this.dokumenty.size()) {
            int targetPos = index - 1 >= 0 ? index - 1 : dokumenty.size() - 1;
            var temp = this.dokumenty.get(index);
            this.dokumenty.set(index, this.dokumenty.get(targetPos));
            this.dokumenty.set(targetPos, temp);
            this.wypelnijPanel();
            return;
        }
        index = this.przyciskiDol.indexOf(przycisk);
        if (index != -1 && index < this.dokumenty.size()) {
            int targetPos = index + 1 < dokumenty.size() ? index + 1 : 0;
            var temp = this.dokumenty.get(index);
            this.dokumenty.set(index, this.dokumenty.get(targetPos));
            this.dokumenty.set(targetPos, temp);
            this.wypelnijPanel();
            return;
        }
    };

    private ActionListener usunListener = (e) -> {
      var przycisk = e.getSource();
      int index = this.przyciskiUsun.indexOf(przycisk);
      if (index != -1 && index < this.dokumenty.size()) {
          var pradziwyPrzycisk = (JButton)przycisk;
          pradziwyPrzycisk.removeActionListener(this.usunListener);
          this.przyciskiZmien.get(index).removeActionListener(this.zmienListener);
          this.przyciskiGora.get(index).removeActionListener(this.changePosListener);
          this.przyciskiDol.get(index).removeActionListener(this.changePosListener);
          this.dokumenty.remove(index);
          this.wypelnijPanel();
      }
    };

    private ActionListener zmienListener = (e) -> {
        var przycisk = e.getSource();
        int index = this.przyciskiZmien.indexOf(przycisk);
        if (index != -1 && index < this.dokumenty.size()) {
            var doc = this.dokumenty.get(index);
            doc.setTresc(this.polaTekstoweTresci.get(index).getText());
            doc.setTytul(this.polaTekstoweTytuly.get(index).getText());
            JOptionPane.showMessageDialog(null, "Dokument zmieniony!");
        }
    };
}
