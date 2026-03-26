// ── MissingFileException.java ──────────────────────────────────────────────
package universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions;

public class MissingFileException extends Exception {

    public MissingFileException() {
        super("Le fichier est introuvable.");
    }

    public MissingFileException(String cheminFichier) {
        super("Le fichier est introuvable : \"" + cheminFichier + "\"");
    }
}


