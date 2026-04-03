package universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions;

public class WrongDataTypeException extends Exception {

    public WrongDataTypeException() {
        super("Type de données invalide dans le fichier CSV.");
    }

    public WrongDataTypeException(String details) {
        super("Type de données invalide : " + details);
    }
}
