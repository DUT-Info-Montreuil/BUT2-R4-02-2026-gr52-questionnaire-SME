package universite_Paris8.iut.qdev.tp2026.gr52.utils;

public class MauvaisFormatFichierException extends Exception {

    public MauvaisFormatFichierException(String nomFichier) {
        super("Le fichier \"" + nomFichier + "\" ne respecte pas le format attendu (questionsQuizz_Vx.csv).");
    }

    public MauvaisFormatFichierException(String nomFichier, Throwable cause) {
        super("Erreur de format dans le fichier \"" + nomFichier + "\" (questionsQuizz_Vx.csv).", cause);
    }
}
