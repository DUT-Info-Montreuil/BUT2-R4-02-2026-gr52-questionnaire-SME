package universite_Paris8.iut.qdev.tp2026.gr52.mocks;

import universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr52.services.interfaces.IServiceQuestionnaire;
import universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions.*;

import java.io.InputStream;
import java.util.List;

public class fichierOkImplMock implements IServiceQuestionnaire {
    @Override
    public InputStream chargerFichier(String cheminFichier) throws MissingFileException, WrongFileFormatException {
        InputStream csvfile = getClass().getResourceAsStream(cheminFichier);

        if (csvfile == null) {
            throw new MissingFileException();
        } else if (!cheminFichier.endsWith(".csv")) {
            throw new WrongFileFormatException(cheminFichier);
        } else {
            return csvfile;
        }
    }

    @Override
    public List fournirListeQuestionnaire(InputStream csvFile) throws FileAccessDeniedException, EmptyQuestionnaireException, WrongDataTypeException, DuplicatedQuestionException {
        java.util.List<QuestionnaireDTO> questionnaires = new java.util.ArrayList<>();

        // On lit le CSV fourni (ou on recharge le fichier demandé dans le sujet)
        InputStream source = csvFile;
        if (source == null) {
            try {
                source = chargerFichier("/questionnaires/questionsQuizz_2025_V1.csv");
            } catch (WrongFileFormatException e) {
                System.out.println("ERREUR - Mauvais format de fichier");
                throw new FileAccessDeniedException();
            } catch (MissingFileException e) {
                System.out.println("ERREUR - Le fichier n'existe pas.");
                throw new FileAccessDeniedException();
            }
        }

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(source))) {

            String ligne;
            // On groupe les lignes par idQuestionnaire + libellé + langue
            java.util.Map<String, java.util.List<String>> mapQuestionsParQuestionnaire = new java.util.HashMap<>();

            while ((ligne = br.readLine()) != null) {
                if (ligne.isBlank()) continue;

                String[] cols = ligne.split(";");
                // Format: 0:idQuestionnaire 1:libelleQuestionnaire 2:idQuestion 3:langue 4:libellé question ...
                if (cols.length < 4) {
                    throw new WrongDataTypeException();
                }

                String idQuestionnaire = cols[0].trim();
                String libelleQuestionnaire = cols[1].trim();
                String langue = cols[3].trim();

                String key = idQuestionnaire + "|" + libelleQuestionnaire + "|" + langue;

                mapQuestionsParQuestionnaire.putIfAbsent(key, new java.util.ArrayList<>());
                mapQuestionsParQuestionnaire.get(key).add(ligne);
            }

            if (mapQuestionsParQuestionnaire.isEmpty()) {
                throw new EmptyQuestionnaireException();
            }

            for (String key : mapQuestionsParQuestionnaire.keySet()) {
                String[] parts = key.split("\\|");
                String idStr = parts[0].trim();
                // enlève le BOM éventuel en début de fichier
                idStr = idStr.replace("\uFEFF", "");
                int idQ = Integer.parseInt(idStr);

                String libelleQ = parts[1];
                String langue = parts[2];

                java.util.List questions = new java.util.ArrayList(); // on laisse les QuestionDTO pour plus tard

                QuestionnaireDTO dto = new QuestionnaireDTO(
                        idQ,
                        libelleQ,
                        langue,
                        questions
                );
                questionnaires.add(dto);
            }

        } catch (java.io.IOException e) {
            throw new FileAccessDeniedException();
        }

        return questionnaires;
    }
}
