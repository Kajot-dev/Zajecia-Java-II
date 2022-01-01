import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.WindowConstants;

public class Ramka extends JFrame implements ActionListener {
    public final JButton przycisk;
    public final JButton przycisk2;
    public final JTextField textField;
    public Ramka () {
        super();
        this.setTitle("Aplikacja Kuby");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        var layout = new GridLayout(1, 3);
        this.setLayout(layout);

        this.przycisk = new JButton("Klikać na własne ryzyko");
        this.przycisk.addActionListener(this);

        this.textField = new JTextField();

        this.przycisk2 = new JButton("Zniszcz świat");
        this.przycisk2.addActionListener(this);

        this.add(this.przycisk);
        this.add(this.textField);
        this.add(this.przycisk2);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var source = e.getSource();
        if (source == przycisk) {
            JOptionPane.showMessageDialog(this, "Działa! Jestem genialny!");
        } else if (source == przycisk2) {
            this.textField.setText("Kliknięte!");
        }

    }
}
