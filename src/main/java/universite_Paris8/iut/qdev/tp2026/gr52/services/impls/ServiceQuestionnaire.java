package universite_Paris8.iut.qdev.tp2026.gr52.services.impls;

import universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos.QuestionDTO;
import universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr52.commons.enums.DifficulteEnum;
import universite_Paris8.iut.qdev.tp2026.gr52.services.interfaces.IServiceQuestionnaire;
import universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class ServiceQuestionnaire implements IServiceQuestionnaire {

    private static final String NOM_FICHIER_PATTERN = "questionsQuizz_.*\\.csv";
    private static final String SEPARATEUR = ";";
    private static final int NB_COLONNES = 9;

    // Indices des colonnes dans le CSV
    private static final int COL_ID_QUESTIONNAIRE   = 0;
    private static final int COL_LIBELLE_QUESTIONNAIRE = 1;
    private static final int COL_NUM_QUESTION       = 2;
    private static final int COL_LANGUE             = 3;
    private static final int COL_LIBELLE_QUESTION   = 4;
    private static final int COL_REPONSE            = 5;
    private static final int COL_DIFFICULTE         = 6;
    private static final int COL_EXPLICATION        = 7;
    private static final int COL_REFERENCE          = 8;

    /**
     * Charge le fichier CSV depuis le chemin donné et retourne un InputStream.
     *
     * @param cheminFichier chemin vers le fichier questionsQuizz_Vx.csv
     * @return InputStream du fichier
     * @throws MissingFileException      si le fichier n'existe pas
     * @throws WrongFileFormatException  si le nom du fichier ne correspond pas au pattern attendu
     * @throws EmptyQuestionnaireException si le fichier est vide
     */
    @Override
    public InputStream chargerFichier(String cheminFichier)
            throws MissingFileException, WrongFileFormatException, EmptyQuestionnaireException {

        File fichier = new File(cheminFichier);

        // Vérification existence du fichier
        if (!fichier.exists() || !fichier.isFile()) {
            throw new MissingFileException(cheminFichier);
        }

        // Vérification du format du nom de fichier
        String nomFichier = fichier.getName();
        if (!Pattern.matches(NOM_FICHIER_PATTERN, nomFichier)) {
            throw new WrongFileFormatException(nomFichier);
        }

        // Vérification que le fichier n'est pas vide
        if (fichier.length() == 0) {
            throw new EmptyQuestionnaireException(nomFichier);
        }

        try {
            return new FileInputStream(fichier);
        } catch (FileNotFoundException e) {
            throw new MissingFileException(cheminFichier);
        }
    }

    /**
     * Lit l'InputStream CSV et retourne la liste des QuestionnaireDTO.
     *
     * @param csvFile InputStream du fichier CSV
     * @return liste de QuestionnaireDTO
     * @throws FileAccessDeniedException   si l'InputStream est null ou illisible
     * @throws EmptyQuestionnaireException si le fichier ne contient aucune ligne de données
     * @throws WrongDataTypeException      si une valeur ne correspond pas au type attendu
     * @throws DuplicatedQuestionException si deux questions ont le même numQuestion dans un même questionnaire
     */
    @Override
    public List<QuestionnaireDTO> fournirListeQuestionnaire(InputStream csvFile)
            throws FileAccessDeniedException, EmptyQuestionnaireException,
            WrongDataTypeException, DuplicatedQuestionException {

        // Vérification accès au flux
        if (csvFile == null) {
            throw new FileAccessDeniedException();
        }

        List<String> lignes = lireLignes(csvFile);

        // Vérification présence de données
        if (lignes.isEmpty()) {
            throw new EmptyQuestionnaireException();
        }

        // Construction de la map : idQuestionnaire -> QuestionnaireDTO
        Map<Integer, QuestionnaireDTO> questionnairesMap = new LinkedHashMap<>();
        // Pour détecter les doublons : idQuestionnaire -> set de numQuestion déjà vus
        Map<Integer, Set<Integer>> numerosVus = new HashMap<>();

        for (String ligne : lignes) {
            String[] colonnes = ligne.split(Pattern.quote(SEPARATEUR), -1);

            if (colonnes.length != NB_COLONNES) {
                throw new WrongDataTypeException(
                        "Ligne malformée (" + colonnes.length + " colonnes au lieu de " + NB_COLONNES + ") : " + ligne
                );
            }

            // --- Parsing des champs ---
            int idQuestionnaire;
            int numQuestion;
            DifficulteEnum difficulte;

            try {
                idQuestionnaire = Integer.parseInt(colonnes[COL_ID_QUESTIONNAIRE].trim());
            } catch (NumberFormatException e) {
                throw new WrongDataTypeException(
                        "idQuestionnaire invalide : \"" + colonnes[COL_ID_QUESTIONNAIRE] + "\""
                );
            }

            try {
                numQuestion = Integer.parseInt(colonnes[COL_NUM_QUESTION].trim());
            } catch (NumberFormatException e) {
                throw new WrongDataTypeException(
                        "numQuestion invalide : \"" + colonnes[COL_NUM_QUESTION] + "\""
                );
            }

            try {
                difficulte = DifficulteEnum.fromValeur(colonnes[COL_DIFFICULTE].trim());
            } catch (IllegalArgumentException e) {
                throw new WrongDataTypeException(
                        "difficulte invalide : \"" + colonnes[COL_DIFFICULTE] + "\" (valeurs attendues : 1, 2, 3)"
                );
            }

            String libelleQuestionnaire = colonnes[COL_LIBELLE_QUESTIONNAIRE].trim();
            String langue               = colonnes[COL_LANGUE].trim();
            String libelleQuestion      = colonnes[COL_LIBELLE_QUESTION].trim();
            String reponse              = colonnes[COL_REPONSE].trim();
            String explication          = colonnes[COL_EXPLICATION].trim();
            String reference            = colonnes[COL_REFERENCE].trim();

            // --- Détection doublons ---
            numerosVus.putIfAbsent(idQuestionnaire, new HashSet<>());
            if (!numerosVus.get(idQuestionnaire).add(numQuestion)) {
                throw new DuplicatedQuestionException(idQuestionnaire, numQuestion);
            }

            // --- Construction des objets métier ---
            QuestionDTO question = new QuestionDTO(
                    numQuestion, libelleQuestion, reponse, difficulte, explication, reference
            );

            if (!questionnairesMap.containsKey(idQuestionnaire)) {
                questionnairesMap.put(
                        idQuestionnaire,
                        new QuestionnaireDTO(idQuestionnaire, libelleQuestionnaire, langue, new ArrayList<>())
                );
            }

            questionnairesMap.get(idQuestionnaire).getQuestions().add(question);
        }

        return new ArrayList<>(questionnairesMap.values());
    }

    /**
     * Lit toutes les lignes non vides de l'InputStream en gérant le BOM UTF-8.
     */
    private List<String> lireLignes(InputStream csvFile) throws FileAccessDeniedException {
        List<String> lignes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(csvFile, StandardCharsets.UTF_8))) {

            String ligne;
            boolean premiereLigne = true;
            while ((ligne = reader.readLine()) != null) {
                // Suppression du BOM UTF-8 éventuel sur la première ligne
                if (premiereLigne) {
                    ligne = ligne.replace("\uFEFF", "");
                    premiereLigne = false;
                }
                if (!ligne.isBlank()) {
                    lignes.add(ligne);
                }
            }
        } catch (IOException e) {
            throw new FileAccessDeniedException();
        }
        return lignes;
    }
}