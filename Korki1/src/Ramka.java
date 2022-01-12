import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Ramka extends JFrame {

    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();

    private JPanel panel1_1 = new JPanel();
    private JPanel panel1_2 = new JPanel();

    public static void main(String[] args) {
        var r = new Ramka();
    }

    public Ramka() {
        this.setVisible(true);
        this.setTitle("Apka");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //layout
        this.setLayout(new GridLayout(1, 2));

        //dodajemy rzeczy
        this.add(this.panel1);
        this.add(this.panel2);

        //panel1
        this.panel1.setLayout(new GridLayout(2, 1));
        this.panel1.add(this.panel1_1);
        this.panel1.add(this.panel1_2);

        //panel1_1
        this.panel1_1.setLayout(new GridLayout(2,2));

        //panel1_2
        this.panel1_2.setLayout(new GridLayout(3, 1));

        //panel 2
        this.panel2.setLayout(new GridLayout(1, 2));

        for (int i = 1; i <= 4; i++) {
            this.panel1_1.add(new JLabel(i + "."));
        }

        for (int i = 5; i <= 7; i++) {
            this.panel1_2.add(new JLabel(i + "."));
        }

        var przycisk1 = new JButton("Przycisk 1");
        var przycisk2 = new JButton("Przycisk 2");

        przycisk1.addActionListener(zlicz3);
        przycisk2.addActionListener(zlicz4);

        this.panel2.add(przycisk1);
        this.panel2.add(przycisk2);



        this.pack();
    }

    private ActionListener zlicz = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private ActionListener zlicz2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //coś się dzieje
        }
    };

    private ActionListener zlicz3 = (e) -> {
        JOptionPane.showMessageDialog(null, "Zlicz 3!");
        //wnętrze metody actionPerformed(ActionEvent e)
    };

    private ActionListener zlicz4 = (e) -> {
        JOptionPane.showMessageDialog(null, "Zlicz 4!");
        //wnętrze metody actionPerformed(ActionEvent e)
    };
}
