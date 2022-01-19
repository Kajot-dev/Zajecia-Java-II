import javax.swing.*;
import java.awt.*;

public class Ramka extends JFrame {

    private UkladPlanet animPanel = new UkladPlanet();
    private JPanel panelOpcji = new JPanel();

    public static void main(String[] args) {
        new Ramka();
    }

    public Ramka() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Buźka zaiwania");
        this.setSize(700, 700);
        this.initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        var przyciskSzybciej = new JButton("Przyspiesz");
        var przyciskWolniej = new JButton("Zwolnij");
        var pokazOrbite = new JCheckBox("Pokaż orbitę", true);
        var tekstPredkosc = new JLabel("Aktualna prędkość:");
        var polePredkosc = new JTextField("", 5);
        this.panelOpcji.add(przyciskSzybciej);
        this.panelOpcji.add(przyciskWolniej);
        this.panelOpcji.add(pokazOrbite);
        this.panelOpcji.add(tekstPredkosc);
        this.panelOpcji.add(polePredkosc);
        this.add(this.animPanel);
        this.add(this.panelOpcji, BorderLayout.SOUTH);
        this.setVisible(true);

        this.animPanel.dodajPlanety(new UkladPlanet.Planeta[] {
                new UkladPlanet.Planeta(20, 1, 60, Color.CYAN),
                new UkladPlanet.Planeta(10, 2, 30, Color.RED)
        });

        this.animPanel.start();
    }
}
