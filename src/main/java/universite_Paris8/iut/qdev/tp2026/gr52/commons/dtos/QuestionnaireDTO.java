package universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos;

import java.util.List;

public class QuestionnaireDTO {

    private int idQuestionnaire;
    private String libelleQuestionnaire;
    private String langue;
    private List<QuestionDTO> questions;

    public QuestionnaireDTO(int idQuestionnaire,
                            String libelleQuestionnaire,
                            String langue,
                            List<QuestionDTO> questions) {
        this.idQuestionnaire = idQuestionnaire;
        this.libelleQuestionnaire = libelleQuestionnaire;
        this.langue = langue;
        this.questions = questions;
    }

    public int getIdQuestionnaire() {
        return idQuestionnaire;
    }

    public void setIdQuestionnaire(int idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }

    public String getLibelleQuestionnaire() {
        return libelleQuestionnaire;
    }

    public void setLibelleQuestionnaire(String libelleQuestionnaire) {
        this.libelleQuestionnaire = libelleQuestionnaire;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
