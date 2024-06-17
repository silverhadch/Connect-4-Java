import javax.swing.*;

public class Spielraum {

    static Spielfeld_Gitter meingitter = new Spielfeld_Gitter();
    static Regeln meineregeln = new Regeln();
    static Spieler Spieler1 = new Spieler(1);
    Spieler Spieler2 = new Spieler(2);
    Bot derBot = new Bot();

    public static void main(String[] args) {
        Spielraum spiel = new Spielraum();
        spiel.startGame();
    }

    public void startGame() {
        boolean wieder = false;
        while (!wieder) {
            boolean spielBeendet = false;

            while (!spielBeendet) {
                Soundplayer.playBackgroundMusic();
                meingitter = new Spielfeld_Gitter();
                GUI.DeineGUI();
                boolean bot = GUI.checkgamemode(derBot, Spieler1, Spieler2);
                boolean ende = false;
                while (!ende) {
                    int menschspalte = 0;
                    int botspalte = 0;
                    boolean check = false;

                    GUI.showTimedMessage(Spieler1.name + " ist am Zug.", "Spieler Zug", JOptionPane.INFORMATION_MESSAGE, 600);

                    while (!check) {
                        menschspalte = GUI.getSpalteVonBenutzer(Spieler1);
                        check = meineregeln.checkZug(menschspalte, meingitter.Gitter);
                    }
                    meingitter.setStein(menschspalte, Spieler1.SpielerID);
                    if (meineregeln.checkGewinn(Spieler1.SpielerID, meingitter.Gitter)) {
                        Soundplayer.stopBackgroundMusic(Soundplayer.backgroundClip);
                        Soundplayer.playWinMusic();
                        JOptionPane.showMessageDialog(null, Spieler1.name + " hat gewonnen!");
                        break;
                    }

                    check = false;
                    if (bot) {
                        GUI.showTimedMessage(derBot.name + " ist am Zug.", "Bot Zug", JOptionPane.INFORMATION_MESSAGE, 750);
                        while (!check){
                            botspalte = Bot.findeBotZug(botspalte, meingitter.Gitter, Spieler1, derBot);
                            check = meineregeln.checkZug(botspalte, meingitter.Gitter);
                        }


                        meingitter.setStein(botspalte, derBot.SpielerID);
                        if (meineregeln.checkGewinn(derBot.SpielerID, meingitter.Gitter)) {
                            Soundplayer.stopBackgroundMusic(Soundplayer.backgroundClip);
                            Soundplayer.playDrawandLoseMusic();
                            JOptionPane.showMessageDialog(null, derBot.name + " hat gewonnen!");
                            break;
                        }
                    } else {
                        GUI.showTimedMessage(Spieler2.name + " ist am Zug.", "Spieler Zug", JOptionPane.INFORMATION_MESSAGE, 600);
                        while (!check) {
                            menschspalte = GUI.getSpalteVonBenutzer(Spieler2);
                            check = meineregeln.checkZug(menschspalte, meingitter.Gitter);
                        }
                        meingitter.setStein(menschspalte, Spieler2.SpielerID);
                        if (meineregeln.checkGewinn(Spieler2.SpielerID, meingitter.Gitter)) {
                            Soundplayer.stopBackgroundMusic(Soundplayer.backgroundClip);
                            Soundplayer.playWinMusic();
                            JOptionPane.showMessageDialog(null, Spieler2.name + " hat gewonnen!");
                            break;
                        }
                    }
                    ende = meineregeln.checkUnentschieden(meingitter.Gitter);
                }

                boolean unentschieden;
                unentschieden = meineregeln.checkUnentschieden(meingitter.Gitter);

                if (unentschieden){
                    Soundplayer.stopBackgroundMusic(Soundplayer.backgroundClip);
                    Soundplayer.playDrawandLoseMusic();
                    JOptionPane.showMessageDialog(null,"Das Spiel endet unentschieden!");
                }


                spielBeendet = true;
            }
            // Frage den Benutzer nach einem Neustart des gesamten Spiels
            wieder = !GUI.neustartAbfrage();
        }
        GUI.frame.dispose();
    }
}