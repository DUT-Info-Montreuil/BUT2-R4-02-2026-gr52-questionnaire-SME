package universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions;

public class FileAccessDeniedException extends Exception {

    public FileAccessDeniedException() {
        super("Accès au fichier refusé ou flux illisible.");
    }

    public FileAccessDeniedException(String cheminFichier) {
        super("Accès refusé au fichier : \"" + cheminFichier + "\"");
    }
}