package universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos;

public class QuestionDTO {

    private int numQuestion;
    private String libelleQuestion;
    private String reponse;
    private int difficulte;
    private String explication;
    private String reference;

    public QuestionDTO(int numQuestion,
                       String libelleQuestion,
                       String reponse,
                       int difficulte,
                       String explication,
                       String reference) {
        this.numQuestion = numQuestion;
        this.libelleQuestion = libelleQuestion;
        this.reponse = reponse;
        this.difficulte = difficulte;
        this.explication = explication;
        this.reference = reference;
    }

    public int getNumQuestion() {
        return numQuestion;
    }

    public void setNumQuestion(int numQuestion) {
        this.numQuestion = numQuestion;
    }

    public String getLibelleQuestion() {
        return libelleQuestion;
    }

    public void setLibelleQuestion(String libelleQuestion) {
        this.libelleQuestion = libelleQuestion;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public String getExplication() {
        return explication;
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
