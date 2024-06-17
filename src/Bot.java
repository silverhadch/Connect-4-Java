import java.util.Random;

public class Bot extends Spieler {

    private static final Regeln meineregeln = new Regeln();

    Bot() {
        super(2);
    }

    String name = "Der Bot";

    public static int findeBotZug(int Spalte, int[][] spielfeld, Spieler Spieler1, Bot derBot) {
        // Überprüfe, ob es sich um den ersten Zug des Spielers 2 (Bot) handelt
        boolean ersterZug = true;
        for (int[] row : spielfeld) {
            for (int val : row) {
                if (val == derBot.SpielerID) {
                    ersterZug = false;
                    break;
                }
            }
            if (!ersterZug) {
                break;
            }
        }

        if (ersterZug) {
            if (spielfeld[3][5] == 1) {
                // Wenn Mitte besetzt, setze den Stein in Spalte 3 (Index 2), wenn frei
                return 2;
            } else {
                return 3;
            }
        }




        // Überprüfe, ob der Bot direkt gewinnen kann
        for (int col = 0; col < 7; col++) {
            int row = meineregeln.findeleerezeile(col, spielfeld);
            if (row != -1) {
                spielfeld[col][row] = derBot.SpielerID;
                if (meineregeln.checkGewinn(derBot.SpielerID, spielfeld)) {
                    spielfeld[col][row] = 0;
                    return col;
                }
                spielfeld[col][row] = 0;
            }
        }

        // Überprüfe, ob der Spieler1 (rot) in der nächsten Runde gewinnen könnte und blockiere ihn
        for (int col = 0; col < 7; col++) {
            int row = meineregeln.findeleerezeile(col, spielfeld);
            if (row != -1) {
                spielfeld[col][row] = Spieler1.SpielerID; // Annahme: Spieler1 ist rot
                if (meineregeln.checkGewinn(Spieler1.SpielerID, spielfeld)) {
                    spielfeld[col][row] = 0;
                    return col;
                }
                spielfeld[col][row] = 0;
            }
        }

        // Wenn weder ein Sieg noch eine Blockade notwendig ist, wähle einen zufälligen legalen Zug
        return findeZufallsZug(Spalte);
    }




    static int findeZufallsZug(int Spalte) {
        Random random = new Random();
        int zufallsVersatz = random.nextInt(3) - 1;

        // Der Bot wählt eine Spalte in der Nähe des Spielers mit mindestens 3 Feldern Abstand
        int botSpalte = Spalte + zufallsVersatz;

        // Überprüfe, ob die ausgewählte Spalte innerhalb des Spielfelds (1-7) liegt
        if (botSpalte < 0) {
            botSpalte = 0;
        } else if (botSpalte > 6) {
            botSpalte = 6;
        }

        return botSpalte;
    }
}
