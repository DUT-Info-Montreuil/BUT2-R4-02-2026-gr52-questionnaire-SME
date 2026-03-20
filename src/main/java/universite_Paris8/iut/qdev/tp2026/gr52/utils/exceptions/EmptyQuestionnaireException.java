package universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions;

public class EmptyQuestionnaireException extends Exception {

    public EmptyQuestionnaireException() {
        super("Aucune donnée de questionnaire n’a été trouvée dans le fichier.");
    }

    public EmptyQuestionnaireException(String nomFichier) {
        super("Le questionnaire extrait du fichier \"" + nomFichier + "\" est vide (aucune question disponible).");
    }

    public EmptyQuestionnaireException(String nomFichier, Throwable cause) {
        super("Le questionnaire extrait du fichier \"" + nomFichier + "\" est vide ou invalide.", cause);
    }
}
