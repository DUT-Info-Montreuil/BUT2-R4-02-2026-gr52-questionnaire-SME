package universite_Paris8.iut.qdev.tp2026.gr52.tests;

import org.junit.jupiter.api.Test;
import universite_Paris8.iut.qdev.tp2026.gr52.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr52.mocks.fichierOkImplMock;
import universite_Paris8.iut.qdev.tp2026.gr52.utils.exceptions.*;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class fichierOkImplMockTest {

    private final fichierOkImplMock service = new fichierOkImplMock();

    @Test
    void chargerFichier_ok_quandFichierExisteEtCsv() throws MissingFileException, WrongFileFormatException {
        InputStream is = service.chargerFichier("/questionnaires/questionsQuizz_2025_V1.csv");
        assertNotNull(is);
    }

    @Test
    void chargerFichier_ko_quandFichierManquant() {
        assertThrows(MissingFileException.class, () ->
                service.chargerFichier("/questionnaires/fichierQuiNExistePas.csv"));
    }

    @Test
    void chargerFichier_ko_quandMauvaiseExtension() {
        assertThrows(WrongFileFormatException.class, () ->
                service.chargerFichier("/questionnaires/pipi.txt"));
    }

    @Test
    void fournirListeQuestionnaire_ok_retourneAuMoinsUnQuestionnaire() throws Exception {
        // on récupère le vrai fichier de ressources via la méthode de service
        InputStream is = service.chargerFichier("/questionnaires/questionsQuizz_2025_V1.csv");

        List<QuestionnaireDTO> liste = service.fournirListeQuestionnaire(is);
        System.out.println(liste);
        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // exemple d’assert supplémentaire
        QuestionnaireDTO q = liste.get(0);
        assertNotNull(q.getLibelleQuestionnaire());
        assertNotNull(q.getLangue());
    }
}
