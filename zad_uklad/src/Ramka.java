import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ramka extends JFrame {
    private final LayoutManager layout = new BorderLayout();


    private final JPanel buttonsPanel = new JPanel();
    private final JButton speedUp = new JButton("Przyspiesz");
    private final JButton slowDown = new JButton("Zwolnij");
    private final JLabel preSpeed = new JLabel("Prêdkoœæ:");
    private final JTextField speed = new JTextField("", 5);
    private final JCheckBox toggleOrbit = new JCheckBox("Poka¿ orbitê", true);
    private Uklad animPanel;
    public Ramka() {
        final Uklad.Planeta[] planety = new Uklad.Planeta[] {
                new Uklad.Planeta("S³oñce", 0d, 60, 0d, Color.YELLOW),
                new Uklad.Planeta("Merkury", 60d, 20, 1 / 0.2408d, Color.GRAY),
                new Uklad.Planeta("Wenus", 100d, 40, 1 / 0.6152d, Color.RED),
                new Uklad.Planeta("Ziemia", 170d, 50, 1d, Color.GREEN, new Uklad.Planeta[] {
                        new Uklad.Planeta("Ksiê¿yc", 40d, 15, 1d, Color.GRAY)
                }),
                new Uklad.Planeta("Mars", 240d, 45, 1 / 1.8808d, Color.ORANGE),
                new Uklad.Planeta("Jowisz", 350d, 150, 1 / 11.86d, Color.MAGENTA)
        };
        this.animPanel = new Uklad(planety);
        this.initGui();
        this.setSize(1000, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.animPanel.startRendering();
    }

    private void initGui() {
        this.setLayout(this.layout);

        this.buttonsPanel.setLayout(new FlowLayout());
        this.add(this.buttonsPanel, BorderLayout.SOUTH);


        this.buttonsPanel.add(this.speedUp);
        speedUp.addActionListener(this.listener);

        this.buttonsPanel.add(this.slowDown);
        slowDown.addActionListener(this.listener);

        this.buttonsPanel.add(this.preSpeed);

        this.buttonsPanel.add(this.speed);
        var text = Uklad.Planeta.calculateSpeed().toString();
        this.speed.setText(text);
        this.speed.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                try {
                    int newSpeed = Integer.parseInt(textField.getText());
                    Uklad.Planeta.setSpeed(newSpeed);
                } catch (NumberFormatException e2) {
                    //do nothing
                }
            }
        });

        this.buttonsPanel.add(this.toggleOrbit);
        this.toggleOrbit.addActionListener(this.listener);

        this.add(this.animPanel);
    }

    private ActionListener listener = (e) -> {
        var source = e.getSource();
        if (source == speedUp) {
            this.animPanel.triggerOperation(Uklad.Operation.SPEED_UP);
            var text = Uklad.Planeta.calculateSpeed().toString();
            this.speed.setText(text);
        } else if (source == slowDown) {
            this.animPanel.triggerOperation(Uklad.Operation.SLOW_DOWN);
            var text = Uklad.Planeta.calculateSpeed().toString();
            this.speed.setText(text);
        } else if (source == toggleOrbit) {
            this.animPanel.triggerOperation(Uklad.Operation.SHOW_ORBIT);
        }

    };

    public static void main (String[] args) {
        var r = new Ramka();

    }
}
