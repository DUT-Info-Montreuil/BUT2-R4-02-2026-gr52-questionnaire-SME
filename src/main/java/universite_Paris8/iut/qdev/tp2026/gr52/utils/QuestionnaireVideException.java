package universite_Paris8.iut.qdev.tp2026.gr52.utils;

public class QuestionnaireVideException extends Exception {

    public QuestionnaireVideException() {
        super("Aucune donnée de questionnaire n’a été trouvée dans le fichier.");
    }

    public QuestionnaireVideException(String nomFichier) {
        super("Le questionnaire extrait du fichier \"" + nomFichier + "\" est vide (aucune question disponible).");
    }

    public QuestionnaireVideException(String nomFichier, Throwable cause) {
        super("Le questionnaire extrait du fichier \"" + nomFichier + "\" est vide ou invalide.", cause);
    }
}
