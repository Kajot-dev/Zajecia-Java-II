import javax.swing.*;
import java.awt.*;

public class Uklad extends JPanel {
    public static class Planeta {
        public static double getAnglePerTick() {
            return anglePerTick;
        }
        public static void setSpeed(int s) {
            double newSpeed = s / 10d;
            if (newSpeed <= 0d) newSpeed = 0d;
            Planeta.anglePerTick = newSpeed;
        }

        public static Integer calculateSpeed() {
            return (Integer) ((int) (Planeta.getAnglePerTick() / 0.1d));
        }

        private static double anglePerTick = 1d;
        private static boolean showOrbit = true;
        private final String name;
        private final double distance;
        private final double size;
        private final double relativeSpeed;
        private final Color color;
        private Planeta[] children = null;
        private double angle;


        public Planeta(String name, double distance, double size, double relativeSpeed, Color color) {
            this.name = name;
            this.distance = distance;
            this.size = size;
            this.relativeSpeed = relativeSpeed;
            this.color = color;
        }

        public Planeta(String name, double distance, double size, double relativeSpeed, Color color, Planeta[] children) {
            this(name, distance, size, relativeSpeed, color);
            this.children = children;
        }
        public void draw(Graphics g, Point origin) {
            final Graphics2D g2 = (Graphics2D) g;
            double x = origin.x;
            double y = origin.y;
            if (this.distance != 0d) {
                g2.setColor(Color.BLACK);

                if (Planeta.showOrbit) {
                    final double t = this.distance * 2;
                    g2.drawOval((int) (x - this.distance), (int) (y - this.distance), (int) t, (int) t);
                }

                double angle = this.getAngle();
                x = x + this.distance * Math.cos(angle);
                y = y + this.distance * Math.sin(angle);
            }

            if (this.children != null) {
                Point planetCenter = new Point((int) x,(int) y);
                for (var p : this.children) {
                    p.draw(g, planetCenter);
                }
            }

            x -= this.size / 2;
            y -= this.size / 2;
            g2.setColor(this.color);

            g2.fillOval((int) x,(int) y,(int) this.size,(int) this.size);
        }

        public void tickAngle() {
            this.angle += Planeta.anglePerTick * this.relativeSpeed;
            if (this.children != null) {
                for (var c : this.children) {
                    c.tickAngle();
                }
            }
        }

        public double getAngle() {
            return Math.toRadians(this.angle);
        }
    };

    private Planeta[] planety = new Planeta[0];

    public enum Operation {
        SPEED_UP,
        SLOW_DOWN,
        SHOW_ORBIT
    }

    public void triggerOperation(Operation o) {
        switch (o) {
            case SPEED_UP -> {
                Planeta.anglePerTick += 0.1d;
                break;
            }
            case SLOW_DOWN -> {
                double n = Planeta.anglePerTick - 0.1d;
                if (n <= 0d) n = 0d;
                Planeta.anglePerTick = n;
                break;
            }
            case SHOW_ORBIT -> {
                Planeta.showOrbit = !Planeta.showOrbit;
                break;
            }

        }
    }

    private int tick = 1000 / 60;
    private Thread animThread;


    public Uklad(Planeta[] planety) {
        this.setDoubleBuffered(true);
        this.setBackground(Color.WHITE);
        this.planety = planety;
    }

    public void startRendering() {
        this.animThread = new Thread(this.renderLoop);
        this.animThread.start();
    }

    public Runnable renderLoop = () -> {
        while (true) {

            for (var p : this.planety) {
                p.tickAngle();
            }
            this.repaint();

            try {
                Thread.sleep(this.tick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void paint(Graphics g) {
        super.paint(g);
        this.render(g);
    }

    private void render(Graphics g) {

        final Dimension d = this.getSize();
        final Point center = new Point(d.width / 2, d.height / 2);
        for (Planeta p : this.planety) {
            //relative to the center
            p.draw(g, center);
        }
    }

}
