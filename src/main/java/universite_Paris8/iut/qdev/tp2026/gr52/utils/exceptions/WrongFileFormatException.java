package universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions;

public class WrongFileFormatException extends Exception {

    public WrongFileFormatException(String nomFichier) {
        super("Le fichier \"" + nomFichier + "\" ne respecte pas le format attendu (questionsQuizz_Vx.csv).");
    }

    public WrongFileFormatException(String nomFichier, Throwable cause) {
        super("Erreur de format dans le fichier \"" + nomFichier + "\" (questionsQuizz_Vx.csv).", cause);
    }
}
