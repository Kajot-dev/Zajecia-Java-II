import javax.swing.*;
import java.awt.*;

public class UkladPlanet extends JPanel {

    Point srodek = null;
    static double predkosc = 10f;
    static boolean pokazOrbite = true;

    Planeta[] planety;

    public static class Planeta {
        //stałe
        int promien;
        double predkoscWlasna;
        int odlegloscOdSrodka;
        Color kolor;

        //zmienne
        double kat = 0d; //w stopniach

        public Planeta(int promien, double predkoscWlasna, int odlegloscOdSrodka, Color kolor) {
            this.promien = promien;
            this.predkoscWlasna = predkoscWlasna;
            this.odlegloscOdSrodka = odlegloscOdSrodka;
            this.kolor = kolor;
        }

        public void draw(Graphics g, Point punktOdniesienia) {
            Graphics2D g2 = (Graphics2D) g;

            int srednicaOrbity = this.odlegloscOdSrodka * 2;

            if (pokazOrbite) {
                g2.setColor(Color.BLACK);
                g2.drawOval(punktOdniesienia.x - this.odlegloscOdSrodka, punktOdniesienia.y - this.odlegloscOdSrodka,  srednicaOrbity, srednicaOrbity);
            }

            int x = (int) (punktOdniesienia.x + this.odlegloscOdSrodka * Math.cos(Math.toRadians(this.kat)));
            int y = (int) (punktOdniesienia.y + this.odlegloscOdSrodka * Math.sin(Math.toRadians(this.kat)));

            x -= this.promien;
            y -= this.promien;

            int srednica = this.promien * 2;

            g2.setPaint(this.kolor);
            g2.fillOval(x, y, srednica, srednica);
        }

        public void tick() {
            this.kat += predkosc * predkoscWlasna;
        }
    }




    public UkladPlanet() {
        this.setDoubleBuffered(true);
    }

    public void dodajPlanety(Planeta[] planety) {
        this.planety = planety;
    }

    public void start() {

        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        this.srodek = new Point(x, y);

        var animThread = new Thread(this.rob);
        animThread.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (this.planety != null) {
            for (var planeta : this.planety) {
                planeta.draw(g, this.srodek);
            }
        }
    }

    private Runnable rob = () -> {
        while(true) {
            if (this.planety != null) {
                for (var planeta : this.planety) {
                    planeta.tick();
                }
            }
            this.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}
