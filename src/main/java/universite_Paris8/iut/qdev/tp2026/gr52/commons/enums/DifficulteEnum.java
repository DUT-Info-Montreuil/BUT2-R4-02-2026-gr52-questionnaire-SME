
package universite_Paris8.iut.qdev.tp2026.gr52.commons.enums;

public enum DifficulteEnum {
    FACILE("1"),
    NORMAL("2"),
    DIFFICILE("3");

    private final String valeur;

    DifficulteEnum(String valeur) {
        this.valeur = valeur;
    }

    public String getValeur() {
        return valeur;
    }

    /**
     * Retourne le DifficulteEnum correspondant à la valeur CSV ("1", "2" ou "3").
     *
     * @param valeur la valeur lue dans le CSV
     * @return le DifficulteEnum correspondant
     * @throws IllegalArgumentException si la valeur est inconnue
     */
    public static DifficulteEnum fromValeur(String valeur) {
        for (DifficulteEnum d : values()) {
            if (d.valeur.equals(valeur)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Valeur de difficulté inconnue : " + valeur);
    }
}