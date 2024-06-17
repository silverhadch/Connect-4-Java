import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Ellipse extends JPanel {
    private final Color color;

    public Ellipse(Color color) {
        this.color = color;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);

        // Bestimme den kleineren Wert zwischen Breite und Höhe, um sicherzustellen, dass die Ellipse vollständig sichtbar ist
        int size = Math.min(getWidth(), getHeight());

        // Berechne die Position, damit die Ellipse in der Mitte der Zelle zentriert ist
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g.fillOval(x, y, size, size);
    }
}