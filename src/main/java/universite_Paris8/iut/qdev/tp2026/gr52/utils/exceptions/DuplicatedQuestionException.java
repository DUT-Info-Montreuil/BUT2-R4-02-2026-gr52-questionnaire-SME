package universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions;

public class DuplicatedQuestionException extends Exception {

    public DuplicatedQuestionException() {
        super("Une question est dupliquée dans le questionnaire.");
    }

    public DuplicatedQuestionException(int idQuestionnaire, int numQuestion) {
        super("Question dupliquée : numQuestion=" + numQuestion
                + " déjà présente dans le questionnaire id=" + idQuestionnaire);
    }
}