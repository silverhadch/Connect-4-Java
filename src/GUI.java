import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("all")
public class GUI {

    //private static JLabel[][] spielfeldLabels;
    private static JPanel spielfeldPanel;
    private static int selectedSpalte = -1;
    static JFrame frame;

    static void showTimedMessage(String message, String title, int messageType, int duration) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        JDialog dialog = optionPane.createDialog(title);


        Timer timer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Schließe das Dialogfeld nach Ablauf der Zeit
            }
        });

        timer.setRepeats(false); // Der Timer wird nur einmal ausgeführt
        timer.start();

        dialog.setVisible(true); // Zeige das Dialogfeld

        // Optional: Halte den Timer an, wenn das Dialogfeld vom Benutzer geschlossen wird
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                timer.stop();
            }
        });
    }
    static boolean neustartAbfrage() {
        int result = JOptionPane.showConfirmDialog(null, "Das Spiel ist beendet! Möchten Sie nochmal spielen?", "Neustart", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    static boolean checkgamemode(Bot derBot, Spieler Spieler1, Spieler Spieler2) {

        int result = JOptionPane.showConfirmDialog(null, "Möchten Sie gegen den Bot spielen?", "Bot-Modus", JOptionPane.YES_NO_OPTION);
        boolean botMode = (result == JOptionPane.YES_OPTION);

        if (botMode) {
            Spieler1.name = JOptionPane.showInputDialog("Gib deinen Namen ein:");
            showTimedMessage(Spieler1.name + " spielt Rot.","Farben Info1",JOptionPane.INFORMATION_MESSAGE, 750 );
            showTimedMessage(derBot.name + " spielt Gelb.","Farben Info2",JOptionPane.INFORMATION_MESSAGE, 750 );
        } else {
            Spieler1.name = JOptionPane.showInputDialog("Spieler 1, gib deinen Namen ein:");
            showTimedMessage(Spieler1.name + " spielt Rot.","Farben Info1",JOptionPane.INFORMATION_MESSAGE, 750 );
            Spieler2.name = JOptionPane.showInputDialog("Spieler 2, gib deinen Namen ein:");
            showTimedMessage(Spieler2.name + " spielt Gelb.","Farben Info2.1",JOptionPane.INFORMATION_MESSAGE, 750 );
        }

        return botMode;
    }


    public static int getSpalteVonBenutzer(Spieler spieler) {
        enableMausklicks();
        while (selectedSpalte == -1) {
            try {
                EventQueue.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        // Warte auf Mausklicks
                    }
                });
                Thread.sleep(50); // Kurze Pause, um die CPU-Last zu reduzieren
            } catch (InterruptedException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        int spalte = selectedSpalte;
        selectedSpalte = -1; // Zurücksetzen für den nächsten Zug
        return spalte;
    }

    public static void enableMausklicks() {
        spielfeldPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Berechne die Spalte basierend auf der X-Position des Mausklicks
                int breiteEinerZelle = spielfeldPanel.getWidth() / 7;
                selectedSpalte = e.getX() / breiteEinerZelle;
            }
        });
    }

    public static void DeineGUI() {
        if (frame != null) {
            frame.dispose(); // Alten Frame schließen
        }

        frame = new JFrame("Vier Gewinnt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        spielfeldPanel = new JPanel(new GridLayout(6, 7));
        spielfeldPanel.setBackground(Color.BLUE);

        // Erstelle das Spielfeld mit Ellipsen für jede Zelle
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                JPanel cell = getjPanel();
                spielfeldPanel.add(cell);
            }
        }

        // Setze das Layout des Panels, bevor die Ellipsen gezeichnet werden
        spielfeldPanel.setLayout(new GridLayout(6, 7));

        frame.add(spielfeldPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        farbedasfeld();

    }



    private static JPanel getjPanel() {
        Ellipse ellipse = new Ellipse(Color.WHITE); // Alle Ellipsen sind zu Beginn weiß
        JPanel cell = new JPanel();
        cell.setLayout(new BorderLayout());
        Color myColor = new Color(52, 59, 215);
        cell.setBorder(BorderFactory.createLineBorder(myColor)); //Trennlinien hinzufügen

        // Setze die bevorzugte Größe der Zelle basierend auf der kleineren Dimension
        int size = Math.min(100, 80); // Ändere dies nach Bedarf
        cell.setPreferredSize(new Dimension(size, size));

        cell.add(ellipse, BorderLayout.CENTER);
        return cell;
    }

    private static Color getSpielerFarbe(int spielerID) {
        if (spielerID == 1) {
            return Color.RED;
        } else if (spielerID == 2) {
            return Color.YELLOW;
        } else {
            return Color.WHITE;
        }
    }
    public static void updateSpielfeldGrafik (int Spalte, int leereZeile, int spielerID){
        if (spielfeldPanel != null && leereZeile * 7 + Spalte < spielfeldPanel.getComponentCount()) {
            JPanel zelle = (JPanel) spielfeldPanel.getComponent(leereZeile * 7 + Spalte);
            zelle.removeAll();

            // Beispiel: Zeichne eine Ellipse in der Mitte der Zelle
            Ellipse ellipse = new Ellipse(getSpielerFarbe(spielerID));
            zelle.add(ellipse, BorderLayout.CENTER);

            spielfeldPanel.revalidate();
            spielfeldPanel.repaint();
        }
    }

    public static void farbedasfeld() {
        if (spielfeldPanel != null) {
            for (Component component : spielfeldPanel.getComponents()) {
                if (component instanceof JPanel zelle) {
                    zelle.setBackground(Color.BLUE); // Setzen Sie den blauen Hintergrund für jede Zelle

                    // Überprüfen Sie, ob die Zelle eine Ellipse enthält und fügen Sie sie hinzu, falls nicht vorhanden
                    if (zelle.getComponentCount() == 0) {
                        Ellipse ellipse = new Ellipse(Color.WHITE);
                        zelle.add(ellipse, BorderLayout.CENTER);
                    }
                }
            }
            spielfeldPanel.revalidate();
            spielfeldPanel.repaint();
        }
    }
}



