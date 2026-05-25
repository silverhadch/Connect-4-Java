public class Spielfeld_Gitter {

    // Einwandern der Regeln in diese Klasse
    static Regeln meineregeln = new Regeln();

    //Das Gitter, da Steine nicht schweben
    public int[][] Gitter = new int[7][6];

    //setzts den Stein jetzt ernsthaft
    public void setStein(int Spalte, int spielerID) {
        int leereZeile = meineregeln.findeleerezeile(Spalte, Gitter);
        Gitter[Spalte][leereZeile] = spielerID;

        GUI.updateSpielfeldGrafik(Spalte, leereZeile, spielerID);

    }
}
