public class Regeln {

    //Hinzugefügt obwohl ich immer noch einen einfachen Counter besser finde
    public boolean checkUnentschieden(int [][]meingitter){
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (meingitter[col][row] == 0) {
                    return false; // Es gibt mindestens ein leeres Feld
                }
            }
        }
        return true; // Alle Felder sind belegt
    }

    //Guckt ob dein Zug illegal ist um ihn den Behörden zumelden
    public boolean checkZug(int Spalte, int[][] meingitter) {
        for (int i = 5; i >= 0; i--) {
            if (meingitter[Spalte][i] == 0) {
                return true; // Die Zeile ist leer
            }
        }
        return false; // Alle Zeilen in der Spalte sind bereits belegt
    }
    //findet eine leere zeile um den Zug zusetzen weil Newton für Computerlogik nicht exiestiert
    public int findeleerezeile(int Spalte, int[][] meingitter) {
        for (int i = 5; i >= 0; i--) {
            if (meingitter[Spalte][i] == 0) {
                return i; // Rückgabe der leeren Zeile
            }
        }
        return -1;
    }

    //checkt ob einer oder der bot dich besiegt hat (der Bot spielt nicht gerade göttlich, trozdem hat er mich geschlagen)
    public boolean checkGewinn(int spielerID, int[][] meingitter){

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (meingitter[j][i] == spielerID &&
                        meingitter[j + 1][i] == spielerID &&
                        meingitter[j + 2][i] == spielerID &&
                        meingitter[j + 3][i] == spielerID) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if (meingitter[i][j] == spielerID &&
                        meingitter[i][j + 1] == spielerID &&
                        meingitter[i][j + 2] == spielerID &&
                        meingitter[i][j + 3] == spielerID) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (meingitter[i][j] == spielerID &&
                        meingitter[i + 1][j + 1] == spielerID &&
                        meingitter[i + 2][j + 2] == spielerID &&
                        meingitter[i + 3][j + 3] == spielerID) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 3; j < 6; j++) {
                if (meingitter[i][j] == spielerID &&
                        meingitter[i + 1][j - 1] == spielerID &&
                        meingitter[i + 2][j - 2] == spielerID &&
                        meingitter[i + 3][j - 3] == spielerID) {
                    return true;
                }
            }
        }
        return false;
    }
}